package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_ROLE") // Nome da tabela no banco de dados
public class RoleModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_ROLE") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_ROLE", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do papel

    @Column(name = "DS_DESCRIPTION", nullable = false) // Nome da coluna no banco de dados
    private String description; // Descrição do papel

}
