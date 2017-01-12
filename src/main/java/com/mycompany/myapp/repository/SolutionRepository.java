package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Solution;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Solution entity.
 */
@SuppressWarnings("unused")
public interface SolutionRepository extends JpaRepository<Solution,Long> {

}
