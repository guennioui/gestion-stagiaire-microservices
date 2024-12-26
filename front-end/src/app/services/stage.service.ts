import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Stage} from "../models/stage.model";

@Injectable({
  providedIn: 'root'
})
export class StageService {
  private apiServerUrl = "http://127.0.0.1:8081/api/stage"
  constructor(private http: HttpClient) { }

  public getStages():Observable<Stage[]>{
    return this.http.get<Stage[]>(this.apiServerUrl+'/all');
  }

  public addStage(data: Stage): Observable<Stage>{
    return this.http.post<Stage>(this.apiServerUrl+'/add-stage', data);
  }

  public updateStage(data: Stage): Observable<Stage> {
    return this.http.put<Stage>(this.apiServerUrl+'/update/'+data.stageId, data);
  }

  public deleteStage(stageId: number):Observable<void>{
    return this.http.delete<void>(this.apiServerUrl+'/delete/'+stageId);
  }

  public findStageById(stageId: number):Observable<Stage>{
    return this.http.get<Stage>(this.apiServerUrl+'/'+stageId);
  }
}
