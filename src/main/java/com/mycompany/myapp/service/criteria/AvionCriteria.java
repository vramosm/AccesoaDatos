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
 * Criteria class for the {@link com.mycompany.myapp.domain.Avion} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AvionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /avions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class AvionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipo;

    private StringFilter matricula;

    private StringFilter numeroSerie;

    private IntegerFilter edad;

    private Boolean distinct;

    public AvionCriteria() {}

    public AvionCriteria(AvionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.matricula = other.matricula == null ? null : other.matricula.copy();
        this.numeroSerie = other.numeroSerie == null ? null : other.numeroSerie.copy();
        this.edad = other.edad == null ? null : other.edad.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AvionCriteria copy() {
        return new AvionCriteria(this);
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

    public StringFilter getTipo() {
        return tipo;
    }

    public StringFilter tipo() {
        if (tipo == null) {
            tipo = new StringFilter();
        }
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getMatricula() {
        return matricula;
    }

    public StringFilter matricula() {
        if (matricula == null) {
            matricula = new StringFilter();
        }
        return matricula;
    }

    public void setMatricula(StringFilter matricula) {
        this.matricula = matricula;
    }

    public StringFilter getNumeroSerie() {
        return numeroSerie;
    }

    public StringFilter numeroSerie() {
        if (numeroSerie == null) {
            numeroSerie = new StringFilter();
        }
        return numeroSerie;
    }

    public void setNumeroSerie(StringFilter numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public IntegerFilter getEdad() {
        return edad;
    }

    public IntegerFilter edad() {
        if (edad == null) {
            edad = new IntegerFilter();
        }
        return edad;
    }

    public void setEdad(IntegerFilter edad) {
        this.edad = edad;
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
        final AvionCriteria that = (AvionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(matricula, that.matricula) &&
            Objects.equals(numeroSerie, that.numeroSerie) &&
            Objects.equals(edad, that.edad) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, matricula, numeroSerie, edad, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (matricula != null ? "matricula=" + matricula + ", " : "") +
            (numeroSerie != null ? "numeroSerie=" + numeroSerie + ", " : "") +
            (edad != null ? "edad=" + edad + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
