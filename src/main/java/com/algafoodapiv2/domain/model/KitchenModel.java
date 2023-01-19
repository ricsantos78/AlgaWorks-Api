package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_KITCHEN") // Nome da tabela no banco de dados
public class KitchenModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_KITCHEN") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_KITCHEN", nullable = false, unique = true) // Nome da coluna no banco de dados
    private String name; // Nome da cozinha
}
