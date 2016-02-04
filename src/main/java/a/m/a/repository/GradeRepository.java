package a.m.a.repository;

import a.m.a.domain.Grade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Grade entity.
 */
public interface GradeRepository extends JpaRepository<Grade,Long> {

}
