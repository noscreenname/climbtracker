package a.m.a.web.rest;

import a.m.a.Application;
import a.m.a.domain.RouteType;
import a.m.a.repository.RouteTypeRepository;

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
 * Test class for the RouteTypeResource REST controller.
 *
 * @see RouteTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RouteTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    @Inject
    private RouteTypeRepository routeTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRouteTypeMockMvc;

    private RouteType routeType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RouteTypeResource routeTypeResource = new RouteTypeResource();
        ReflectionTestUtils.setField(routeTypeResource, "routeTypeRepository", routeTypeRepository);
        this.restRouteTypeMockMvc = MockMvcBuilders.standaloneSetup(routeTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        routeType = new RouteType();
        routeType.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createRouteType() throws Exception {
        int databaseSizeBeforeCreate = routeTypeRepository.findAll().size();

        // Create the RouteType

        restRouteTypeMockMvc.perform(post("/api/routeTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(routeType)))
                .andExpect(status().isCreated());

        // Validate the RouteType in the database
        List<RouteType> routeTypes = routeTypeRepository.findAll();
        assertThat(routeTypes).hasSize(databaseSizeBeforeCreate + 1);
        RouteType testRouteType = routeTypes.get(routeTypes.size() - 1);
        assertThat(testRouteType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = routeTypeRepository.findAll().size();
        // set the field null
        routeType.setType(null);

        // Create the RouteType, which fails.

        restRouteTypeMockMvc.perform(post("/api/routeTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(routeType)))
                .andExpect(status().isBadRequest());

        List<RouteType> routeTypes = routeTypeRepository.findAll();
        assertThat(routeTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRouteTypes() throws Exception {
        // Initialize the database
        routeTypeRepository.saveAndFlush(routeType);

        // Get all the routeTypes
        restRouteTypeMockMvc.perform(get("/api/routeTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(routeType.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getRouteType() throws Exception {
        // Initialize the database
        routeTypeRepository.saveAndFlush(routeType);

        // Get the routeType
        restRouteTypeMockMvc.perform(get("/api/routeTypes/{id}", routeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(routeType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRouteType() throws Exception {
        // Get the routeType
        restRouteTypeMockMvc.perform(get("/api/routeTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRouteType() throws Exception {
        // Initialize the database
        routeTypeRepository.saveAndFlush(routeType);

		int databaseSizeBeforeUpdate = routeTypeRepository.findAll().size();

        // Update the routeType
        routeType.setType(UPDATED_TYPE);

        restRouteTypeMockMvc.perform(put("/api/routeTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(routeType)))
                .andExpect(status().isOk());

        // Validate the RouteType in the database
        List<RouteType> routeTypes = routeTypeRepository.findAll();
        assertThat(routeTypes).hasSize(databaseSizeBeforeUpdate);
        RouteType testRouteType = routeTypes.get(routeTypes.size() - 1);
        assertThat(testRouteType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteRouteType() throws Exception {
        // Initialize the database
        routeTypeRepository.saveAndFlush(routeType);

		int databaseSizeBeforeDelete = routeTypeRepository.findAll().size();

        // Get the routeType
        restRouteTypeMockMvc.perform(delete("/api/routeTypes/{id}", routeType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RouteType> routeTypes = routeTypeRepository.findAll();
        assertThat(routeTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
