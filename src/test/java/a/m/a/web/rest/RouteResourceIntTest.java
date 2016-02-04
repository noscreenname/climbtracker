package a.m.a.web.rest;

import a.m.a.Application;
import a.m.a.domain.Route;
import a.m.a.repository.RouteRepository;

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
 * Test class for the RouteResource REST controller.
 *
 * @see RouteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RouteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_OPEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_OPEN = false;
    private static final Boolean UPDATED_IS_OPEN = true;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private RouteRepository routeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRouteMockMvc;

    private Route route;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RouteResource routeResource = new RouteResource();
        ReflectionTestUtils.setField(routeResource, "routeRepository", routeRepository);
        this.restRouteMockMvc = MockMvcBuilders.standaloneSetup(routeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        route = new Route();
        route.setName(DEFAULT_NAME);
        route.setOpenDate(DEFAULT_OPEN_DATE);
        route.setIsOpen(DEFAULT_IS_OPEN);
        route.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRoute() throws Exception {
        int databaseSizeBeforeCreate = routeRepository.findAll().size();

        // Create the Route

        restRouteMockMvc.perform(post("/api/routes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(route)))
                .andExpect(status().isCreated());

        // Validate the Route in the database
        List<Route> routes = routeRepository.findAll();
        assertThat(routes).hasSize(databaseSizeBeforeCreate + 1);
        Route testRoute = routes.get(routes.size() - 1);
        assertThat(testRoute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoute.getOpenDate()).isEqualTo(DEFAULT_OPEN_DATE);
        assertThat(testRoute.getIsOpen()).isEqualTo(DEFAULT_IS_OPEN);
        assertThat(testRoute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRoutes() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get all the routes
        restRouteMockMvc.perform(get("/api/routes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(route.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].openDate").value(hasItem(DEFAULT_OPEN_DATE.toString())))
                .andExpect(jsonPath("$.[*].isOpen").value(hasItem(DEFAULT_IS_OPEN.booleanValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get the route
        restRouteMockMvc.perform(get("/api/routes/{id}", route.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(route.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.openDate").value(DEFAULT_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.isOpen").value(DEFAULT_IS_OPEN.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoute() throws Exception {
        // Get the route
        restRouteMockMvc.perform(get("/api/routes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

		int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route
        route.setName(UPDATED_NAME);
        route.setOpenDate(UPDATED_OPEN_DATE);
        route.setIsOpen(UPDATED_IS_OPEN);
        route.setDescription(UPDATED_DESCRIPTION);

        restRouteMockMvc.perform(put("/api/routes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(route)))
                .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routes = routeRepository.findAll();
        assertThat(routes).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routes.get(routes.size() - 1);
        assertThat(testRoute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoute.getOpenDate()).isEqualTo(UPDATED_OPEN_DATE);
        assertThat(testRoute.getIsOpen()).isEqualTo(UPDATED_IS_OPEN);
        assertThat(testRoute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

		int databaseSizeBeforeDelete = routeRepository.findAll().size();

        // Get the route
        restRouteMockMvc.perform(delete("/api/routes/{id}", route.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Route> routes = routeRepository.findAll();
        assertThat(routes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
