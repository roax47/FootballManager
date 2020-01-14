import {Component, OnInit} from '@angular/core';
import {Player} from '../../model/player';
import {Club} from '../../model/club';
import {Position} from '../../model/position.enum';
import {PlayerService} from '../../service/player.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-player-edit',
  templateUrl: './player-edit.component.html',
  styleUrls: ['./player-edit.component.css']
})
export class PlayerEditComponent implements OnInit {

  datePattern = '[0-9]{4}-[0-9]{2}-[0-9]{2}';

  player: Player;

  availableClubs: Club[];

  availablePositions = Position;

  positionKeys: string[];

  constructor(private service: PlayerService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id == null) {
      this.player = new Player();
    } else {
      this.service.findPlayer(Number(id))
                  .subscribe(player => this.player = player);
    }
    this.service.findAllClubs()
                .subscribe(clubs => this.availableClubs = clubs);
    this.positionKeys = Object.keys(this.availablePositions)
                              .filter(key => !isNaN(Number(key)));
  }

  save() {
    this.service.savePlayer(this.player)
              .subscribe(() => this.router.navigateByUrl('/players'));
  }


}
