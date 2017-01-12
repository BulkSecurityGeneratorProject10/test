package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Solution;
import com.mycompany.myapp.repository.SolutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Solution.
 */
@Service
@Transactional
public class SolutionService {

    private final Logger log = LoggerFactory.getLogger(SolutionService.class);
    
    @Inject
    private SolutionRepository solutionRepository;

    /**
     * Save a solution.
     *
     * @param solution the entity to save
     * @return the persisted entity
     */
    public Solution save(Solution solution) {
        log.debug("Request to save Solution : {}", solution);
        Solution result = solutionRepository.save(solution);
        return result;
    }

    /**
     *  Get all the solutions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Solution> findAll(Pageable pageable) {
        log.debug("Request to get all Solutions");
        Page<Solution> result = solutionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one solution by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Solution findOne(Long id) {
        log.debug("Request to get Solution : {}", id);
        Solution solution = solutionRepository.findOne(id);
        return solution;
    }

    /**
     *  Delete the  solution by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Solution : {}", id);
        solutionRepository.delete(id);
    }
}
