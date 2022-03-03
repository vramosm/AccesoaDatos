import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPiloto, Piloto } from '../piloto.model';
import { PilotoService } from '../service/piloto.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-piloto-update',
  templateUrl: './piloto-update.component.html',
})
export class PilotoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    apellido: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    dni: [null, [Validators.required, Validators.pattern('[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]')]],
    direccion: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    email: [null, [Validators.required, Validators.pattern('^[^@\\\\s]+@[^@\\\\s]+\\\\.[^@\\\\s]+$')]],
    horasDeVuelo: [null, [Validators.min(0)]],
    foto: [null, [Validators.required]],
    fotoContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pilotoService: PilotoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ piloto }) => {
      this.updateForm(piloto);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('aDatosApp.error', { message: err.message })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const piloto = this.createFromForm();
    if (piloto.id !== undefined) {
      this.subscribeToSaveResponse(this.pilotoService.update(piloto));
    } else {
      this.subscribeToSaveResponse(this.pilotoService.create(piloto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPiloto>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(piloto: IPiloto): void {
    this.editForm.patchValue({
      id: piloto.id,
      nombre: piloto.nombre,
      apellido: piloto.apellido,
      dni: piloto.dni,
      direccion: piloto.direccion,
      email: piloto.email,
      horasDeVuelo: piloto.horasDeVuelo,
      foto: piloto.foto,
      fotoContentType: piloto.fotoContentType,
    });
  }

  protected createFromForm(): IPiloto {
    return {
      ...new Piloto(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido: this.editForm.get(['apellido'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      email: this.editForm.get(['email'])!.value,
      horasDeVuelo: this.editForm.get(['horasDeVuelo'])!.value,
      fotoContentType: this.editForm.get(['fotoContentType'])!.value,
      foto: this.editForm.get(['foto'])!.value,
    };
  }
}
