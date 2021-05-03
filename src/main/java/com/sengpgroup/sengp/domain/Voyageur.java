package com.sengpgroup.sengp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Voyageur.
 */
@Entity
@Table(name = "voyageur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Voyageur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telephone")
    private Integer telephone;

    @Column(name = "born_date")
    private Instant bornDate;

    @Column(name = "cin")
    private String cin;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "voyageur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voyage", "voyageur" }, allowSetters = true)
    private Set<Billet> billets = new HashSet<>();

    @OneToMany(mappedBy = "voyageur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "billet", "colis", "voyageur" }, allowSetters = true)
    private Set<Voyage> voyages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voyageur id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Voyageur firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Voyageur lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public Voyageur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Voyageur password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTelephone() {
        return this.telephone;
    }

    public Voyageur telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Instant getBornDate() {
        return this.bornDate;
    }

    public Voyageur bornDate(Instant bornDate) {
        this.bornDate = bornDate;
        return this;
    }

    public void setBornDate(Instant bornDate) {
        this.bornDate = bornDate;
    }

    public String getCin() {
        return this.cin;
    }

    public Voyageur cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Voyageur photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Billet> getBillets() {
        return this.billets;
    }

    public Voyageur billets(Set<Billet> billets) {
        this.setBillets(billets);
        return this;
    }

    public Voyageur addBillet(Billet billet) {
        this.billets.add(billet);
        billet.setVoyageur(this);
        return this;
    }

    public Voyageur removeBillet(Billet billet) {
        this.billets.remove(billet);
        billet.setVoyageur(null);
        return this;
    }

    public void setBillets(Set<Billet> billets) {
        if (this.billets != null) {
            this.billets.forEach(i -> i.setVoyageur(null));
        }
        if (billets != null) {
            billets.forEach(i -> i.setVoyageur(this));
        }
        this.billets = billets;
    }

    public Set<Voyage> getVoyages() {
        return this.voyages;
    }

    public Voyageur voyages(Set<Voyage> voyages) {
        this.setVoyages(voyages);
        return this;
    }

    public Voyageur addVoyage(Voyage voyage) {
        this.voyages.add(voyage);
        voyage.setVoyageur(this);
        return this;
    }

    public Voyageur removeVoyage(Voyage voyage) {
        this.voyages.remove(voyage);
        voyage.setVoyageur(null);
        return this;
    }

    public void setVoyages(Set<Voyage> voyages) {
        if (this.voyages != null) {
            this.voyages.forEach(i -> i.setVoyageur(null));
        }
        if (voyages != null) {
            voyages.forEach(i -> i.setVoyageur(this));
        }
        this.voyages = voyages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voyageur)) {
            return false;
        }
        return id != null && id.equals(((Voyageur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voyageur{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", telephone=" + getTelephone() +
            ", bornDate='" + getBornDate() + "'" +
            ", cin='" + getCin() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
