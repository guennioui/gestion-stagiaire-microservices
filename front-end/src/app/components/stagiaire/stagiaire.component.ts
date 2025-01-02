import {Component, OnInit} from '@angular/core';
import {Stage} from "../../models/stage.model";
import {StageService} from "../../services/stage.service";
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {StagiaireService} from "../../services/stagiaire.service";
import {Stagiaire} from "../../models/stagiaire.model";
import {PageResponse} from "../../models/pageResponse";

@Component({
  selector: 'app-stagiaire',
  templateUrl: './stagiaire.component.html',
  styleUrl: './stagiaire.component.css'
})
export class StagiaireComponent implements OnInit{
  stagiaires: Stagiaire[] = [];
  updateStagiaire: Stagiaire | null = null;
  deletedMatricule: string | null = null;
  stageOfSelectedStagiaire: Stage | null = null;
  currentPage: number = 0;
  pageSize: number = 5;
  totalItems: number = 0;
  totalPages: number = 0;

  constructor(private stagiaireService: StagiaireService) {}

  ngOnInit(): void {
    this.getStagiaires()
  }

  public addStagiaire(data: NgForm) {
    if (data.valid) {
      const stagiaire: Stagiaire = {} as Stagiaire;
      stagiaire.matricule = data.value.matricule;
      stagiaire.lastName = data.value.lastName;
      stagiaire.firstName = data.value.firstName;
      stagiaire.email = data.value.email;
      stagiaire.phoneNumber = data.value.phoneNumber;
      stagiaire.schoolName = data.value.schoolName;

      this.stagiaireService.addStagiaire(stagiaire).subscribe(
        (addedStagiaire: Stagiaire) => {
          alert('Stagiaire successfully added');
          this.getStagiaires();
          data.reset();
        },
        (error: any) => {
          console.log(error);
          alert('Failed: ' + error);
        }
      );
    }
  }

  public onUpdate(stagiaire: Stagiaire){
    this.updateStagiaire = {...stagiaire};
  }

  onUpdateStagiaire(updatedStagiaire: Stagiaire) {
    if (this.updateStagiaire) {
      this.stagiaireService.updateStagiaire(updatedStagiaire).subscribe(
        (response: Stagiaire) => {
          console.log(response);
          this.getStagiaires();
          this.updateStagiaire = null;
        },
        (error: HttpErrorResponse) => {
          console.log(error);
          alert(error.message);
        }
      );
    }
  }

  viewStageDetails(stagiaire: Stagiaire) {
    console.log(stagiaire);
    if (stagiaire.stageDto) {
      this.stageOfSelectedStagiaire = { ...stagiaire.stageDto };
    } else {
      this.stageOfSelectedStagiaire = null;
    }
  }

  setDeleteId(matricule: string | null ) {
    this.deletedMatricule = matricule;
  }

  onDeleteStagiaire() {
    if (this.deletedMatricule !== null) {
      this.stagiaireService.deleteStagiaire(this.deletedMatricule).subscribe(
        (res) => {
          console.log('Stagiaire '+this.deletedMatricule+' is deleted!');
          console.log(res);
          this.getStagiaires()
        },
        (error: any) => {
          alert(error.message);
          console.log(error);
        }
      );
      this.deletedMatricule = null;
    }
  }

  public getStagiaires(): void {
    this.stagiaireService.getStagiaires(this.currentPage, this.pageSize)
      .subscribe((response: PageResponse) => {
        this.stagiaires = response.stagiaires;
        this.currentPage = response.currentPage;
        this.totalItems = response.totalItems;
        this.totalPages = response.totalPages;
      });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.getStagiaires();
  }
}
