import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPiloto } from '../piloto.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-piloto-detail',
  templateUrl: './piloto-detail.component.html',
})
export class PilotoDetailComponent implements OnInit {
  piloto: IPiloto | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ piloto }) => {
      this.piloto = piloto;
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
