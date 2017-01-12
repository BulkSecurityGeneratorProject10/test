package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Solution.
 */
@Entity
@Table(name = "solution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Solution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "cout")
    private String cout;

    @Column(name = "public_vise")
    private String publicVise;

    @Column(name = "objectif")
    private String objectif;

    @Column(name = "delai")
    private String delai;

    @Column(name = "budget_min")
    private Integer budgetMin;

    @Column(name = "budget_max")
    private Integer budgetMax;

    @Column(name = "nbr_personnes_visees")
    private Integer nbrPersonnesVisees;

    @ManyToOne
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Solution titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public Solution description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCout() {
        return cout;
    }

    public Solution cout(String cout) {
        this.cout = cout;
        return this;
    }

    public void setCout(String cout) {
        this.cout = cout;
    }

    public String getPublicVise() {
        return publicVise;
    }

    public Solution publicVise(String publicVise) {
        this.publicVise = publicVise;
        return this;
    }

    public void setPublicVise(String publicVise) {
        this.publicVise = publicVise;
    }

    public String getObjectif() {
        return objectif;
    }

    public Solution objectif(String objectif) {
        this.objectif = objectif;
        return this;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getDelai() {
        return delai;
    }

    public Solution delai(String delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(String delai) {
        this.delai = delai;
    }

    public Integer getBudgetMin() {
        return budgetMin;
    }

    public Solution budgetMin(Integer budgetMin) {
        this.budgetMin = budgetMin;
        return this;
    }

    public void setBudgetMin(Integer budgetMin) {
        this.budgetMin = budgetMin;
    }

    public Integer getBudgetMax() {
        return budgetMax;
    }

    public Solution budgetMax(Integer budgetMax) {
        this.budgetMax = budgetMax;
        return this;
    }

    public void setBudgetMax(Integer budgetMax) {
        this.budgetMax = budgetMax;
    }

    public Integer getNbrPersonnesVisees() {
        return nbrPersonnesVisees;
    }

    public Solution nbrPersonnesVisees(Integer nbrPersonnesVisees) {
        this.nbrPersonnesVisees = nbrPersonnesVisees;
        return this;
    }

    public void setNbrPersonnesVisees(Integer nbrPersonnesVisees) {
        this.nbrPersonnesVisees = nbrPersonnesVisees;
    }

    public Contact getContact() {
        return contact;
    }

    public Solution contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Solution solution = (Solution) o;
        if (solution.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, solution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Solution{" +
            "id=" + id +
            ", titre='" + titre + "'" +
            ", description='" + description + "'" +
            ", cout='" + cout + "'" +
            ", publicVise='" + publicVise + "'" +
            ", objectif='" + objectif + "'" +
            ", delai='" + delai + "'" +
            ", budgetMin='" + budgetMin + "'" +
            ", budgetMax='" + budgetMax + "'" +
            ", nbrPersonnesVisees='" + nbrPersonnesVisees + "'" +
            '}';
    }
}
