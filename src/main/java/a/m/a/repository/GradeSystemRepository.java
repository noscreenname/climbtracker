package a.m.a.repository;

import a.m.a.domain.GradeSystem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GradeSystem entity.
 */
public interface GradeSystemRepository extends JpaRepository<GradeSystem,Long> {

}
