import dayjs from 'dayjs';
import { IBillet } from 'app/shared/model/billet.model';
import { IColis } from 'app/shared/model/colis.model';
import { IVoyageur } from 'app/shared/model/voyageur.model';

export interface IVoyage {
  id?: number;
  depatureCountry?: string | null;
  depatureAddress?: string | null;
  depatureCity?: string | null;
  depatureDate?: string | null;
  depatureTime?: string | null;
  arrivalCountry?: string | null;
  arrivalAddress?: string | null;
  cityArrival?: string | null;
  dateArrival?: string | null;
  arrivalTime?: string | null;
  kilos?: number | null;
  unitPrice?: number | null;
  valid?: boolean | null;
  billet?: IBillet | null;
  colis?: IColis[] | null;
  voyageur?: IVoyageur | null;
}

export const defaultValue: Readonly<IVoyage> = {
  valid: false,
};
