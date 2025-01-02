import {Departement} from "./departement.model";

export interface DepartementPageResponse {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  content: Departement[];
}
