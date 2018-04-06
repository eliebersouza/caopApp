package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.ServicoPublico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ServicoPublico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicoPublicoRepository extends JpaRepository<ServicoPublico, Long> {

}
