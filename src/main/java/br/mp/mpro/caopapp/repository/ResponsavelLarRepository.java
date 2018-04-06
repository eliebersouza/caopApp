package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.ResponsavelLar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ResponsavelLar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsavelLarRepository extends JpaRepository<ResponsavelLar, Long> {

}
