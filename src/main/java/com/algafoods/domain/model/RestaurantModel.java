package com.algafoods.domain.model;

import com.algafoods.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
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
    private UUID id;

    @NotBlank
    @Column(name = "NM_RESTAURANT", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do restaurante

    @PositiveOrZero
    @Column(name = "VL_FREIGHT", nullable = false) // Nome da coluna no banco de dados
    private BigDecimal freight; // Valor do frete

    @Valid
    @ConvertGroup(to = Groups.KitchenID.class)
    @NotNull
    @ManyToOne() // Muitos restaurantes para uma cozinha
    @JoinColumn(name = "ID_KITCHEN", nullable = false)
    @ToString.Exclude // Nome da coluna no banco de dados
    private KitchenModel kitchen; // Cozinha do restaurante

    @JsonIgnore
    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime registrationDate;

    @JsonIgnore
    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime updateDate;

    @JsonIgnore
    @Embedded
    private AddressModel address;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_RESTAURANT_PAYMENT",
    joinColumns = @JoinColumn(name = "ID_RESTAURANT"),
    inverseJoinColumns = @JoinColumn(name = "ID_PAYMENT"))
    @ToString.Exclude
    private List<PaymentModel> payments = new ArrayList<>();

    @JsonIgnore // não vai sereliarizar quando quando ssubir a aplicacao
    @OneToMany(mappedBy = "restaurant")
    @ToString.Exclude
    private List<ProductModel> product = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RestaurantModel that = (RestaurantModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
