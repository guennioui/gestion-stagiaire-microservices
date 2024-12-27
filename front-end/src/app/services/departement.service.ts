import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PageResponse} from "../models/PageResponse";
import {Stagiaire} from "../models/stagiaire.model";
import {Departement} from "../models/departement.model";
import {Stage} from "../models/stage.model";

@Injectable({
  providedIn: 'root'
})
export class DepartementService {

  private apiServerUrl = "http://127.0.0.1:8082/api/departement"
  constructor(private http: HttpClient) { }

  public getDepartements():Observable<Departement[]>{
    return this.http.get<Departement[]>(this.apiServerUrl+'/all');
  }

  public addDepartement(data: Departement): Observable<Departement>{
    return this.http.post<Departement>(this.apiServerUrl+'/add-departement', data);
  }

  public updateDepartement(data: Departement): Observable<Departement> {
    return this.http.put<Departement>(this.apiServerUrl+'/update/'+data.code, data);
  }

  public deleteDepartement(code: string):Observable<string>{
    return this.http.delete<string>(this.apiServerUrl+'/delete/'+code, {responseType: 'text' as 'json'});
  }

  public findDepartementByCode(code: string):Observable<Departement>{
    return this.http.get<Departement>(this.apiServerUrl+'/'+code);
  }
}
