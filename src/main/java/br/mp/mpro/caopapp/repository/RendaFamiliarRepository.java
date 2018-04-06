package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.RendaFamiliar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RendaFamiliar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RendaFamiliarRepository extends JpaRepository<RendaFamiliar, Long> {

}
