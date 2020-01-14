import {Component, OnInit} from '@angular/core';
import {PlayerService} from '../../service/player.service';
import {Observable} from 'rxjs';
import {Player} from '../../model/player';

@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {

  players: Observable<Player[]>;

  constructor(private service: PlayerService) {
  }

  ngOnInit() {
    this.players = this.service.findAllPlayers();
  }

  remove(player: Player) {
    this.service.removePlayer(player)
                .subscribe(() => this.ngOnInit());
  }
}
