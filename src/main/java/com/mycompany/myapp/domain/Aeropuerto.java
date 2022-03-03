package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Aeropuerto.
 */
@Entity
@Table(name = "aeropuerto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aeropuerto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "ciudad", length = 255, nullable = false)
    private String ciudad;

    @OneToMany(mappedBy = "origen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "origen", "destino", "avion", "piloto", "tripulacions" }, allowSetters = true)
    private Set<Vuelo> origens = new HashSet<>();

    @OneToMany(mappedBy = "destino")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "origen", "destino", "avion", "piloto", "tripulacions" }, allowSetters = true)
    private Set<Vuelo> destinos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aeropuerto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Aeropuerto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Aeropuerto ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Set<Vuelo> getOrigens() {
        return this.origens;
    }

    public void setOrigens(Set<Vuelo> vuelos) {
        if (this.origens != null) {
            this.origens.forEach(i -> i.setOrigen(null));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.setOrigen(this));
        }
        this.origens = vuelos;
    }

    public Aeropuerto origens(Set<Vuelo> vuelos) {
        this.setOrigens(vuelos);
        return this;
    }

    public Aeropuerto addOrigen(Vuelo vuelo) {
        this.origens.add(vuelo);
        vuelo.setOrigen(this);
        return this;
    }

    public Aeropuerto removeOrigen(Vuelo vuelo) {
        this.origens.remove(vuelo);
        vuelo.setOrigen(null);
        return this;
    }

    public Set<Vuelo> getDestinos() {
        return this.destinos;
    }

    public void setDestinos(Set<Vuelo> vuelos) {
        if (this.destinos != null) {
            this.destinos.forEach(i -> i.setDestino(null));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.setDestino(this));
        }
        this.destinos = vuelos;
    }

    public Aeropuerto destinos(Set<Vuelo> vuelos) {
        this.setDestinos(vuelos);
        return this;
    }

    public Aeropuerto addDestino(Vuelo vuelo) {
        this.destinos.add(vuelo);
        vuelo.setDestino(this);
        return this;
    }

    public Aeropuerto removeDestino(Vuelo vuelo) {
        this.destinos.remove(vuelo);
        vuelo.setDestino(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aeropuerto)) {
            return false;
        }
        return id != null && id.equals(((Aeropuerto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aeropuerto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            "}";
    }
}
