package com.algafoods.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
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
    private UUID id;

    @Column(name = "NM_KITCHEN", nullable = false, unique = true) // Nome da coluna no banco de dados
    private String name; // Nome da cozinha

    @JsonIgnore // não vai sereliarizar quando quando ssubir a aplicacao
    @OneToMany(mappedBy = "kitchen")
    @ToString.Exclude
    private List<RestaurantModel> restaurants = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KitchenModel that = (KitchenModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
