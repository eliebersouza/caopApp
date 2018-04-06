package br.mp.mpro.caopapp.repository;

import br.mp.mpro.caopapp.domain.EspacoLazer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EspacoLazer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspacoLazerRepository extends JpaRepository<EspacoLazer, Long> {

}
