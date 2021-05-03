import { IVoyage } from 'app/shared/model/voyage.model';
import { IVoyageur } from 'app/shared/model/voyageur.model';

export interface IBillet {
  id?: number;
  label?: string | null;
  url?: string | null;
  website?: string | null;
  voyage?: IVoyage | null;
  voyageur?: IVoyageur | null;
}

export const defaultValue: Readonly<IBillet> = {};
