export interface ITripulante {
  id?: number;
  nombre?: string;
  apellidos?: string;
  dni?: string;
  direccion?: string;
  email?: string;
  fotoContentType?: string;
  foto?: string;
}

export class Tripulante implements ITripulante {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidos?: string,
    public dni?: string,
    public direccion?: string,
    public email?: string,
    public fotoContentType?: string,
    public foto?: string
  ) {}
}

export function getTripulanteIdentifier(tripulante: ITripulante): number | undefined {
  return tripulante.id;
}
