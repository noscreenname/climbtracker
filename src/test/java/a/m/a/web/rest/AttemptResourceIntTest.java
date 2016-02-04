package a.m.a.web.rest;

import a.m.a.Application;
import a.m.a.domain.Attempt;
import a.m.a.repository.AttemptRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AttemptResource REST controller.
 *
 * @see AttemptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AttemptResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_NB_FAIL = 0;
    private static final Integer UPDATED_NB_FAIL = 1;

    private static final Integer DEFAULT_NB_SUCCESS = 0;
    private static final Integer UPDATED_NB_SUCCESS = 1;

    @Inject
    private AttemptRepository attemptRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAttemptMockMvc;

    private Attempt attempt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttemptResource attemptResource = new AttemptResource();
        ReflectionTestUtils.setField(attemptResource, "attemptRepository", attemptRepository);
        this.restAttemptMockMvc = MockMvcBuilders.standaloneSetup(attemptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        attempt = new Attempt();
        attempt.setDate(DEFAULT_DATE);
        attempt.setDescription(DEFAULT_DESCRIPTION);
        attempt.setNbFail(DEFAULT_NB_FAIL);
        attempt.setNbSuccess(DEFAULT_NB_SUCCESS);
    }

    @Test
    @Transactional
    public void createAttempt() throws Exception {
        int databaseSizeBeforeCreate = attemptRepository.findAll().size();

        // Create the Attempt

        restAttemptMockMvc.perform(post("/api/attempts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attempt)))
                .andExpect(status().isCreated());

        // Validate the Attempt in the database
        List<Attempt> attempts = attemptRepository.findAll();
        assertThat(attempts).hasSize(databaseSizeBeforeCreate + 1);
        Attempt testAttempt = attempts.get(attempts.size() - 1);
        assertThat(testAttempt.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAttempt.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAttempt.getNbFail()).isEqualTo(DEFAULT_NB_FAIL);
        assertThat(testAttempt.getNbSuccess()).isEqualTo(DEFAULT_NB_SUCCESS);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attemptRepository.findAll().size();
        // set the field null
        attempt.setDate(null);

        // Create the Attempt, which fails.

        restAttemptMockMvc.perform(post("/api/attempts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attempt)))
                .andExpect(status().isBadRequest());

        List<Attempt> attempts = attemptRepository.findAll();
        assertThat(attempts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbFailIsRequired() throws Exception {
        int databaseSizeBeforeTest = attemptRepository.findAll().size();
        // set the field null
        attempt.setNbFail(null);

        // Create the Attempt, which fails.

        restAttemptMockMvc.perform(post("/api/attempts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attempt)))
                .andExpect(status().isBadRequest());

        List<Attempt> attempts = attemptRepository.findAll();
        assertThat(attempts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbSuccessIsRequired() throws Exception {
        int databaseSizeBeforeTest = attemptRepository.findAll().size();
        // set the field null
        attempt.setNbSuccess(null);

        // Create the Attempt, which fails.

        restAttemptMockMvc.perform(post("/api/attempts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attempt)))
                .andExpect(status().isBadRequest());

        List<Attempt> attempts = attemptRepository.findAll();
        assertThat(attempts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttempts() throws Exception {
        // Initialize the database
        attemptRepository.saveAndFlush(attempt);

        // Get all the attempts
        restAttemptMockMvc.perform(get("/api/attempts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(attempt.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].nbFail").value(hasItem(DEFAULT_NB_FAIL)))
                .andExpect(jsonPath("$.[*].nbSuccess").value(hasItem(DEFAULT_NB_SUCCESS)));
    }

    @Test
    @Transactional
    public void getAttempt() throws Exception {
        // Initialize the database
        attemptRepository.saveAndFlush(attempt);

        // Get the attempt
        restAttemptMockMvc.perform(get("/api/attempts/{id}", attempt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(attempt.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.nbFail").value(DEFAULT_NB_FAIL))
            .andExpect(jsonPath("$.nbSuccess").value(DEFAULT_NB_SUCCESS));
    }

    @Test
    @Transactional
    public void getNonExistingAttempt() throws Exception {
        // Get the attempt
        restAttemptMockMvc.perform(get("/api/attempts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttempt() throws Exception {
        // Initialize the database
        attemptRepository.saveAndFlush(attempt);

		int databaseSizeBeforeUpdate = attemptRepository.findAll().size();

        // Update the attempt
        attempt.setDate(UPDATED_DATE);
        attempt.setDescription(UPDATED_DESCRIPTION);
        attempt.setNbFail(UPDATED_NB_FAIL);
        attempt.setNbSuccess(UPDATED_NB_SUCCESS);

        restAttemptMockMvc.perform(put("/api/attempts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attempt)))
                .andExpect(status().isOk());

        // Validate the Attempt in the database
        List<Attempt> attempts = attemptRepository.findAll();
        assertThat(attempts).hasSize(databaseSizeBeforeUpdate);
        Attempt testAttempt = attempts.get(attempts.size() - 1);
        assertThat(testAttempt.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttempt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAttempt.getNbFail()).isEqualTo(UPDATED_NB_FAIL);
        assertThat(testAttempt.getNbSuccess()).isEqualTo(UPDATED_NB_SUCCESS);
    }

    @Test
    @Transactional
    public void deleteAttempt() throws Exception {
        // Initialize the database
        attemptRepository.saveAndFlush(attempt);

		int databaseSizeBeforeDelete = attemptRepository.findAll().size();

        // Get the attempt
        restAttemptMockMvc.perform(delete("/api/attempts/{id}", attempt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Attempt> attempts = attemptRepository.findAll();
        assertThat(attempts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
