package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_RESTAURANT") // Nome da tabela no banco de dados
public class RestaurantModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_RESTAURANT") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_RESTAURANT", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do restaurante

    @Column(name = "VL_FREIGHT", nullable = false) // Nome da coluna no banco de dados
    private BigDecimal freight; // Valor do frete

    @ManyToOne // Muitos restaurantes para uma cozinha
    @JoinColumn(name = "ID_KITCHEN", nullable = false) // Nome da coluna no banco de dados
    private KitchenModel kitchen; // Cozinha do restaurante
}
