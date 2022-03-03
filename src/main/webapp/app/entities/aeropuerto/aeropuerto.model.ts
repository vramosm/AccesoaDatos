import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface IAeropuerto {
  id?: number;
  nombre?: string;
  ciudad?: string;
  origens?: IVuelo[] | null;
  destinos?: IVuelo[] | null;
}

export class Aeropuerto implements IAeropuerto {
  constructor(
    public id?: number,
    public nombre?: string,
    public ciudad?: string,
    public origens?: IVuelo[] | null,
    public destinos?: IVuelo[] | null
  ) {}
}

export function getAeropuertoIdentifier(aeropuerto: IAeropuerto): number | undefined {
  return aeropuerto.id;
}
