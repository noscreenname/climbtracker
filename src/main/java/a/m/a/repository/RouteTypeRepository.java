package a.m.a.repository;

import a.m.a.domain.RouteType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RouteType entity.
 */
public interface RouteTypeRepository extends JpaRepository<RouteType,Long> {

}
