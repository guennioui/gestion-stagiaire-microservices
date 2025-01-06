import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Departement} from "../../models/departement.model";
import {DepartementService} from "../../services/departement.service";
import {DepartementPageResponse} from "../../models/departementPageResponse";
import {Stage} from "../../models/stage.model";
import {StageService} from "../../services/stage.service";

@Component({
  selector: 'app-departement',
  templateUrl: './departement.component.html',
  styleUrl: './departement.component.css'
})
export class DepartementComponent implements OnInit{
  departements: Departement[] = [];
  stages: Stage[] = [];
  selectedStageId: number = 0;
  codeDepartemet: string | null = null;
  updateDepartement: Departement | null = null;
  deletedCode: string | null = null;
  currentPage: number = 0;
  pageSize: number = 5;
  totalItems: number = 0;
  totalPages: number = 0;

  constructor(private departementService: DepartementService,
              private stageService: StageService) {}

  ngOnInit(): void {
    this.getDepartements();
    this.loadStages();
  }

  public addDepartement(data: NgForm) {
    if (data.valid) {
      const departement: Departement = {} as Departement;
      departement.code = data.value.code;
      departement.name = data.value.name;
      departement.description = data.value.description;

      this.departementService.addDepartement(departement).subscribe(
        (addedDepartement: Departement) => {
          alert('Departement successfully added');
          this.getDepartements();
          data.reset();
        },
        (error: any) => {
          console.log(error);
          alert('Failed: ' + error);
        }
      );
    }
  }

  public onUpdate(departement: Departement){
    this.updateDepartement = {...departement};
  }

  onUpdateDepartement(updatedDepartement: Departement) {
    if (this.updateDepartement) {
      this.departementService.updateDepartement(updatedDepartement).subscribe(
        (response: Departement) => {
          console.log(response);
          this.getDepartements();
          this.updateDepartement = null;
        },
        (error: HttpErrorResponse) => {
          console.log(error);
          alert(error.message);
        }
      );
    }
  }

  setDeleteId(code: string | null ) {
    this.deletedCode = code;
  }

  onDeleteDepartement() {
    if (this.deletedCode !== null) {
      this.departementService.deleteDepartement(this.deletedCode).subscribe(
        (res) => {
          console.log('Departement '+this.deletedCode+' is deleted!');
          console.log(res);
          this.getDepartements();
        },
        (error: any) => {
          alert(error.message);
          console.log(error);
        }
      );
      this.deletedCode = null;
    }
  }

  public getDepartements(): void {
    this.departementService.getDepartements(this.currentPage, this.pageSize)
      .subscribe((response: DepartementPageResponse) => {
        this.departements = response.content;
        this.currentPage = response.currentPage;
        this.totalItems = response.totalItems;
        this.totalPages = response.totalPages;
      });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.getDepartements();
  }

  setCodeDepartement(departement: Departement){
    this.codeDepartemet = departement.code;
  }

  assignStageToDepartement(){
    console.log(this.selectedStageId+'..'+this.codeDepartemet);
    if (this.selectedStageId && this.codeDepartemet) {
      this.stageService.assignDepartementToStage(this.selectedStageId, this.codeDepartemet,).subscribe(
        (response) => {
          console.log('Stage assigned successfully');
        }
      );
    }
  }

  loadStages() {
    this.stageService.getAll().subscribe(
      (data: Stage[]) => {
        this.stages = data;
      },
      (error) => {
        console.error('Error loading stages:', error);
      }
    );
  }
}
