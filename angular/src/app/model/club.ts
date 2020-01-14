import {League} from './league.enum';
import {Player} from './player';

export class Club {

  id: number;

  name: string;

  foundingDate: Date;

  stadiumName: string;

  league: League;

  players: Player[];
}
