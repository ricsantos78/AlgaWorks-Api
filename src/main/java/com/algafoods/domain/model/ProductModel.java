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
    private UUID idProduct;

    @Column(name = "CD_PRODUCT", nullable = false, unique = true) // Nome da coluna no banco de dados
    private Long cdProduct;

    @Column(name = "NM_PRODUCT", nullable = false) // Nome da coluna no banco de dados
    private String nmProduct; // Nome do produto

    @Column(name = "NM_DESCRIPTION", nullable = false) // Nome da coluna no banco de dados
    private String nmDescription; // Nome do produto

    @Column(name = "VL_PRICE", nullable = false) // Nome da coluna no banco de dados
    private BigDecimal vlPrice; // Valor do produto

    @Column(name = "VL_STATUS", nullable = false)
    private Boolean vlStatus = Boolean.FALSE;

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime dtRegistration;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime dtUpdate;

    @ManyToOne
    @JoinColumn(name = "CD_RESTAURANT",referencedColumnName = "CD_RESTAURANT", nullable = false) // Nome da coluna no banco de dados
    private RestaurantModel restaurant; // Produto do restaurante

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return idProduct != null && Objects.equals(idProduct, that.idProduct);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void activate(){
        setVlStatus(Boolean.TRUE);
    }

    public void inactivate(){
        setVlStatus(Boolean.FALSE);
    }
}
