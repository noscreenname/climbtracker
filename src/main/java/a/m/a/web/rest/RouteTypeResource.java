package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.RouteType;
import a.m.a.repository.RouteTypeRepository;
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
 * REST controller for managing RouteType.
 */
@RestController
@RequestMapping("/api")
public class RouteTypeResource {

    private final Logger log = LoggerFactory.getLogger(RouteTypeResource.class);
        
    @Inject
    private RouteTypeRepository routeTypeRepository;
    
    /**
     * POST  /routeTypes -> Create a new routeType.
     */
    @RequestMapping(value = "/routeTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RouteType> createRouteType(@Valid @RequestBody RouteType routeType) throws URISyntaxException {
        log.debug("REST request to save RouteType : {}", routeType);
        if (routeType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("routeType", "idexists", "A new routeType cannot already have an ID")).body(null);
        }
        RouteType result = routeTypeRepository.save(routeType);
        return ResponseEntity.created(new URI("/api/routeTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("routeType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /routeTypes -> Updates an existing routeType.
     */
    @RequestMapping(value = "/routeTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RouteType> updateRouteType(@Valid @RequestBody RouteType routeType) throws URISyntaxException {
        log.debug("REST request to update RouteType : {}", routeType);
        if (routeType.getId() == null) {
            return createRouteType(routeType);
        }
        RouteType result = routeTypeRepository.save(routeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("routeType", routeType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /routeTypes -> get all the routeTypes.
     */
    @RequestMapping(value = "/routeTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RouteType> getAllRouteTypes() {
        log.debug("REST request to get all RouteTypes");
        return routeTypeRepository.findAll();
            }

    /**
     * GET  /routeTypes/:id -> get the "id" routeType.
     */
    @RequestMapping(value = "/routeTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RouteType> getRouteType(@PathVariable Long id) {
        log.debug("REST request to get RouteType : {}", id);
        RouteType routeType = routeTypeRepository.findOne(id);
        return Optional.ofNullable(routeType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /routeTypes/:id -> delete the "id" routeType.
     */
    @RequestMapping(value = "/routeTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRouteType(@PathVariable Long id) {
        log.debug("REST request to delete RouteType : {}", id);
        routeTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("routeType", id.toString())).build();
    }
}
