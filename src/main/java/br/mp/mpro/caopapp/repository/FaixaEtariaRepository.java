package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.FaixaEtaria;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FaixaEtaria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaixaEtariaRepository extends JpaRepository<FaixaEtaria, Long> {

}
