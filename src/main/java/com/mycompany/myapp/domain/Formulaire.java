package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Formulaire.
 */
@Entity
@Table(name = "formulaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Formulaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Solution solution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solution getSolution() {
        return solution;
    }

    public Formulaire solution(Solution solution) {
        this.solution = solution;
        return this;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formulaire formulaire = (Formulaire) o;
        if (formulaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, formulaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Formulaire{" +
            "id=" + id +
            '}';
    }
}
