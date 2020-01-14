import {Component, OnInit} from '@angular/core';
import {Club} from '../../model/club';
import {League} from '../../model/league.enum';
import {ClubService} from '../../service/club.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Player} from '../../model/player';

@Component({
  selector: 'app-club-edit',
  templateUrl: './club-edit.component.html',
  styleUrls: ['./club-edit.component.css']
})
export class ClubEditComponent implements OnInit {

  club: Club;

  availablePlayers: Player[];

  availableLeagues = League;

  leagueKeys: string[];

  constructor(private service: ClubService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id == null) {
      this.club = new Club();
    } else {
      this.service.findClub(Number(id))
                  .subscribe(club => this.club = club);
    }
    this.service.findAllPlayers()
                .subscribe(players => this.availablePlayers = players);

    this.leagueKeys = Object.keys(this.availableLeagues)
                            .filter(key => !isNaN(Number(key)));
  }

  save() {
    this.service.saveClub(this.club)
                .subscribe(() => this.router.navigateByUrl('/clubs'));
  }

  comparePlayers(player1: Player, player2: Player): boolean {
    return player1 && player2 ? player1.id === player2.id : player1 === player2;
  }

}
