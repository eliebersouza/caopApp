package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.MotivoEvasao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MotivoEvasao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotivoEvasaoRepository extends JpaRepository<MotivoEvasao, Long> {

}
