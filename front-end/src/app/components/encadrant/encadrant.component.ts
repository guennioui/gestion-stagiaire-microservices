import {Component, OnInit} from '@angular/core';
import {EncadrantService} from "../../services/encadrant.service";;
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Encadrant} from "../../models/encadrant.model";
import {EncadrantPageResponse} from "../../models/encadrantPageResponse";
import {Stagiaire} from "../../models/stagiaire.model";
import {Stage} from "../../models/stage.model";
import {StageService} from "../../services/stage.service";

@Component({
  selector: 'app-encadrant',
  templateUrl: './encadrant.component.html',
  styleUrl: './encadrant.component.css'
})
export class EncadrantComponent {
  encadrants: Encadrant[] = [];
  stages: Stage[] = [];
  selectedStageId: number = 0;
  matricule: string | null = null;
  updateEncadrant: Encadrant | null = null;
  deletedMatricule: string | null = null;
  currentPage: number = 0;
  pageSize: number = 5;
  totalItems: number = 0;
  totalPages: number = 0;

  constructor(private encadrantService: EncadrantService,
              private stageService: StageService) {}

  ngOnInit(): void {
    this.getEncadrants()
    this.loadStages()
  }

  public addEncadrant(data: NgForm) {
    if (data.valid) {
      const encadrant: Encadrant = {} as Encadrant;
      encadrant.matricule = data.value.matricule;
      encadrant.nom = data.value.lastName;
      encadrant.prenom = data.value.firstName;
      encadrant.email = data.value.email;
      encadrant.telephone = data.value.phoneNumber;
      encadrant.specialite = data.value.specialite;

      this.encadrantService.addEncadrant(encadrant).subscribe(
        (addedEncadrant: Encadrant) => {
          alert('Encadrant successfully added');
          this.getEncadrants();
          data.reset();
        },
        (error: any) => {
          console.log(error);
          alert('Failed: ' + error);
        }
      );
    }
  }

  public onUpdate(encadrant: Encadrant){
    this.updateEncadrant = {...encadrant};
  }

  onUpdateEncadrant(updatedEncadrant: Encadrant) {
    if (this.updateEncadrant) {
      this.encadrantService.updateEncadrant(updatedEncadrant).subscribe(
        (response: Encadrant) => {
          console.log(response);
          this.getEncadrants();
          this.updateEncadrant = null;
        },
        (error: HttpErrorResponse) => {
          console.log(error);
          alert(error.message);
        }
      );
    }
  }

  setDeleteId(matricule: string | null ) {
    this.deletedMatricule = matricule;
  }

  setMatriculeEncadrant(encadrant: Encadrant){
    this.matricule = encadrant.matricule;
  }

  assignStageToEncadrant(){
    console.log(this.selectedStageId+'..'+this.matricule);
    if (this.selectedStageId && this.matricule) {
      this.stageService.assignEncadrantToStage(this.selectedStageId, this.matricule,).subscribe(
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

  onDeleteEncadrant() {
    if (this.deletedMatricule !== null) {
      this.encadrantService.deleteEncadrant(this.deletedMatricule).subscribe(
        (res) => {
          console.log('Encadrant '+this.deletedMatricule+' is deleted!');
          console.log(res);
          this.getEncadrants()
        },
        (error: any) => {
          alert(error.message);
          console.log(error);
        }
      );
      this.deletedMatricule = null;
    }
  }

  public getEncadrants(): void {
    this.encadrantService.getEncadrants(this.currentPage, this.pageSize)
      .subscribe((response: EncadrantPageResponse) => {
        this.encadrants = response.content;
        this.currentPage = response.currentPage;
        this.totalItems = response.totalItems;
        this.totalPages = response.totalPages;
      });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.getEncadrants();}



  }


