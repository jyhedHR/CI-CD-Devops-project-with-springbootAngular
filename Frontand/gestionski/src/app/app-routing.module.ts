import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SkierComponent } from './components/skier/skier.component';

const routes: Routes = [
  { path: 'skiers', component: SkierComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
