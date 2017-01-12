package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Formulaire;
import com.mycompany.myapp.repository.FormulaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Formulaire.
 */
@Service
@Transactional
public class FormulaireService {

    private final Logger log = LoggerFactory.getLogger(FormulaireService.class);
    
    @Inject
    private FormulaireRepository formulaireRepository;

    /**
     * Save a formulaire.
     *
     * @param formulaire the entity to save
     * @return the persisted entity
     */
    public Formulaire save(Formulaire formulaire) {
        log.debug("Request to save Formulaire : {}", formulaire);
        Formulaire result = formulaireRepository.save(formulaire);
        return result;
    }

    /**
     *  Get all the formulaires.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Formulaire> findAll(Pageable pageable) {
        log.debug("Request to get all Formulaires");
        Page<Formulaire> result = formulaireRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one formulaire by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Formulaire findOne(Long id) {
        log.debug("Request to get Formulaire : {}", id);
        Formulaire formulaire = formulaireRepository.findOne(id);
        return formulaire;
    }

    /**
     *  Delete the  formulaire by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Formulaire : {}", id);
        formulaireRepository.delete(id);
    }
}
