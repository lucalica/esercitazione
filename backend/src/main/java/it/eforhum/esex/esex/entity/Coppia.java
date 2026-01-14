package it.eforhum.esex.esex.entity;
 

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Coppia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coppiaId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private long codice;

    @OneToMany(mappedBy = "coppia", cascade=CascadeType.ALL)
    private List<Foto> fotoList;

}
