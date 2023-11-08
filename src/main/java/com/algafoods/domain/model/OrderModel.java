package com.algafoods.domain.model;

import com.algafoods.domain.model.enums.StatusOrder;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TB_ORDER")
public class OrderModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ORDER")
    private UUID id;

    @Column(name = "VL_SUBTOTAL", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "VL_FREIGHTRATE", nullable = false)
    private BigDecimal freightRate;

    @Column(name = "VL_AMOUNT", nullable = false)
    private BigDecimal amount;

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime registrationDate;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "DT_CONFIRMATION")
    private LocalDateTime confirmationDate;

    @Column(name = "DT_CANCEL")
    private LocalDateTime cancelDate;

    @Column(name = "DT_DELIVERY")
    private LocalDateTime deliveryDate;

    @Column(name = "NM_STATUS")
    private StatusOrder status;

    @Embedded
    private AddressModel address;

    @ManyToOne
    @JoinColumn(name = "ID_USER", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "ID_RESTAURANT", nullable = false)
    private RestaurantModel restaurant;

    @ManyToOne
    @JoinColumn(name = "ID_PAYMENT", nullable = false)
    private PaymentModel payment;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    private List<OrderItemsModel> orderItemsModelList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderModel that = (OrderModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
