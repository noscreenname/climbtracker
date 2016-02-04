package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.CragType;
import a.m.a.repository.CragTypeRepository;
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
 * REST controller for managing CragType.
 */
@RestController
@RequestMapping("/api")
public class CragTypeResource {

    private final Logger log = LoggerFactory.getLogger(CragTypeResource.class);
        
    @Inject
    private CragTypeRepository cragTypeRepository;
    
    /**
     * POST  /cragTypes -> Create a new cragType.
     */
    @RequestMapping(value = "/cragTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CragType> createCragType(@RequestBody CragType cragType) throws URISyntaxException {
        log.debug("REST request to save CragType : {}", cragType);
        if (cragType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cragType", "idexists", "A new cragType cannot already have an ID")).body(null);
        }
        CragType result = cragTypeRepository.save(cragType);
        return ResponseEntity.created(new URI("/api/cragTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cragType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cragTypes -> Updates an existing cragType.
     */
    @RequestMapping(value = "/cragTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CragType> updateCragType(@RequestBody CragType cragType) throws URISyntaxException {
        log.debug("REST request to update CragType : {}", cragType);
        if (cragType.getId() == null) {
            return createCragType(cragType);
        }
        CragType result = cragTypeRepository.save(cragType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cragType", cragType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cragTypes -> get all the cragTypes.
     */
    @RequestMapping(value = "/cragTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CragType> getAllCragTypes() {
        log.debug("REST request to get all CragTypes");
        return cragTypeRepository.findAll();
            }

    /**
     * GET  /cragTypes/:id -> get the "id" cragType.
     */
    @RequestMapping(value = "/cragTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CragType> getCragType(@PathVariable Long id) {
        log.debug("REST request to get CragType : {}", id);
        CragType cragType = cragTypeRepository.findOne(id);
        return Optional.ofNullable(cragType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cragTypes/:id -> delete the "id" cragType.
     */
    @RequestMapping(value = "/cragTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCragType(@PathVariable Long id) {
        log.debug("REST request to delete CragType : {}", id);
        cragTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cragType", id.toString())).build();
    }
}
