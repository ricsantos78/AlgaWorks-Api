package com.algafoods.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private UUID id;

    @Column(name = "VL_QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "VL_UNITARY", nullable = false)
    private BigDecimal unitaryValue;

    @Column(name = "VL_TOTAL", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "VL_OBSERVATION")
    private String observation;

    @ManyToOne
    @JoinColumn(name = "ID_ORDER", nullable = false)
    private OrderModel order;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderItemsModel that = (OrderItemsModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
