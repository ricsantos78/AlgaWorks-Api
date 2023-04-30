package com.algafoods.domain.model;

import com.algafoods.Groups;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TB_CITY") // Nome da tabela no banco de dados
public class CityModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_CITY") // Nome da coluna no banco de dados
    private UUID id;

    @NotBlank
    @Column(name = "NM_CITY", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome da cidade

    @NotNull
    @Valid
    @ConvertGroup(to = Groups.StateID.class)
    @ManyToOne
    @JoinColumn(name = "ID_STATE") // Nome da coluna no banco de dados
    private StateModel state; // Estado da cidade

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CityModel cityModel = (CityModel) o;
        return id != null && Objects.equals(id, cityModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
