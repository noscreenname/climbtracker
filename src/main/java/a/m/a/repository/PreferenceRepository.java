package a.m.a.repository;

import a.m.a.domain.Preference;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Preference entity.
 */
public interface PreferenceRepository extends JpaRepository<Preference,Long> {

    @Query("select distinct preference from Preference preference left join fetch preference.routes")
    List<Preference> findAllWithEagerRelationships();

    @Query("select preference from Preference preference left join fetch preference.routes where preference.id =:id")
    Preference findOneWithEagerRelationships(@Param("id") Long id);

}
