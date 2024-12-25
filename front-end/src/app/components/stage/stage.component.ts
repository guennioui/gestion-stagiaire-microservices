import {Component, OnInit} from '@angular/core';
import {StageService} from "../../services/stage.service";
import {NgForm} from "@angular/forms";
import {Stage} from "../../models/stage.model";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrl: './stage.component.css'
})
export class StageComponent implements OnInit{
  stages: Stage[] = [];
  updateStage: Stage | null = null;
  deleteId: number | null = null;

  constructor(private stageService: StageService) {}

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

  public getStages(): void{
    this.stageService.getStages().subscribe((data:any)=>{
      this.stages = data;
    });
  }

}
