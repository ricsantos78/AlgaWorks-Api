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
@Table(name = "TB_KITCHEN") // Nome da tabela no banco de dados
public class KitchenModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_KITCHEN") // Nome da coluna no banco de dados
    private UUID idKitchen;

    @Column(name = "CD_KITCHEN", nullable = false, unique = true) // Nome da coluna no banco de dados
    private Long cdKitchen;


    @Column(name = "NM_KITCHEN", nullable = false, unique = true) // Nome da coluna no banco de dados
    private String nmKitchen; // Nome da cozinha

    @OneToMany(mappedBy = "kitchen")
    @ToString.Exclude
    private List<RestaurantModel> restaurants = new ArrayList<>();

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime dtRegistration;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime dtUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KitchenModel that = (KitchenModel) o;
        return idKitchen != null && Objects.equals(idKitchen, that.idKitchen);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
