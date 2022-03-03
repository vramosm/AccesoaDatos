import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface IPiloto {
  id?: number;
  nombre?: string;
  apellido?: string;
  dni?: string;
  direccion?: string;
  email?: string;
  horasDeVuelo?: number | null;
  fotoContentType?: string;
  foto?: string;
  vuelos?: IVuelo[] | null;
}

export class Piloto implements IPiloto {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido?: string,
    public dni?: string,
    public direccion?: string,
    public email?: string,
    public horasDeVuelo?: number | null,
    public fotoContentType?: string,
    public foto?: string,
    public vuelos?: IVuelo[] | null
  ) {}
}

export function getPilotoIdentifier(piloto: IPiloto): number | undefined {
  return piloto.id;
}
