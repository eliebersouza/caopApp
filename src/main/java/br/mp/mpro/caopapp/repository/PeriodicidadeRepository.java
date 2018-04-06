package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.Periodicidade;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Periodicidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodicidadeRepository extends JpaRepository<Periodicidade, Long> {

}
