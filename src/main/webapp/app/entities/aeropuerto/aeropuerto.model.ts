export interface IAeropuerto {
  id?: number;
  nombre?: string;
  ciudad?: string;
}

export class Aeropuerto implements IAeropuerto {
  constructor(public id?: number, public nombre?: string, public ciudad?: string) {}
}

export function getAeropuertoIdentifier(aeropuerto: IAeropuerto): number | undefined {
  return aeropuerto.id;
}
