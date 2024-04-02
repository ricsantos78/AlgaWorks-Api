package com.algafoods.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
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
@Table(name = "TB_ORDER_ITEM")
public class OrderItemsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ORDER_ITEM")
    private UUID idOrderItem;

    @Column(name = "CD_ORDER_ITEM", nullable = false, unique = true)
    private Long cdOrderItem;

    @Column(name = "VL_QUANTITY", nullable = false)
    private Integer vlQuantity;

    @Column(name = "VL_UNITARY", nullable = false)
    private BigDecimal vlUnitary;

    @Column(name = "VL_TOTAL", nullable = false)
    private BigDecimal vlTotal;

    @Column(name = "VL_OBSERVATION")
    private String tsObservation;

    @ManyToOne
    @JoinColumn(name = "CD_ORDER", referencedColumnName = "CD_ORDER",nullable = false)
    private OrderModel order;

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime registrationDate;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime updateDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderItemsModel that = (OrderItemsModel) o;
        return idOrderItem != null && Objects.equals(idOrderItem, that.idOrderItem);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
