package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Formulaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Formulaire entity.
 */
@SuppressWarnings("unused")
public interface FormulaireRepository extends JpaRepository<Formulaire,Long> {

}
