package it.eforhum.esex.esex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fotoId;

    private String path;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="coppia_id")
    private Coppia coppia;

}
