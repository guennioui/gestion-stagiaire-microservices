import {Encadrant} from "./encadrant.model";
import {Departement} from "./departement.model";

export interface Stage {
  stageId: number;
  title: string;
  description: string;
  startDate: Date;
  endDate: Date;
  codeDepartement: string;
  departementDto?: Departement;
  matriculeEncadrant: string;
  encadrantDto?: Encadrant;
}
