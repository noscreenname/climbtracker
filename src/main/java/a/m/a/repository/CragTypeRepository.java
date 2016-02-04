package a.m.a.repository;

import a.m.a.domain.CragType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CragType entity.
 */
public interface CragTypeRepository extends JpaRepository<CragType,Long> {

}
