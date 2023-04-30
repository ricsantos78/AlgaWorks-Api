package com.algafoods.domain.model;

import com.algafoods.Groups;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TB_STATE") // Nome da tabela no banco de dados
public class StateModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(groups = Groups.StateID.class)
    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_STATE") // Nome da coluna no banco de dados
    private UUID id;

    @NotBlank
    @Column(name = "NM_STATE", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do estado

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StateModel that = (StateModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
