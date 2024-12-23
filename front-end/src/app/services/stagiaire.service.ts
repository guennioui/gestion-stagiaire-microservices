import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Stage} from "../models/stage.model";
import {Stagiaire} from "../models/stagiaire.model";

@Injectable({
  providedIn: 'root'
})
export class StagiaireService {
  private apiServerUrl = "http://127.0.0.1:8081/api/stagiaire"
  constructor(private http: HttpClient) { }

  public getStagiaires():Observable<Stagiaire[]>{
    return this.http.get<Stagiaire[]>(this.apiServerUrl+'/all');
  }

  public addStagiaire(data: Stagiaire): Observable<Stagiaire>{
    return this.http.post<Stagiaire>(this.apiServerUrl+'/add-stagiaire', data);
  }

  public updateStagiaire(data: Stagiaire): Observable<Stagiaire> {
    return this.http.put<Stagiaire>(this.apiServerUrl+'/update-stagiaire/'+data.matricule, data);
  }

  public deleteStagiaire(matricule: string):Observable<void>{
    return this.http.delete<void>(this.apiServerUrl+'/delete-stagiaire/'+matricule);
  }

  public findStagiaireByMatricule(matricule: string):Observable<Stagiaire>{
    return this.http.get<Stagiaire>(this.apiServerUrl+'/'+matricule);
  }

  public assignStageToStagiaire(matricule: string, stageId: number):Observable<string>{
    return this.http.post<string>(this.apiServerUrl+'/assign-stagiaire/'+matricule+'/'+stageId, {});
  }


}
