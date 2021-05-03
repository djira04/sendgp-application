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
 * A Voyage.
 */
@Entity
@Table(name = "voyage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Voyage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "depature_country")
    private String depatureCountry;

    @Column(name = "depature_address")
    private String depatureAddress;

    @Column(name = "depature_city")
    private String depatureCity;

    @Column(name = "depature_date")
    private Instant depatureDate;

    @Column(name = "depature_time")
    private Instant depatureTime;

    @Column(name = "arrival_country")
    private String arrivalCountry;

    @Column(name = "arrival_address")
    private String arrivalAddress;

    @Column(name = "city_arrival")
    private String cityArrival;

    @Column(name = "date_arrival")
    private Instant dateArrival;

    @Column(name = "arrival_time")
    private Instant arrivalTime;

    @Column(name = "kilos")
    private Integer kilos;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "valid")
    private Boolean valid;

    @JsonIgnoreProperties(value = { "voyage", "voyageur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Billet billet;

    @OneToMany(mappedBy = "voyage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voyage", "client" }, allowSetters = true)
    private Set<Colis> colis = new HashSet<>();

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

    public Voyage id(Long id) {
        this.id = id;
        return this;
    }

    public String getDepatureCountry() {
        return this.depatureCountry;
    }

    public Voyage depatureCountry(String depatureCountry) {
        this.depatureCountry = depatureCountry;
        return this;
    }

    public void setDepatureCountry(String depatureCountry) {
        this.depatureCountry = depatureCountry;
    }

    public String getDepatureAddress() {
        return this.depatureAddress;
    }

    public Voyage depatureAddress(String depatureAddress) {
        this.depatureAddress = depatureAddress;
        return this;
    }

    public void setDepatureAddress(String depatureAddress) {
        this.depatureAddress = depatureAddress;
    }

    public String getDepatureCity() {
        return this.depatureCity;
    }

    public Voyage depatureCity(String depatureCity) {
        this.depatureCity = depatureCity;
        return this;
    }

    public void setDepatureCity(String depatureCity) {
        this.depatureCity = depatureCity;
    }

    public Instant getDepatureDate() {
        return this.depatureDate;
    }

    public Voyage depatureDate(Instant depatureDate) {
        this.depatureDate = depatureDate;
        return this;
    }

    public void setDepatureDate(Instant depatureDate) {
        this.depatureDate = depatureDate;
    }

    public Instant getDepatureTime() {
        return this.depatureTime;
    }

    public Voyage depatureTime(Instant depatureTime) {
        this.depatureTime = depatureTime;
        return this;
    }

    public void setDepatureTime(Instant depatureTime) {
        this.depatureTime = depatureTime;
    }

    public String getArrivalCountry() {
        return this.arrivalCountry;
    }

    public Voyage arrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
        return this;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getArrivalAddress() {
        return this.arrivalAddress;
    }

    public Voyage arrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
        return this;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public String getCityArrival() {
        return this.cityArrival;
    }

    public Voyage cityArrival(String cityArrival) {
        this.cityArrival = cityArrival;
        return this;
    }

    public void setCityArrival(String cityArrival) {
        this.cityArrival = cityArrival;
    }

    public Instant getDateArrival() {
        return this.dateArrival;
    }

    public Voyage dateArrival(Instant dateArrival) {
        this.dateArrival = dateArrival;
        return this;
    }

    public void setDateArrival(Instant dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Instant getArrivalTime() {
        return this.arrivalTime;
    }

    public Voyage arrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getKilos() {
        return this.kilos;
    }

    public Voyage kilos(Integer kilos) {
        this.kilos = kilos;
        return this;
    }

    public void setKilos(Integer kilos) {
        this.kilos = kilos;
    }

    public Float getUnitPrice() {
        return this.unitPrice;
    }

    public Voyage unitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Voyage valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Billet getBillet() {
        return this.billet;
    }

    public Voyage billet(Billet billet) {
        this.setBillet(billet);
        return this;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public Set<Colis> getColis() {
        return this.colis;
    }

    public Voyage colis(Set<Colis> colis) {
        this.setColis(colis);
        return this;
    }

    public Voyage addColis(Colis colis) {
        this.colis.add(colis);
        colis.setVoyage(this);
        return this;
    }

    public Voyage removeColis(Colis colis) {
        this.colis.remove(colis);
        colis.setVoyage(null);
        return this;
    }

    public void setColis(Set<Colis> colis) {
        if (this.colis != null) {
            this.colis.forEach(i -> i.setVoyage(null));
        }
        if (colis != null) {
            colis.forEach(i -> i.setVoyage(this));
        }
        this.colis = colis;
    }

    public Voyageur getVoyageur() {
        return this.voyageur;
    }

    public Voyage voyageur(Voyageur voyageur) {
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
        if (!(o instanceof Voyage)) {
            return false;
        }
        return id != null && id.equals(((Voyage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voyage{" +
            "id=" + getId() +
            ", depatureCountry='" + getDepatureCountry() + "'" +
            ", depatureAddress='" + getDepatureAddress() + "'" +
            ", depatureCity='" + getDepatureCity() + "'" +
            ", depatureDate='" + getDepatureDate() + "'" +
            ", depatureTime='" + getDepatureTime() + "'" +
            ", arrivalCountry='" + getArrivalCountry() + "'" +
            ", arrivalAddress='" + getArrivalAddress() + "'" +
            ", cityArrival='" + getCityArrival() + "'" +
            ", dateArrival='" + getDateArrival() + "'" +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", kilos=" + getKilos() +
            ", unitPrice=" + getUnitPrice() +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
