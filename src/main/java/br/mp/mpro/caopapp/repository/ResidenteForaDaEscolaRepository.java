package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.ResidenteForaDaEscola;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ResidenteForaDaEscola entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResidenteForaDaEscolaRepository extends JpaRepository<ResidenteForaDaEscola, Long> {

}
