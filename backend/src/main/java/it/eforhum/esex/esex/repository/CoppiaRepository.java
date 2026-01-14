package it.eforhum.esex.esex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eforhum.esex.esex.entity.Coppia;

@Repository
public interface CoppiaRepository extends JpaRepository<Coppia, Long> {
    
    Optional<Coppia> findByCodice(String codice);

    boolean existsByCodice(String codice);
}
