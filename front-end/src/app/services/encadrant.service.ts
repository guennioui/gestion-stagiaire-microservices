import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Encadrant} from "../models/encadrant.model";
import {PageResponse} from "../models/PageResponse";
import {EncadrantPageResponse} from "../models/encadrantPageResponse";

@Injectable({
  providedIn: 'root'
})
export class EncadrantService {
  private apiServerUrl = "http://127.0.0.1:8083/api/encadrant"
  constructor(private http: HttpClient) { }

  public getEncadrants(page: number, size: number): Observable<EncadrantPageResponse> {
    return this.http.get<EncadrantPageResponse>(`${this.apiServerUrl}/get-all?page=${page}&size=${size}`);
  }

  public addEncadrant(data: Encadrant): Observable<Encadrant>{
    return this.http.post<Encadrant>(this.apiServerUrl+'/add-encadrant', data);
  }

  public updateEncadrant(data: Encadrant): Observable<Encadrant> {
    return this.http.put<Encadrant>(this.apiServerUrl+'/update-encadrant/'+data.matricule, data);
  }

  public deleteEncadrant(matricule: string):Observable<string>{
    return this.http.delete<string>(this.apiServerUrl+'/delete-encadrant/'+matricule, {responseType: 'text' as 'json'});
  }

  public findEncadrantByMatricule(matricule: string):Observable<Encadrant>{
    return this.http.get<Encadrant>(this.apiServerUrl+'/'+matricule);
  }

  public assignStageToEncadrant(matricule: string, stageId: number):Observable<string>{
    return this.http.post<string>(this.apiServerUrl+'/assign-encadrant/'+matricule+'/'+stageId, {});
  }
}
