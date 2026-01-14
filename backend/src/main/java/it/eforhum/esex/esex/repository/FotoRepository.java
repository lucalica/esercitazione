package it.eforhum.esex.esex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eforhum.esex.esex.entity.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    List<Foto> findByCoppiaCoppiaId(Long coppiaId);
    List<Foto> findByCoppiaCodice(String codice);
    
}
