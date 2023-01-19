package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_STATE") // Nome da tabela no banco de dados
public class StateModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_STATE") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_STATE", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do estado
}
