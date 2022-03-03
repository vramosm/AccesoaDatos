package com.mycompany.myapp.domain;

import java.io.Serializable;
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
