package a.m.a.repository;

import a.m.a.domain.Route;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Route entity.
 */
public interface RouteRepository extends JpaRepository<Route,Long> {

    @Query("select distinct route from Route route left join fetch route.routeTypess")
    List<Route> findAllWithEagerRelationships();

    @Query("select route from Route route left join fetch route.routeTypess where route.id =:id")
    Route findOneWithEagerRelationships(@Param("id") Long id);

}
