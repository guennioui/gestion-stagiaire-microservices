import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SideBarComponent} from "./components/side-bar/side-bar.component";
import {HomeComponent} from "./components/home/home.component";
import {StagiaireComponent} from "./components/stagiaire/stagiaire.component";
import {StageComponent} from "./components/stage/stage.component";
import {EncadrantComponent} from "./components/encadrant/encadrant.component";

const routes: Routes = [
  {path:'sideBar',component:SideBarComponent},
  {path:'',redirectTo:'home',pathMatch:'full'},
  {path:'home',component:HomeComponent},
  {path: 'stagiaires', component: StagiaireComponent},
  {path: 'stages', component: StageComponent},
  {path: 'encadrants', component: EncadrantComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
