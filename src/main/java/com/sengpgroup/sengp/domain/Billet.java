package com.sengpgroup.sengp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Billet.
 */
@Entity
@Table(name = "billet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Billet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "url")
    private String url;

    @Column(name = "website")
    private String website;

    @JsonIgnoreProperties(value = { "billet", "colis", "voyageur" }, allowSetters = true)
    @OneToOne(mappedBy = "billet")
    private Voyage voyage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "billets", "voyages" }, allowSetters = true)
    private Voyageur voyageur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Billet id(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public Billet label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return this.url;
    }

    public Billet url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return this.website;
    }

    public Billet website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Voyage getVoyage() {
        return this.voyage;
    }

    public Billet voyage(Voyage voyage) {
        this.setVoyage(voyage);
        return this;
    }

    public void setVoyage(Voyage voyage) {
        if (this.voyage != null) {
            this.voyage.setBillet(null);
        }
        if (voyage != null) {
            voyage.setBillet(this);
        }
        this.voyage = voyage;
    }

    public Voyageur getVoyageur() {
        return this.voyageur;
    }

    public Billet voyageur(Voyageur voyageur) {
        this.setVoyageur(voyageur);
        return this;
    }

    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Billet)) {
            return false;
        }
        return id != null && id.equals(((Billet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Billet{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", url='" + getUrl() + "'" +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
