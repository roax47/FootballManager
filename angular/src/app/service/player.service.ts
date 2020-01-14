import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Player} from '../model/player';
import {Club} from '../model/club';


@Injectable()
export class PlayerService {

  constructor(private http: HttpClient) {
  }

  findAllPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>('api/players');
  }

  findAllClubs(): Observable<Club[]> {
    return this.http.get<Club[]>('api/clubs');
  }
  findPlayer(id: number): Observable<Player> {
    return this.http.get<Player>(`api/players/${id}`);
  }

  removePlayer(player: Player): Observable<any> {
    return this.http.delete(`api/players/${player.id}`);
  }

  savePlayer(player: Player): Observable<any> {
    if (player.id) {
      return this.http.put(`api/players/${player.id}`, player);
    } else {
      return this.http.post('api/players/', player);
    }
  }

}
