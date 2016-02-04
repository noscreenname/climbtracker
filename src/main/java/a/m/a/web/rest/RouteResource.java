package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.Route;
import a.m.a.repository.RouteRepository;
import a.m.a.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Route.
 */
@RestController
@RequestMapping("/api")
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);
        
    @Inject
    private RouteRepository routeRepository;
    
    /**
     * POST  /routes -> Create a new route.
     */
    @RequestMapping(value = "/routes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Route> createRoute(@Valid @RequestBody Route route) throws URISyntaxException {
        log.debug("REST request to save Route : {}", route);
        if (route.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("route", "idexists", "A new route cannot already have an ID")).body(null);
        }
        Route result = routeRepository.save(route);
        return ResponseEntity.created(new URI("/api/routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("route", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /routes -> Updates an existing route.
     */
    @RequestMapping(value = "/routes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody Route route) throws URISyntaxException {
        log.debug("REST request to update Route : {}", route);
        if (route.getId() == null) {
            return createRoute(route);
        }
        Route result = routeRepository.save(route);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("route", route.getId().toString()))
            .body(result);
    }

    /**
     * GET  /routes -> get all the routes.
     */
    @RequestMapping(value = "/routes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Route> getAllRoutes() {
        log.debug("REST request to get all Routes");
        return routeRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /routes/:id -> get the "id" route.
     */
    @RequestMapping(value = "/routes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        log.debug("REST request to get Route : {}", id);
        Route route = routeRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(route)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /routes/:id -> delete the "id" route.
     */
    @RequestMapping(value = "/routes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        log.debug("REST request to delete Route : {}", id);
        routeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("route", id.toString())).build();
    }
}
