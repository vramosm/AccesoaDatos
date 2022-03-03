import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITripulante } from '../tripulante.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-tripulante-detail',
  templateUrl: './tripulante-detail.component.html',
})
export class TripulanteDetailComponent implements OnInit {
  tripulante: ITripulante | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tripulante }) => {
      this.tripulante = tripulante;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
