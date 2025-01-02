import {Stagiaire} from "./stagiaire.model";
import {Encadrant} from "./encadrant.model";

export interface EncadrantPageResponse {
  content: Encadrant[];
  currentPage: number;
  totalItems: number;
  totalPages: number;
}
