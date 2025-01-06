import {Component, OnInit} from '@angular/core';
import {StageService} from "../../services/stage.service";
import {NgForm} from "@angular/forms";
import {Stage} from "../../models/stage.model";
import {HttpErrorResponse} from "@angular/common/http";
import {StagePageResponse} from "../../models/stagePageResponse";
import {StagiaireService} from "../../services/stagiaire.service";
import {Stagiaire} from "../../models/stagiaire.model";

@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrl: './stage.component.css'
})
export class StageComponent implements OnInit{
  stages: Stage[] = [];
  stagiaires: Stagiaire[] = [];
  updateStage: Stage | null = null;
  stageId: number | null = null;
  deleteId: number | null = null;
  currentPage: number = 0;
  pageSize: number = 5;
  totalItems: number = 0;
  totalPages: number = 0;
  constructor(private stageService: StageService,
              private stagiaireService: StagiaireService) {}

  ngOnInit(): void {
    this.getStages();
  }

  public addStage(data: NgForm) {
    if (data.valid) {
      const stage: Stage = {} as Stage;
      stage.title = data.value.title;
      stage.description = data.value.description;
      stage.startDate = data.value.startDate;
      stage.endDate = data.value.endDate;
      stage.stageId = data.value.stageId;

      this.stageService.addStage(stage).subscribe(
        (addedStage: Stage) => {
          alert('Internship successfully added');
          this.getStages();
          data.reset();
        },
        (error: any) => {
          console.log(error);
          alert('Failed: ' + error);
        }
      );
    }
  }

  public onUpdate(stage:Stage){
    this.updateStage = {...stage};
  }

  onUpdateStage(updatedStage: Stage) {
    if (this.updateStage) {
      this.stageService.updateStage(updatedStage).subscribe(
        (response: Stage) => {
          console.log(response);
          this.getStages();
          this.updateStage = null;
        },
        (error: HttpErrorResponse) => {
          console.log(error);
          alert(error.message);
        }
      );
    }
  }

  setDeleteId(stageId: number | null ) {
    this.deleteId = stageId;
  }

  onDeleteStage() {
    if (this.deleteId !== null) {
      this.stageService.deleteStage(this.deleteId).subscribe(
        (res) => {
          console.log('Internship '+this.deleteId+' is deleted!');
          console.log(res);
          this.getStages()
        },
        (error: any) => {
          alert(error.message);
          console.log(error);
        }
      );
      this.deleteId = null;
    }
  }

  public getStages(): void {
    this.stageService.getStages(this.currentPage, this.pageSize)
      .subscribe((response: StagePageResponse) => {
        this.stages = response.content;
        this.currentPage = response.currentPage;
        this.totalItems = response.totalItems;
        this.totalPages = response.totalPages;
      });
  }

  loadStagiaires(stageId: number): void {
    console.log(stageId);
    this.stagiaireService.getStagiaireByStageId(stageId).subscribe(
      (stagiaires: Stagiaire[]) => {
        this.stagiaires = stagiaires;
      },
      (error) => {
        console.error('Error loading stagiaires:', error);
      }
    );
  }

  setStageId(stage: Stage){
    this.stageId = stage.stageId;
    this.loadStagiaires(this.stageId);
  }
  onPageChange(page: number): void {
    this.currentPage = page;
    this.getStages();
  }

}
