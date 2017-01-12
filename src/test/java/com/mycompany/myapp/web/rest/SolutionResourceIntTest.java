package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjhipsterApp;

import com.mycompany.myapp.domain.Solution;
import com.mycompany.myapp.repository.SolutionRepository;
import com.mycompany.myapp.service.SolutionService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SolutionResource REST controller.
 *
 * @see SolutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestjhipsterApp.class)
public class SolutionResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COUT = "AAAAAAAAAA";
    private static final String UPDATED_COUT = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_VISE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_VISE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIF = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIF = "BBBBBBBBBB";

    private static final String DEFAULT_DELAI = "AAAAAAAAAA";
    private static final String UPDATED_DELAI = "BBBBBBBBBB";

    private static final Integer DEFAULT_BUDGET_MIN = 1;
    private static final Integer UPDATED_BUDGET_MIN = 2;

    private static final Integer DEFAULT_BUDGET_MAX = 1;
    private static final Integer UPDATED_BUDGET_MAX = 2;

    private static final Integer DEFAULT_NBR_PERSONNES_VISEES = 1;
    private static final Integer UPDATED_NBR_PERSONNES_VISEES = 2;

    @Inject
    private SolutionRepository solutionRepository;

    @Inject
    private SolutionService solutionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSolutionMockMvc;

    private Solution solution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SolutionResource solutionResource = new SolutionResource();
        ReflectionTestUtils.setField(solutionResource, "solutionService", solutionService);
        this.restSolutionMockMvc = MockMvcBuilders.standaloneSetup(solutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solution createEntity(EntityManager em) {
        Solution solution = new Solution()
                .titre(DEFAULT_TITRE)
                .description(DEFAULT_DESCRIPTION)
                .cout(DEFAULT_COUT)
                .publicVise(DEFAULT_PUBLIC_VISE)
                .objectif(DEFAULT_OBJECTIF)
                .delai(DEFAULT_DELAI)
                .budgetMin(DEFAULT_BUDGET_MIN)
                .budgetMax(DEFAULT_BUDGET_MAX)
                .nbrPersonnesVisees(DEFAULT_NBR_PERSONNES_VISEES);
        return solution;
    }

    @Before
    public void initTest() {
        solution = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolution() throws Exception {
        int databaseSizeBeforeCreate = solutionRepository.findAll().size();

        // Create the Solution

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isCreated());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeCreate + 1);
        Solution testSolution = solutionList.get(solutionList.size() - 1);
        assertThat(testSolution.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testSolution.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSolution.getCout()).isEqualTo(DEFAULT_COUT);
        assertThat(testSolution.getPublicVise()).isEqualTo(DEFAULT_PUBLIC_VISE);
        assertThat(testSolution.getObjectif()).isEqualTo(DEFAULT_OBJECTIF);
        assertThat(testSolution.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testSolution.getBudgetMin()).isEqualTo(DEFAULT_BUDGET_MIN);
        assertThat(testSolution.getBudgetMax()).isEqualTo(DEFAULT_BUDGET_MAX);
        assertThat(testSolution.getNbrPersonnesVisees()).isEqualTo(DEFAULT_NBR_PERSONNES_VISEES);
    }

    @Test
    @Transactional
    public void createSolutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solutionRepository.findAll().size();

        // Create the Solution with an existing ID
        Solution existingSolution = new Solution();
        existingSolution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSolution)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolutions() throws Exception {
        // Initialize the database
        solutionRepository.saveAndFlush(solution);

        // Get all the solutionList
        restSolutionMockMvc.perform(get("/api/solutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solution.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cout").value(hasItem(DEFAULT_COUT.toString())))
            .andExpect(jsonPath("$.[*].publicVise").value(hasItem(DEFAULT_PUBLIC_VISE.toString())))
            .andExpect(jsonPath("$.[*].objectif").value(hasItem(DEFAULT_OBJECTIF.toString())))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.toString())))
            .andExpect(jsonPath("$.[*].budgetMin").value(hasItem(DEFAULT_BUDGET_MIN)))
            .andExpect(jsonPath("$.[*].budgetMax").value(hasItem(DEFAULT_BUDGET_MAX)))
            .andExpect(jsonPath("$.[*].nbrPersonnesVisees").value(hasItem(DEFAULT_NBR_PERSONNES_VISEES)));
    }

    @Test
    @Transactional
    public void getSolution() throws Exception {
        // Initialize the database
        solutionRepository.saveAndFlush(solution);

        // Get the solution
        restSolutionMockMvc.perform(get("/api/solutions/{id}", solution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solution.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cout").value(DEFAULT_COUT.toString()))
            .andExpect(jsonPath("$.publicVise").value(DEFAULT_PUBLIC_VISE.toString()))
            .andExpect(jsonPath("$.objectif").value(DEFAULT_OBJECTIF.toString()))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.toString()))
            .andExpect(jsonPath("$.budgetMin").value(DEFAULT_BUDGET_MIN))
            .andExpect(jsonPath("$.budgetMax").value(DEFAULT_BUDGET_MAX))
            .andExpect(jsonPath("$.nbrPersonnesVisees").value(DEFAULT_NBR_PERSONNES_VISEES));
    }

    @Test
    @Transactional
    public void getNonExistingSolution() throws Exception {
        // Get the solution
        restSolutionMockMvc.perform(get("/api/solutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolution() throws Exception {
        // Initialize the database
        solutionService.save(solution);

        int databaseSizeBeforeUpdate = solutionRepository.findAll().size();

        // Update the solution
        Solution updatedSolution = solutionRepository.findOne(solution.getId());
        updatedSolution
                .titre(UPDATED_TITRE)
                .description(UPDATED_DESCRIPTION)
                .cout(UPDATED_COUT)
                .publicVise(UPDATED_PUBLIC_VISE)
                .objectif(UPDATED_OBJECTIF)
                .delai(UPDATED_DELAI)
                .budgetMin(UPDATED_BUDGET_MIN)
                .budgetMax(UPDATED_BUDGET_MAX)
                .nbrPersonnesVisees(UPDATED_NBR_PERSONNES_VISEES);

        restSolutionMockMvc.perform(put("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolution)))
            .andExpect(status().isOk());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeUpdate);
        Solution testSolution = solutionList.get(solutionList.size() - 1);
        assertThat(testSolution.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testSolution.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSolution.getCout()).isEqualTo(UPDATED_COUT);
        assertThat(testSolution.getPublicVise()).isEqualTo(UPDATED_PUBLIC_VISE);
        assertThat(testSolution.getObjectif()).isEqualTo(UPDATED_OBJECTIF);
        assertThat(testSolution.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testSolution.getBudgetMin()).isEqualTo(UPDATED_BUDGET_MIN);
        assertThat(testSolution.getBudgetMax()).isEqualTo(UPDATED_BUDGET_MAX);
        assertThat(testSolution.getNbrPersonnesVisees()).isEqualTo(UPDATED_NBR_PERSONNES_VISEES);
    }

    @Test
    @Transactional
    public void updateNonExistingSolution() throws Exception {
        int databaseSizeBeforeUpdate = solutionRepository.findAll().size();

        // Create the Solution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolutionMockMvc.perform(put("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isCreated());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolution() throws Exception {
        // Initialize the database
        solutionService.save(solution);

        int databaseSizeBeforeDelete = solutionRepository.findAll().size();

        // Get the solution
        restSolutionMockMvc.perform(delete("/api/solutions/{id}", solution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
