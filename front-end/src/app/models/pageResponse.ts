import {Stagiaire} from "./stagiaire.model";

export interface PageResponse {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  stagiaires: Stagiaire[];
}
