import {Stagiaire} from "./stagiaire.model";

export interface PageResponse {
  stagiaires: Stagiaire[];
  currentPage: number;
  totalItems: number;
  totalPages: number;
}
