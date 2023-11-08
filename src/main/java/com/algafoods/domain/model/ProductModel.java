package com.algafoods.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TB_PRODUCT") // Nome da tabela no banco de dados
public class ProductModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_PRODUCT") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_PRODUCT", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do produto

    @Column(name = "NM_DESCRIPTION", nullable = false) // Nome da coluna no banco de dados
    private String description; // Nome do produto

    @Column(name = "VL_PRICE", nullable = false) // Nome da coluna no banco de dados
    private BigDecimal price; // Valor do frete

    @Column(name = "VL_STATUS", nullable = false)
    private Boolean status = Boolean.TRUE;

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime registrationDate;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime updateDate;

    @ManyToOne // Muitos restaurantes para uma cozinha
    @JoinColumn(name = "ID_RESTAURANT", nullable = false) // Nome da coluna no banco de dados
    private RestaurantModel restaurant; // Produto do restaurante

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
