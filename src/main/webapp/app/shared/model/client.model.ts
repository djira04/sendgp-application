import dayjs from 'dayjs';
import { IColis } from 'app/shared/model/colis.model';

export interface IClient {
  id?: number;
  firstname?: string | null;
  lastname?: string | null;
  email?: string | null;
  password?: string | null;
  telephone?: number | null;
  bornDate?: string | null;
  cin?: string | null;
  photo?: string | null;
  colis?: IColis[] | null;
}

export const defaultValue: Readonly<IClient> = {};
