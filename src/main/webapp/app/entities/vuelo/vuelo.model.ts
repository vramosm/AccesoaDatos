export interface IVuelo {
  id?: number;
  pasaporteCovid?: boolean;
  numeroDeVuelo?: string;
}

export class Vuelo implements IVuelo {
  constructor(public id?: number, public pasaporteCovid?: boolean, public numeroDeVuelo?: string) {
    this.pasaporteCovid = this.pasaporteCovid ?? false;
  }
}

export function getVueloIdentifier(vuelo: IVuelo): number | undefined {
  return vuelo.id;
}
