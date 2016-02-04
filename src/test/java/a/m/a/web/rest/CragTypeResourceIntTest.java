package a.m.a.web.rest;

import a.m.a.Application;
import a.m.a.domain.CragType;
import a.m.a.repository.CragTypeRepository;

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
 * Test class for the CragTypeResource REST controller.
 *
 * @see CragTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CragTypeResourceIntTest {


    private static final Boolean DEFAULT_INDOOR = false;
    private static final Boolean UPDATED_INDOOR = true;

    private static final Boolean DEFAULT_OUTDOOR = false;
    private static final Boolean UPDATED_OUTDOOR = true;

    @Inject
    private CragTypeRepository cragTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCragTypeMockMvc;

    private CragType cragType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CragTypeResource cragTypeResource = new CragTypeResource();
        ReflectionTestUtils.setField(cragTypeResource, "cragTypeRepository", cragTypeRepository);
        this.restCragTypeMockMvc = MockMvcBuilders.standaloneSetup(cragTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cragType = new CragType();
        cragType.setIndoor(DEFAULT_INDOOR);
        cragType.setOutdoor(DEFAULT_OUTDOOR);
    }

    @Test
    @Transactional
    public void createCragType() throws Exception {
        int databaseSizeBeforeCreate = cragTypeRepository.findAll().size();

        // Create the CragType

        restCragTypeMockMvc.perform(post("/api/cragTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cragType)))
                .andExpect(status().isCreated());

        // Validate the CragType in the database
        List<CragType> cragTypes = cragTypeRepository.findAll();
        assertThat(cragTypes).hasSize(databaseSizeBeforeCreate + 1);
        CragType testCragType = cragTypes.get(cragTypes.size() - 1);
        assertThat(testCragType.getIndoor()).isEqualTo(DEFAULT_INDOOR);
        assertThat(testCragType.getOutdoor()).isEqualTo(DEFAULT_OUTDOOR);
    }

    @Test
    @Transactional
    public void getAllCragTypes() throws Exception {
        // Initialize the database
        cragTypeRepository.saveAndFlush(cragType);

        // Get all the cragTypes
        restCragTypeMockMvc.perform(get("/api/cragTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cragType.getId().intValue())))
                .andExpect(jsonPath("$.[*].indoor").value(hasItem(DEFAULT_INDOOR.booleanValue())))
                .andExpect(jsonPath("$.[*].outdoor").value(hasItem(DEFAULT_OUTDOOR.booleanValue())));
    }

    @Test
    @Transactional
    public void getCragType() throws Exception {
        // Initialize the database
        cragTypeRepository.saveAndFlush(cragType);

        // Get the cragType
        restCragTypeMockMvc.perform(get("/api/cragTypes/{id}", cragType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cragType.getId().intValue()))
            .andExpect(jsonPath("$.indoor").value(DEFAULT_INDOOR.booleanValue()))
            .andExpect(jsonPath("$.outdoor").value(DEFAULT_OUTDOOR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCragType() throws Exception {
        // Get the cragType
        restCragTypeMockMvc.perform(get("/api/cragTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCragType() throws Exception {
        // Initialize the database
        cragTypeRepository.saveAndFlush(cragType);

		int databaseSizeBeforeUpdate = cragTypeRepository.findAll().size();

        // Update the cragType
        cragType.setIndoor(UPDATED_INDOOR);
        cragType.setOutdoor(UPDATED_OUTDOOR);

        restCragTypeMockMvc.perform(put("/api/cragTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cragType)))
                .andExpect(status().isOk());

        // Validate the CragType in the database
        List<CragType> cragTypes = cragTypeRepository.findAll();
        assertThat(cragTypes).hasSize(databaseSizeBeforeUpdate);
        CragType testCragType = cragTypes.get(cragTypes.size() - 1);
        assertThat(testCragType.getIndoor()).isEqualTo(UPDATED_INDOOR);
        assertThat(testCragType.getOutdoor()).isEqualTo(UPDATED_OUTDOOR);
    }

    @Test
    @Transactional
    public void deleteCragType() throws Exception {
        // Initialize the database
        cragTypeRepository.saveAndFlush(cragType);

		int databaseSizeBeforeDelete = cragTypeRepository.findAll().size();

        // Get the cragType
        restCragTypeMockMvc.perform(delete("/api/cragTypes/{id}", cragType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CragType> cragTypes = cragTypeRepository.findAll();
        assertThat(cragTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
