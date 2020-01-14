import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClubListComponent} from './component/club-list/club-list.component';
import {ClubEditComponent} from './component/club-edit/club-edit.component';
import {PlayerListComponent} from './component/player-list/player-list.component';
import {PlayerEditComponent} from './component/player-edit/player-edit.component';

const routes: Routes = [
  {path: 'clubs', component: ClubListComponent},
  {path: 'clubs/edit', component: ClubEditComponent},
  {path: 'clubs/:id/edit', component: ClubEditComponent},
  {path: 'players', component: PlayerListComponent},
  {path: 'players/edit', component: PlayerEditComponent},
  {path: 'players/:id/edit', component: PlayerEditComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
