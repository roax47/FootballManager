import {Component, OnInit} from '@angular/core';
import {ClubService} from '../../service/club.service';
import {Observable} from 'rxjs';
import {Club} from '../../model/club';


@Component({
  selector: 'app-club-list',
  templateUrl: './club-list.component.html',
  styleUrls: ['./club-list.component.css']
})
export class ClubListComponent implements OnInit {

  clubs: Observable<Club[]>;

  constructor(private service: ClubService) {
  }

  ngOnInit() {
    this.clubs = this.service.findAllClubs();
  }

  remove(club: Club) {
    this.service.removeClub(club)
                .subscribe(() => this.ngOnInit());
  }
}
