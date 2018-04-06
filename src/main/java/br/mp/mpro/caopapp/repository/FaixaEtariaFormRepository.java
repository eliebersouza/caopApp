package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.FaixaEtariaForm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FaixaEtariaForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaixaEtariaFormRepository extends JpaRepository<FaixaEtariaForm, Long> {

}
