package a.m.a.repository;

import a.m.a.domain.Attempt;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Attempt entity.
 */
public interface AttemptRepository extends JpaRepository<Attempt,Long> {

}
