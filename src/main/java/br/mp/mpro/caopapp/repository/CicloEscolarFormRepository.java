package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.CicloEscolarForm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CicloEscolarForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CicloEscolarFormRepository extends JpaRepository<CicloEscolarForm, Long> {

}
