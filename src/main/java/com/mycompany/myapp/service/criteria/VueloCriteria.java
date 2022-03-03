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
 * Criteria class for the {@link com.mycompany.myapp.domain.Vuelo} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VueloResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vuelos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class VueloCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter pasaporteCovid;

    private StringFilter numeroDeVuelo;

    private Boolean distinct;

    public VueloCriteria() {}

    public VueloCriteria(VueloCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pasaporteCovid = other.pasaporteCovid == null ? null : other.pasaporteCovid.copy();
        this.numeroDeVuelo = other.numeroDeVuelo == null ? null : other.numeroDeVuelo.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VueloCriteria copy() {
        return new VueloCriteria(this);
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

    public BooleanFilter getPasaporteCovid() {
        return pasaporteCovid;
    }

    public BooleanFilter pasaporteCovid() {
        if (pasaporteCovid == null) {
            pasaporteCovid = new BooleanFilter();
        }
        return pasaporteCovid;
    }

    public void setPasaporteCovid(BooleanFilter pasaporteCovid) {
        this.pasaporteCovid = pasaporteCovid;
    }

    public StringFilter getNumeroDeVuelo() {
        return numeroDeVuelo;
    }

    public StringFilter numeroDeVuelo() {
        if (numeroDeVuelo == null) {
            numeroDeVuelo = new StringFilter();
        }
        return numeroDeVuelo;
    }

    public void setNumeroDeVuelo(StringFilter numeroDeVuelo) {
        this.numeroDeVuelo = numeroDeVuelo;
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
        final VueloCriteria that = (VueloCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(pasaporteCovid, that.pasaporteCovid) &&
            Objects.equals(numeroDeVuelo, that.numeroDeVuelo) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pasaporteCovid, numeroDeVuelo, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VueloCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pasaporteCovid != null ? "pasaporteCovid=" + pasaporteCovid + ", " : "") +
            (numeroDeVuelo != null ? "numeroDeVuelo=" + numeroDeVuelo + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
