package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Formulaire;
import com.mycompany.myapp.service.FormulaireService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Formulaire.
 */
@RestController
@RequestMapping("/api")
public class FormulaireResource {

    private final Logger log = LoggerFactory.getLogger(FormulaireResource.class);
        
    @Inject
    private FormulaireService formulaireService;

    /**
     * POST  /formulaires : Create a new formulaire.
     *
     * @param formulaire the formulaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formulaire, or with status 400 (Bad Request) if the formulaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formulaires")
    @Timed
    public ResponseEntity<Formulaire> createFormulaire(@RequestBody Formulaire formulaire) throws URISyntaxException {
        log.debug("REST request to save Formulaire : {}", formulaire);
        if (formulaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("formulaire", "idexists", "A new formulaire cannot already have an ID")).body(null);
        }
        Formulaire result = formulaireService.save(formulaire);
        return ResponseEntity.created(new URI("/api/formulaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("formulaire", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formulaires : Updates an existing formulaire.
     *
     * @param formulaire the formulaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formulaire,
     * or with status 400 (Bad Request) if the formulaire is not valid,
     * or with status 500 (Internal Server Error) if the formulaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formulaires")
    @Timed
    public ResponseEntity<Formulaire> updateFormulaire(@RequestBody Formulaire formulaire) throws URISyntaxException {
        log.debug("REST request to update Formulaire : {}", formulaire);
        if (formulaire.getId() == null) {
            return createFormulaire(formulaire);
        }
        Formulaire result = formulaireService.save(formulaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("formulaire", formulaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formulaires : get all the formulaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formulaires in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/formulaires")
    @Timed
    public ResponseEntity<List<Formulaire>> getAllFormulaires(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Formulaires");
        Page<Formulaire> page = formulaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/formulaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /formulaires/:id : get the "id" formulaire.
     *
     * @param id the id of the formulaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formulaire, or with status 404 (Not Found)
     */
    @GetMapping("/formulaires/{id}")
    @Timed
    public ResponseEntity<Formulaire> getFormulaire(@PathVariable Long id) {
        log.debug("REST request to get Formulaire : {}", id);
        Formulaire formulaire = formulaireService.findOne(id);
        return Optional.ofNullable(formulaire)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /formulaires/:id : delete the "id" formulaire.
     *
     * @param id the id of the formulaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formulaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormulaire(@PathVariable Long id) {
        log.debug("REST request to delete Formulaire : {}", id);
        formulaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("formulaire", id.toString())).build();
    }

}
