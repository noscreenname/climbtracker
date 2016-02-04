package a.m.a.web.rest;

import a.m.a.Application;
import a.m.a.domain.Crag;
import a.m.a.repository.CragRepository;

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
 * Test class for the CragResource REST controller.
 *
 * @see CragResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CragResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private CragRepository cragRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCragMockMvc;

    private Crag crag;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CragResource cragResource = new CragResource();
        ReflectionTestUtils.setField(cragResource, "cragRepository", cragRepository);
        this.restCragMockMvc = MockMvcBuilders.standaloneSetup(cragResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        crag = new Crag();
        crag.setName(DEFAULT_NAME);
        crag.setLocation(DEFAULT_LOCATION);
        crag.setAddress(DEFAULT_ADDRESS);
        crag.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCrag() throws Exception {
        int databaseSizeBeforeCreate = cragRepository.findAll().size();

        // Create the Crag

        restCragMockMvc.perform(post("/api/crags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(crag)))
                .andExpect(status().isCreated());

        // Validate the Crag in the database
        List<Crag> crags = cragRepository.findAll();
        assertThat(crags).hasSize(databaseSizeBeforeCreate + 1);
        Crag testCrag = crags.get(crags.size() - 1);
        assertThat(testCrag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrag.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCrag.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCrag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cragRepository.findAll().size();
        // set the field null
        crag.setName(null);

        // Create the Crag, which fails.

        restCragMockMvc.perform(post("/api/crags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(crag)))
                .andExpect(status().isBadRequest());

        List<Crag> crags = cragRepository.findAll();
        assertThat(crags).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCrags() throws Exception {
        // Initialize the database
        cragRepository.saveAndFlush(crag);

        // Get all the crags
        restCragMockMvc.perform(get("/api/crags?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(crag.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCrag() throws Exception {
        // Initialize the database
        cragRepository.saveAndFlush(crag);

        // Get the crag
        restCragMockMvc.perform(get("/api/crags/{id}", crag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(crag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrag() throws Exception {
        // Get the crag
        restCragMockMvc.perform(get("/api/crags/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrag() throws Exception {
        // Initialize the database
        cragRepository.saveAndFlush(crag);

		int databaseSizeBeforeUpdate = cragRepository.findAll().size();

        // Update the crag
        crag.setName(UPDATED_NAME);
        crag.setLocation(UPDATED_LOCATION);
        crag.setAddress(UPDATED_ADDRESS);
        crag.setDescription(UPDATED_DESCRIPTION);

        restCragMockMvc.perform(put("/api/crags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(crag)))
                .andExpect(status().isOk());

        // Validate the Crag in the database
        List<Crag> crags = cragRepository.findAll();
        assertThat(crags).hasSize(databaseSizeBeforeUpdate);
        Crag testCrag = crags.get(crags.size() - 1);
        assertThat(testCrag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrag.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCrag.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCrag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCrag() throws Exception {
        // Initialize the database
        cragRepository.saveAndFlush(crag);

		int databaseSizeBeforeDelete = cragRepository.findAll().size();

        // Get the crag
        restCragMockMvc.perform(delete("/api/crags/{id}", crag.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Crag> crags = cragRepository.findAll();
        assertThat(crags).hasSize(databaseSizeBeforeDelete - 1);
    }
}
