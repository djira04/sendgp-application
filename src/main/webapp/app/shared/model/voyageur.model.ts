import dayjs from 'dayjs';
import { IBillet } from 'app/shared/model/billet.model';
import { IVoyage } from 'app/shared/model/voyage.model';

export interface IVoyageur {
  id?: number;
  firstname?: string | null;
  lastname?: string | null;
  email?: string | null;
  password?: string | null;
  telephone?: number | null;
  bornDate?: string | null;
  cin?: string | null;
  photo?: string | null;
  billets?: IBillet[] | null;
  voyages?: IVoyage[] | null;
}

export const defaultValue: Readonly<IVoyageur> = {};
