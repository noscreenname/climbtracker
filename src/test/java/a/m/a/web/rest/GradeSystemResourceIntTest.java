package a.m.a.web.rest;

import a.m.a.Application;
import a.m.a.domain.GradeSystem;
import a.m.a.repository.GradeSystemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
 * Test class for the GradeSystemResource REST controller.
 *
 * @see GradeSystemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GradeSystemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private GradeSystemRepository gradeSystemRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGradeSystemMockMvc;

    private GradeSystem gradeSystem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GradeSystemResource gradeSystemResource = new GradeSystemResource();
        ReflectionTestUtils.setField(gradeSystemResource, "gradeSystemRepository", gradeSystemRepository);
        this.restGradeSystemMockMvc = MockMvcBuilders.standaloneSetup(gradeSystemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gradeSystem = new GradeSystem();
        gradeSystem.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGradeSystem() throws Exception {
        int databaseSizeBeforeCreate = gradeSystemRepository.findAll().size();

        // Create the GradeSystem

        restGradeSystemMockMvc.perform(post("/api/gradeSystems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradeSystem)))
                .andExpect(status().isCreated());

        // Validate the GradeSystem in the database
        List<GradeSystem> gradeSystems = gradeSystemRepository.findAll();
        assertThat(gradeSystems).hasSize(databaseSizeBeforeCreate + 1);
        GradeSystem testGradeSystem = gradeSystems.get(gradeSystems.size() - 1);
        assertThat(testGradeSystem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllGradeSystems() throws Exception {
        // Initialize the database
        gradeSystemRepository.saveAndFlush(gradeSystem);

        // Get all the gradeSystems
        restGradeSystemMockMvc.perform(get("/api/gradeSystems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gradeSystem.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGradeSystem() throws Exception {
        // Initialize the database
        gradeSystemRepository.saveAndFlush(gradeSystem);

        // Get the gradeSystem
        restGradeSystemMockMvc.perform(get("/api/gradeSystems/{id}", gradeSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gradeSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGradeSystem() throws Exception {
        // Get the gradeSystem
        restGradeSystemMockMvc.perform(get("/api/gradeSystems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradeSystem() throws Exception {
        // Initialize the database
        gradeSystemRepository.saveAndFlush(gradeSystem);

		int databaseSizeBeforeUpdate = gradeSystemRepository.findAll().size();

        // Update the gradeSystem
        gradeSystem.setName(UPDATED_NAME);

        restGradeSystemMockMvc.perform(put("/api/gradeSystems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradeSystem)))
                .andExpect(status().isOk());

        // Validate the GradeSystem in the database
        List<GradeSystem> gradeSystems = gradeSystemRepository.findAll();
        assertThat(gradeSystems).hasSize(databaseSizeBeforeUpdate);
        GradeSystem testGradeSystem = gradeSystems.get(gradeSystems.size() - 1);
        assertThat(testGradeSystem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteGradeSystem() throws Exception {
        // Initialize the database
        gradeSystemRepository.saveAndFlush(gradeSystem);

		int databaseSizeBeforeDelete = gradeSystemRepository.findAll().size();

        // Get the gradeSystem
        restGradeSystemMockMvc.perform(delete("/api/gradeSystems/{id}", gradeSystem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GradeSystem> gradeSystems = gradeSystemRepository.findAll();
        assertThat(gradeSystems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
