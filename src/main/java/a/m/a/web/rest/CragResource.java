package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.Crag;
import a.m.a.repository.CragRepository;
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
 * REST controller for managing Crag.
 */
@RestController
@RequestMapping("/api")
public class CragResource {

    private final Logger log = LoggerFactory.getLogger(CragResource.class);
        
    @Inject
    private CragRepository cragRepository;
    
    /**
     * POST  /crags -> Create a new crag.
     */
    @RequestMapping(value = "/crags",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Crag> createCrag(@Valid @RequestBody Crag crag) throws URISyntaxException {
        log.debug("REST request to save Crag : {}", crag);
        if (crag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("crag", "idexists", "A new crag cannot already have an ID")).body(null);
        }
        Crag result = cragRepository.save(crag);
        return ResponseEntity.created(new URI("/api/crags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("crag", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crags -> Updates an existing crag.
     */
    @RequestMapping(value = "/crags",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Crag> updateCrag(@Valid @RequestBody Crag crag) throws URISyntaxException {
        log.debug("REST request to update Crag : {}", crag);
        if (crag.getId() == null) {
            return createCrag(crag);
        }
        Crag result = cragRepository.save(crag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("crag", crag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crags -> get all the crags.
     */
    @RequestMapping(value = "/crags",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Crag> getAllCrags() {
        log.debug("REST request to get all Crags");
        return cragRepository.findAll();
            }

    /**
     * GET  /crags/:id -> get the "id" crag.
     */
    @RequestMapping(value = "/crags/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Crag> getCrag(@PathVariable Long id) {
        log.debug("REST request to get Crag : {}", id);
        Crag crag = cragRepository.findOne(id);
        return Optional.ofNullable(crag)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /crags/:id -> delete the "id" crag.
     */
    @RequestMapping(value = "/crags/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCrag(@PathVariable Long id) {
        log.debug("REST request to delete Crag : {}", id);
        cragRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("crag", id.toString())).build();
    }
}
