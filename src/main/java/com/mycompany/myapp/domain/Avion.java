package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avion.
 */
@Entity
@Table(name = "avion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "^.\\\\\\\\-.$")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Pattern(regexp = "^.\\\\\\\\-.$")
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "numero_serie", length = 255, nullable = false, unique = true)
    private String numeroSerie;

    @Min(value = 0)
    @Column(name = "edad")
    private Integer edad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Avion tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public Avion matricula(String matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Avion numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public Avion edad(Integer edad) {
        this.setEdad(edad);
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avion)) {
            return false;
        }
        return id != null && id.equals(((Avion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avion{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", matricula='" + getMatricula() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", edad=" + getEdad() +
            "}";
    }
}
