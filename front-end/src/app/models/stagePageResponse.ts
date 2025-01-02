import {Stage} from "./stage.model";

export interface StagePageResponse {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  content: Stage[];
}
