import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Club} from '../model/club';
import {Player} from '../model/player';

@Injectable()
export class ClubService {

  constructor(private http: HttpClient) {
  }

  findAllClubs(): Observable<Club[]> {
    return this.http.get<Club[]>('api/clubs');
  }

  findAllPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>('api/players');
  }

  findClub(id: number): Observable<Club> {
    return this.http.get<Club>(`api/clubs/${id}`);
  }

  removeClub(club: Club): Observable<any> {
    return this.http.delete(`api/clubs/${club.id}`);
  }

  saveClub(club: Club): Observable<any> {
    if (club.id) {
      return this.http.put(`api/clubs/${club.id}`, club);
    } else {
      return this.http.post('api/clubs/', club);
    }
  }

}
