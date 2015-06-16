package com.pacpontos.web.rest;

import com.pacpontos.Application;
import com.pacpontos.domain.Carimbo;
import com.pacpontos.repository.CarimboRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarimboResource REST controller.
 *
 * @see CarimboResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CarimboResourceTest {


    @Inject
    private CarimboRepository carimboRepository;

    private MockMvc restCarimboMockMvc;

    private Carimbo carimbo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarimboResource carimboResource = new CarimboResource();
        ReflectionTestUtils.setField(carimboResource, "carimboRepository", carimboRepository);
        this.restCarimboMockMvc = MockMvcBuilders.standaloneSetup(carimboResource).build();
    }

    @Before
    public void initTest() {
        carimbo = new Carimbo();
    }

    @Test
    @Transactional
    public void createCarimbo() throws Exception {
        int databaseSizeBeforeCreate = carimboRepository.findAll().size();

        // Create the Carimbo
        restCarimboMockMvc.perform(post("/api/carimbos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carimbo)))
                .andExpect(status().isCreated());

        // Validate the Carimbo in the database
        List<Carimbo> carimbos = carimboRepository.findAll();
        assertThat(carimbos).hasSize(databaseSizeBeforeCreate + 1);
        Carimbo testCarimbo = carimbos.get(carimbos.size() - 1);
    }

    @Test
    @Transactional
    public void getAllCarimbos() throws Exception {
        // Initialize the database
        carimboRepository.saveAndFlush(carimbo);

        // Get all the carimbos
        restCarimboMockMvc.perform(get("/api/carimbos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carimbo.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCarimbo() throws Exception {
        // Initialize the database
        carimboRepository.saveAndFlush(carimbo);

        // Get the carimbo
        restCarimboMockMvc.perform(get("/api/carimbos/{id}", carimbo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carimbo.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarimbo() throws Exception {
        // Get the carimbo
        restCarimboMockMvc.perform(get("/api/carimbos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarimbo() throws Exception {
        // Initialize the database
        carimboRepository.saveAndFlush(carimbo);

		int databaseSizeBeforeUpdate = carimboRepository.findAll().size();

        // Update the carimbo
        restCarimboMockMvc.perform(put("/api/carimbos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carimbo)))
                .andExpect(status().isOk());

        // Validate the Carimbo in the database
        List<Carimbo> carimbos = carimboRepository.findAll();
        assertThat(carimbos).hasSize(databaseSizeBeforeUpdate);
        Carimbo testCarimbo = carimbos.get(carimbos.size() - 1);
    }

    @Test
    @Transactional
    public void deleteCarimbo() throws Exception {
        // Initialize the database
        carimboRepository.saveAndFlush(carimbo);

		int databaseSizeBeforeDelete = carimboRepository.findAll().size();

        // Get the carimbo
        restCarimboMockMvc.perform(delete("/api/carimbos/{id}", carimbo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Carimbo> carimbos = carimboRepository.findAll();
        assertThat(carimbos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
