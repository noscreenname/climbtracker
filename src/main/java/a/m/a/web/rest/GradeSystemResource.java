package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.GradeSystem;
import a.m.a.repository.GradeSystemRepository;
import a.m.a.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GradeSystem.
 */
@RestController
@RequestMapping("/api")
public class GradeSystemResource {

    private final Logger log = LoggerFactory.getLogger(GradeSystemResource.class);
        
    @Inject
    private GradeSystemRepository gradeSystemRepository;
    
    /**
     * POST  /gradeSystems -> Create a new gradeSystem.
     */
    @RequestMapping(value = "/gradeSystems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradeSystem> createGradeSystem(@RequestBody GradeSystem gradeSystem) throws URISyntaxException {
        log.debug("REST request to save GradeSystem : {}", gradeSystem);
        if (gradeSystem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gradeSystem", "idexists", "A new gradeSystem cannot already have an ID")).body(null);
        }
        GradeSystem result = gradeSystemRepository.save(gradeSystem);
        return ResponseEntity.created(new URI("/api/gradeSystems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gradeSystem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gradeSystems -> Updates an existing gradeSystem.
     */
    @RequestMapping(value = "/gradeSystems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradeSystem> updateGradeSystem(@RequestBody GradeSystem gradeSystem) throws URISyntaxException {
        log.debug("REST request to update GradeSystem : {}", gradeSystem);
        if (gradeSystem.getId() == null) {
            return createGradeSystem(gradeSystem);
        }
        GradeSystem result = gradeSystemRepository.save(gradeSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gradeSystem", gradeSystem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gradeSystems -> get all the gradeSystems.
     */
    @RequestMapping(value = "/gradeSystems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GradeSystem> getAllGradeSystems() {
        log.debug("REST request to get all GradeSystems");
        return gradeSystemRepository.findAll();
            }

    /**
     * GET  /gradeSystems/:id -> get the "id" gradeSystem.
     */
    @RequestMapping(value = "/gradeSystems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradeSystem> getGradeSystem(@PathVariable Long id) {
        log.debug("REST request to get GradeSystem : {}", id);
        GradeSystem gradeSystem = gradeSystemRepository.findOne(id);
        return Optional.ofNullable(gradeSystem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gradeSystems/:id -> delete the "id" gradeSystem.
     */
    @RequestMapping(value = "/gradeSystems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGradeSystem(@PathVariable Long id) {
        log.debug("REST request to delete GradeSystem : {}", id);
        gradeSystemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gradeSystem", id.toString())).build();
    }
}
