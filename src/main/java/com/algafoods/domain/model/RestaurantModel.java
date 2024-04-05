package com.algafoods.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Table(name = "TB_RESTAURANT") // Nome da tabela no banco de dados
public class RestaurantModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_RESTAURANT") // Nome da coluna no banco de dados
    private UUID idRestaurant; // Identificador do restaurante

    @Column(name = "CD_RESTAURANT", nullable = false, unique = true) // Nome da coluna no banco de dados
    private Long cdRestaurant; // Código do restaurante

    @Column(name = "NM_RESTAURANT", nullable = false) // Nome da coluna no banco de dados
    private String nmRestaurant; // Nome do restaurante


    @Column(name = "VL_FREIGHT", nullable = false) // Nome da coluna no banco de dados
    private BigDecimal vlFreight; // Valor do frete

    @ManyToOne() // Muitos restaurantes para uma cozinha
    @JoinColumn(name = "CD_KITCHEN",referencedColumnName = "CD_KITCHEN", nullable = false)
    @ToString.Exclude // Nome da coluna no banco de dados
    private KitchenModel kitchen; // Cozinha do restaurante


    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime dtRegistration;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime dtUpdate;


    @Embedded
    private AddressModel address;

    @Column(name= "VL_STATUS")
    private Boolean vlStatus = Boolean.TRUE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_RESTAURANT_PAYMENT",
    joinColumns = @JoinColumn(name = "CD_RESTAURANT", referencedColumnName = "CD_RESTAURANT"),
    inverseJoinColumns = @JoinColumn(name = "CD_PAYMENT", referencedColumnName = "CD_PAYMENT"))
    @ToString.Exclude
    private List<PaymentModel> payments = new ArrayList<>();


    @OneToMany(mappedBy = "restaurant")
    @ToString.Exclude
    private List<ProductModel> product = new ArrayList<>();

    public void activate(){
        setVlStatus(Boolean.TRUE);
    }
    public void inactivate(){
        setVlStatus(Boolean.FALSE);
    }

    public void removePayment(PaymentModel paymentModel){
        getPayments().remove(paymentModel);
    }

    public void addPayment(PaymentModel paymentModel){
         getPayments().add(paymentModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RestaurantModel that = (RestaurantModel) o;
        return idRestaurant != null && Objects.equals(idRestaurant, that.idRestaurant);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
