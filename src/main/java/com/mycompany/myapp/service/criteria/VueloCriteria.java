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

    private LongFilter origenId;

    private LongFilter destinoId;

    private LongFilter avionId;

    private LongFilter pilotoId;

    private LongFilter tripulacionId;

    private Boolean distinct;

    public VueloCriteria() {}

    public VueloCriteria(VueloCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pasaporteCovid = other.pasaporteCovid == null ? null : other.pasaporteCovid.copy();
        this.numeroDeVuelo = other.numeroDeVuelo == null ? null : other.numeroDeVuelo.copy();
        this.origenId = other.origenId == null ? null : other.origenId.copy();
        this.destinoId = other.destinoId == null ? null : other.destinoId.copy();
        this.avionId = other.avionId == null ? null : other.avionId.copy();
        this.pilotoId = other.pilotoId == null ? null : other.pilotoId.copy();
        this.tripulacionId = other.tripulacionId == null ? null : other.tripulacionId.copy();
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

    public LongFilter getOrigenId() {
        return origenId;
    }

    public LongFilter origenId() {
        if (origenId == null) {
            origenId = new LongFilter();
        }
        return origenId;
    }

    public void setOrigenId(LongFilter origenId) {
        this.origenId = origenId;
    }

    public LongFilter getDestinoId() {
        return destinoId;
    }

    public LongFilter destinoId() {
        if (destinoId == null) {
            destinoId = new LongFilter();
        }
        return destinoId;
    }

    public void setDestinoId(LongFilter destinoId) {
        this.destinoId = destinoId;
    }

    public LongFilter getAvionId() {
        return avionId;
    }

    public LongFilter avionId() {
        if (avionId == null) {
            avionId = new LongFilter();
        }
        return avionId;
    }

    public void setAvionId(LongFilter avionId) {
        this.avionId = avionId;
    }

    public LongFilter getPilotoId() {
        return pilotoId;
    }

    public LongFilter pilotoId() {
        if (pilotoId == null) {
            pilotoId = new LongFilter();
        }
        return pilotoId;
    }

    public void setPilotoId(LongFilter pilotoId) {
        this.pilotoId = pilotoId;
    }

    public LongFilter getTripulacionId() {
        return tripulacionId;
    }

    public LongFilter tripulacionId() {
        if (tripulacionId == null) {
            tripulacionId = new LongFilter();
        }
        return tripulacionId;
    }

    public void setTripulacionId(LongFilter tripulacionId) {
        this.tripulacionId = tripulacionId;
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
            Objects.equals(origenId, that.origenId) &&
            Objects.equals(destinoId, that.destinoId) &&
            Objects.equals(avionId, that.avionId) &&
            Objects.equals(pilotoId, that.pilotoId) &&
            Objects.equals(tripulacionId, that.tripulacionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pasaporteCovid, numeroDeVuelo, origenId, destinoId, avionId, pilotoId, tripulacionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VueloCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pasaporteCovid != null ? "pasaporteCovid=" + pasaporteCovid + ", " : "") +
            (numeroDeVuelo != null ? "numeroDeVuelo=" + numeroDeVuelo + ", " : "") +
            (origenId != null ? "origenId=" + origenId + ", " : "") +
            (destinoId != null ? "destinoId=" + destinoId + ", " : "") +
            (avionId != null ? "avionId=" + avionId + ", " : "") +
            (pilotoId != null ? "pilotoId=" + pilotoId + ", " : "") +
            (tripulacionId != null ? "tripulacionId=" + tripulacionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
