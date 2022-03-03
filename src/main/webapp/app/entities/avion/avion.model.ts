export interface IAvion {
  id?: number;
  tipo?: string;
  matricula?: string;
  numeroSerie?: string;
  edad?: number | null;
}

export class Avion implements IAvion {
  constructor(
    public id?: number,
    public tipo?: string,
    public matricula?: string,
    public numeroSerie?: string,
    public edad?: number | null
  ) {}
}

export function getAvionIdentifier(avion: IAvion): number | undefined {
  return avion.id;
}
