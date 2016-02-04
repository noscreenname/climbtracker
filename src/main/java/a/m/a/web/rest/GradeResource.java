package a.m.a.web.rest;

import com.codahale.metrics.annotation.Timed;
import a.m.a.domain.Grade;
import a.m.a.repository.GradeRepository;
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
 * REST controller for managing Grade.
 */
@RestController
@RequestMapping("/api")
public class GradeResource {

    private final Logger log = LoggerFactory.getLogger(GradeResource.class);
        
    @Inject
    private GradeRepository gradeRepository;
    
    /**
     * POST  /grades -> Create a new grade.
     */
    @RequestMapping(value = "/grades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) throws URISyntaxException {
        log.debug("REST request to save Grade : {}", grade);
        if (grade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("grade", "idexists", "A new grade cannot already have an ID")).body(null);
        }
        Grade result = gradeRepository.save(grade);
        return ResponseEntity.created(new URI("/api/grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("grade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grades -> Updates an existing grade.
     */
    @RequestMapping(value = "/grades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grade> updateGrade(@RequestBody Grade grade) throws URISyntaxException {
        log.debug("REST request to update Grade : {}", grade);
        if (grade.getId() == null) {
            return createGrade(grade);
        }
        Grade result = gradeRepository.save(grade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("grade", grade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grades -> get all the grades.
     */
    @RequestMapping(value = "/grades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Grade> getAllGrades() {
        log.debug("REST request to get all Grades");
        return gradeRepository.findAll();
            }

    /**
     * GET  /grades/:id -> get the "id" grade.
     */
    @RequestMapping(value = "/grades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grade> getGrade(@PathVariable Long id) {
        log.debug("REST request to get Grade : {}", id);
        Grade grade = gradeRepository.findOne(id);
        return Optional.ofNullable(grade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /grades/:id -> delete the "id" grade.
     */
    @RequestMapping(value = "/grades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        log.debug("REST request to delete Grade : {}", id);
        gradeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grade", id.toString())).build();
    }
}
