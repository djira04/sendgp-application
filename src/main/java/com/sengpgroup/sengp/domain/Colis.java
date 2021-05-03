package com.sengpgroup.sengp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Colis.
 */
@Entity
@Table(name = "colis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Colis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnoreProperties(value = { "billet", "colis", "voyageur" }, allowSetters = true)
    private Voyage voyage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "colis" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Colis id(Long id) {
        this.id = id;
        return this;
    }

    public Float getWeight() {
        return this.weight;
    }

    public Colis weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getDetails() {
        return this.details;
    }

    public Colis details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Voyage getVoyage() {
        return this.voyage;
    }

    public Colis voyage(Voyage voyage) {
        this.setVoyage(voyage);
        return this;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Client getClient() {
        return this.client;
    }

    public Colis client(Client client) {
        this.setClient(client);
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colis)) {
            return false;
        }
        return id != null && id.equals(((Colis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colis{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
