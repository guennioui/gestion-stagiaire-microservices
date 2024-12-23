import {Stage} from "./stage.model";

export interface Stagiaire{
  matricule: string;
  lastName: string;
  firstName: string;
  email: string;
  phoneNumber: string;
  schoolName: string;
  stage?: Stage;
}
