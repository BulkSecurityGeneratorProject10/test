package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjhipsterApp;

import com.mycompany.myapp.domain.Formulaire;
import com.mycompany.myapp.repository.FormulaireRepository;
import com.mycompany.myapp.service.FormulaireService;

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
 * Test class for the FormulaireResource REST controller.
 *
 * @see FormulaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestjhipsterApp.class)
public class FormulaireResourceIntTest {

    @Inject
    private FormulaireRepository formulaireRepository;

    @Inject
    private FormulaireService formulaireService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFormulaireMockMvc;

    private Formulaire formulaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormulaireResource formulaireResource = new FormulaireResource();
        ReflectionTestUtils.setField(formulaireResource, "formulaireService", formulaireService);
        this.restFormulaireMockMvc = MockMvcBuilders.standaloneSetup(formulaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formulaire createEntity(EntityManager em) {
        Formulaire formulaire = new Formulaire();
        return formulaire;
    }

    @Before
    public void initTest() {
        formulaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormulaire() throws Exception {
        int databaseSizeBeforeCreate = formulaireRepository.findAll().size();

        // Create the Formulaire

        restFormulaireMockMvc.perform(post("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formulaire)))
            .andExpect(status().isCreated());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeCreate + 1);
        Formulaire testFormulaire = formulaireList.get(formulaireList.size() - 1);
    }

    @Test
    @Transactional
    public void createFormulaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formulaireRepository.findAll().size();

        // Create the Formulaire with an existing ID
        Formulaire existingFormulaire = new Formulaire();
        existingFormulaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormulaireMockMvc.perform(post("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFormulaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormulaires() throws Exception {
        // Initialize the database
        formulaireRepository.saveAndFlush(formulaire);

        // Get all the formulaireList
        restFormulaireMockMvc.perform(get("/api/formulaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formulaire.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFormulaire() throws Exception {
        // Initialize the database
        formulaireRepository.saveAndFlush(formulaire);

        // Get the formulaire
        restFormulaireMockMvc.perform(get("/api/formulaires/{id}", formulaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formulaire.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFormulaire() throws Exception {
        // Get the formulaire
        restFormulaireMockMvc.perform(get("/api/formulaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormulaire() throws Exception {
        // Initialize the database
        formulaireService.save(formulaire);

        int databaseSizeBeforeUpdate = formulaireRepository.findAll().size();

        // Update the formulaire
        Formulaire updatedFormulaire = formulaireRepository.findOne(formulaire.getId());

        restFormulaireMockMvc.perform(put("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormulaire)))
            .andExpect(status().isOk());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeUpdate);
        Formulaire testFormulaire = formulaireList.get(formulaireList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFormulaire() throws Exception {
        int databaseSizeBeforeUpdate = formulaireRepository.findAll().size();

        // Create the Formulaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormulaireMockMvc.perform(put("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formulaire)))
            .andExpect(status().isCreated());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormulaire() throws Exception {
        // Initialize the database
        formulaireService.save(formulaire);

        int databaseSizeBeforeDelete = formulaireRepository.findAll().size();

        // Get the formulaire
        restFormulaireMockMvc.perform(delete("/api/formulaires/{id}", formulaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
