import { IVoyage } from 'app/shared/model/voyage.model';
import { IClient } from 'app/shared/model/client.model';

export interface IColis {
  id?: number;
  weight?: number | null;
  details?: string | null;
  voyage?: IVoyage | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IColis> = {};
