package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Solution;
import com.mycompany.myapp.service.SolutionService;
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
 * REST controller for managing Solution.
 */
@RestController
@RequestMapping("/api")
public class SolutionResource {

    private final Logger log = LoggerFactory.getLogger(SolutionResource.class);
        
    @Inject
    private SolutionService solutionService;

    /**
     * POST  /solutions : Create a new solution.
     *
     * @param solution the solution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solution, or with status 400 (Bad Request) if the solution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solutions")
    @Timed
    public ResponseEntity<Solution> createSolution(@RequestBody Solution solution) throws URISyntaxException {
        log.debug("REST request to save Solution : {}", solution);
        if (solution.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("solution", "idexists", "A new solution cannot already have an ID")).body(null);
        }
        Solution result = solutionService.save(solution);
        return ResponseEntity.created(new URI("/api/solutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("solution", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solutions : Updates an existing solution.
     *
     * @param solution the solution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solution,
     * or with status 400 (Bad Request) if the solution is not valid,
     * or with status 500 (Internal Server Error) if the solution couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solutions")
    @Timed
    public ResponseEntity<Solution> updateSolution(@RequestBody Solution solution) throws URISyntaxException {
        log.debug("REST request to update Solution : {}", solution);
        if (solution.getId() == null) {
            return createSolution(solution);
        }
        Solution result = solutionService.save(solution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("solution", solution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solutions : get all the solutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solutions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/solutions")
    @Timed
    public ResponseEntity<List<Solution>> getAllSolutions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Solutions");
        Page<Solution> page = solutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solutions/:id : get the "id" solution.
     *
     * @param id the id of the solution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solution, or with status 404 (Not Found)
     */
    @GetMapping("/solutions/{id}")
    @Timed
    public ResponseEntity<Solution> getSolution(@PathVariable Long id) {
        log.debug("REST request to get Solution : {}", id);
        Solution solution = solutionService.findOne(id);
        return Optional.ofNullable(solution)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /solutions/:id : delete the "id" solution.
     *
     * @param id the id of the solution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solutions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolution(@PathVariable Long id) {
        log.debug("REST request to delete Solution : {}", id);
        solutionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("solution", id.toString())).build();
    }

}
