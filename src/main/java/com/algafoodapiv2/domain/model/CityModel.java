package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_CITY") // Nome da tabela no banco de dados
public class CityModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_CITY") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_CITY", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome da cidade

    @ManyToOne
    @JoinColumn(name = "ID_STATE") // Nome da coluna no banco de dados
    private StateModel state; // Estado da cidade
}
