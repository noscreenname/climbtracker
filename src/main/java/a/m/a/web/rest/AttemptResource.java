package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.Attempt;
import a.m.a.repository.AttemptRepository;
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
 * REST controller for managing Attempt.
 */
@RestController
@RequestMapping("/api")
public class AttemptResource {

    private final Logger log = LoggerFactory.getLogger(AttemptResource.class);
        
    @Inject
    private AttemptRepository attemptRepository;
    
    /**
     * POST  /attempts -> Create a new attempt.
     */
    @RequestMapping(value = "/attempts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attempt> createAttempt(@Valid @RequestBody Attempt attempt) throws URISyntaxException {
        log.debug("REST request to save Attempt : {}", attempt);
        if (attempt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("attempt", "idexists", "A new attempt cannot already have an ID")).body(null);
        }
        Attempt result = attemptRepository.save(attempt);
        return ResponseEntity.created(new URI("/api/attempts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("attempt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attempts -> Updates an existing attempt.
     */
    @RequestMapping(value = "/attempts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attempt> updateAttempt(@Valid @RequestBody Attempt attempt) throws URISyntaxException {
        log.debug("REST request to update Attempt : {}", attempt);
        if (attempt.getId() == null) {
            return createAttempt(attempt);
        }
        Attempt result = attemptRepository.save(attempt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("attempt", attempt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attempts -> get all the attempts.
     */
    @RequestMapping(value = "/attempts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attempt> getAllAttempts() {
        log.debug("REST request to get all Attempts");
        return attemptRepository.findAll();
            }

    /**
     * GET  /attempts/:id -> get the "id" attempt.
     */
    @RequestMapping(value = "/attempts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attempt> getAttempt(@PathVariable Long id) {
        log.debug("REST request to get Attempt : {}", id);
        Attempt attempt = attemptRepository.findOne(id);
        return Optional.ofNullable(attempt)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /attempts/:id -> delete the "id" attempt.
     */
    @RequestMapping(value = "/attempts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAttempt(@PathVariable Long id) {
        log.debug("REST request to delete Attempt : {}", id);
        attemptRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attempt", id.toString())).build();
    }
}
