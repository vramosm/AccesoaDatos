package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Piloto} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PilotoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pilotos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PilotoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido;

    private StringFilter dni;

    private StringFilter direccion;

    private StringFilter email;

    private IntegerFilter horasDeVuelo;

    private Boolean distinct;

    public PilotoCriteria() {}

    public PilotoCriteria(PilotoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido = other.apellido == null ? null : other.apellido.copy();
        this.dni = other.dni == null ? null : other.dni.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.horasDeVuelo = other.horasDeVuelo == null ? null : other.horasDeVuelo.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PilotoCriteria copy() {
        return new PilotoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public StringFilter nombre() {
        if (nombre == null) {
            nombre = new StringFilter();
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getApellido() {
        return apellido;
    }

    public StringFilter apellido() {
        if (apellido == null) {
            apellido = new StringFilter();
        }
        return apellido;
    }

    public void setApellido(StringFilter apellido) {
        this.apellido = apellido;
    }

    public StringFilter getDni() {
        return dni;
    }

    public StringFilter dni() {
        if (dni == null) {
            dni = new StringFilter();
        }
        return dni;
    }

    public void setDni(StringFilter dni) {
        this.dni = dni;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public StringFilter direccion() {
        if (direccion == null) {
            direccion = new StringFilter();
        }
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public IntegerFilter getHorasDeVuelo() {
        return horasDeVuelo;
    }

    public IntegerFilter horasDeVuelo() {
        if (horasDeVuelo == null) {
            horasDeVuelo = new IntegerFilter();
        }
        return horasDeVuelo;
    }

    public void setHorasDeVuelo(IntegerFilter horasDeVuelo) {
        this.horasDeVuelo = horasDeVuelo;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PilotoCriteria that = (PilotoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido, that.apellido) &&
            Objects.equals(dni, that.dni) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(email, that.email) &&
            Objects.equals(horasDeVuelo, that.horasDeVuelo) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, dni, direccion, email, horasDeVuelo, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PilotoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (apellido != null ? "apellido=" + apellido + ", " : "") +
            (dni != null ? "dni=" + dni + ", " : "") +
            (direccion != null ? "direccion=" + direccion + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (horasDeVuelo != null ? "horasDeVuelo=" + horasDeVuelo + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
