package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.Form;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Form entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    @Query("select distinct form from Form form left join fetch form.servicoPublicos left join fetch form.espacoLazers")
    List<Form> findAllWithEagerRelationships();

    @Query("select form from Form form left join fetch form.servicoPublicos left join fetch form.espacoLazers where form.id =:id")
    Form findOneWithEagerRelationships(@Param("id") Long id);

}
