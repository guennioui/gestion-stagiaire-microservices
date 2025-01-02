import {Encadrant} from "./encadrant.model";

export interface EncadrantPageResponse {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  content: Encadrant[];
}
