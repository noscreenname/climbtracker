package a.m.a.repository;

import a.m.a.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Route entity.
 */
public interface RouteRepository extends JpaRepository<Route, Long> {

}
