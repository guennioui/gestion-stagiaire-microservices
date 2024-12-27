import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Stage} from "../models/stage.model";
import {Stagiaire} from "../models/stagiaire.model";
import {PageResponse} from "../models/PageResponse";

@Injectable({
  providedIn: 'root'
})
export class StagiaireService {
  private apiServerUrl = "http://127.0.0.1:8080/api/stagiaire"
  constructor(private http: HttpClient) { }

  public getStagiaires(page: number, size: number): Observable<PageResponse> {
    return this.http.get<PageResponse>(`${this.apiServerUrl}/get-all?page=${page}&size=${size}`);
  }

  public addStagiaire(data: Stagiaire): Observable<Stagiaire>{
    return this.http.post<Stagiaire>(this.apiServerUrl+'/add-stagiaire', data);
  }

  public updateStagiaire(data: Stagiaire): Observable<Stagiaire> {
    return this.http.put<Stagiaire>(this.apiServerUrl+'/update-stagiaire/'+data.matricule, data);
  }

  public deleteStagiaire(matricule: string):Observable<string>{
    return this.http.delete<string>(this.apiServerUrl+'/delete-stagiaire/'+matricule, {responseType: 'text' as 'json'});
  }

  public findStagiaireByMatricule(matricule: string):Observable<Stagiaire>{
    return this.http.get<Stagiaire>(this.apiServerUrl+'/'+matricule);
  }

  public assignStageToStagiaire(matricule: string, stageId: number):Observable<string>{
    return this.http.post<string>(this.apiServerUrl+'/assign-stagiaire/'+matricule+'/'+stageId, {});
  }


}
