package a.m.a.repository;

import a.m.a.domain.Crag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Crag entity.
 */
public interface CragRepository extends JpaRepository<Crag,Long> {

}
