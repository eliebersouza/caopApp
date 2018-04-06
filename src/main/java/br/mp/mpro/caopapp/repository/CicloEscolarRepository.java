package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.CicloEscolar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CicloEscolar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CicloEscolarRepository extends JpaRepository<CicloEscolar, Long> {

}
