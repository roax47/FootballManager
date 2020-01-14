import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './component/app/app.component';
import {AppRoutingModule} from './app-routing.module';
import {ClubService} from './service/club.service';
import {PlayerService} from './service/player.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {ClubListComponent} from './component/club-list/club-list.component';
import {ClubEditComponent} from './component/club-edit/club-edit.component';
import {PlayerListComponent} from './component/player-list/player-list.component';
import {PlayerEditComponent} from './component/player-edit/player-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    ClubListComponent,
    ClubEditComponent,
    PlayerListComponent,
    PlayerEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ClubService,
    PlayerService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
