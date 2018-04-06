package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.Religiao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Religiao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReligiaoRepository extends JpaRepository<Religiao, Long> {

}
