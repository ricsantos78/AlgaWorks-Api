package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_PAYMENT") // Nome da tabela no banco de dados
public class PaymentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_PAYMENT") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_PAYMENT", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do pagamento
}
