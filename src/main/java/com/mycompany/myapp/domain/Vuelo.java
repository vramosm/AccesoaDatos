package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vuelo.
 */
@Entity
@Table(name = "vuelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "pasaporte_covid", nullable = false)
    private Boolean pasaporteCovid;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "numero_de_vuelo", length = 255, nullable = false, unique = true)
    private String numeroDeVuelo;

    @ManyToOne(optional = false)
    @NotNull
    private Aeropuerto origen;

    @ManyToOne(optional = false)
    @NotNull
    private Aeropuerto destino;

    @ManyToOne(optional = false)
    @NotNull
    private Avion avion;

    @ManyToOne(optional = false)
    @NotNull
    private Piloto piloto;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_vuelo__tripulacion",
        joinColumns = @JoinColumn(name = "vuelo_id"),
        inverseJoinColumns = @JoinColumn(name = "tripulacion_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Tripulante> tripulacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vuelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPasaporteCovid() {
        return this.pasaporteCovid;
    }

    public Vuelo pasaporteCovid(Boolean pasaporteCovid) {
        this.setPasaporteCovid(pasaporteCovid);
        return this;
    }

    public void setPasaporteCovid(Boolean pasaporteCovid) {
        this.pasaporteCovid = pasaporteCovid;
    }

    public String getNumeroDeVuelo() {
        return this.numeroDeVuelo;
    }

    public Vuelo numeroDeVuelo(String numeroDeVuelo) {
        this.setNumeroDeVuelo(numeroDeVuelo);
        return this;
    }

    public void setNumeroDeVuelo(String numeroDeVuelo) {
        this.numeroDeVuelo = numeroDeVuelo;
    }

    public Aeropuerto getOrigen() {
        return this.origen;
    }

    public void setOrigen(Aeropuerto aeropuerto) {
        this.origen = aeropuerto;
    }

    public Vuelo origen(Aeropuerto aeropuerto) {
        this.setOrigen(aeropuerto);
        return this;
    }

    public Aeropuerto getDestino() {
        return this.destino;
    }

    public void setDestino(Aeropuerto aeropuerto) {
        this.destino = aeropuerto;
    }

    public Vuelo destino(Aeropuerto aeropuerto) {
        this.setDestino(aeropuerto);
        return this;
    }

    public Avion getAvion() {
        return this.avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Vuelo avion(Avion avion) {
        this.setAvion(avion);
        return this;
    }

    public Piloto getPiloto() {
        return this.piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Vuelo piloto(Piloto piloto) {
        this.setPiloto(piloto);
        return this;
    }

    public Set<Tripulante> getTripulacions() {
        return this.tripulacions;
    }

    public void setTripulacions(Set<Tripulante> tripulantes) {
        this.tripulacions = tripulantes;
    }

    public Vuelo tripulacions(Set<Tripulante> tripulantes) {
        this.setTripulacions(tripulantes);
        return this;
    }

    public Vuelo addTripulacion(Tripulante tripulante) {
        this.tripulacions.add(tripulante);
        return this;
    }

    public Vuelo removeTripulacion(Tripulante tripulante) {
        this.tripulacions.remove(tripulante);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vuelo)) {
            return false;
        }
        return id != null && id.equals(((Vuelo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vuelo{" +
            "id=" + getId() +
            ", pasaporteCovid='" + getPasaporteCovid() + "'" +
            ", numeroDeVuelo='" + getNumeroDeVuelo() + "'" +
            "}";
    }
}
