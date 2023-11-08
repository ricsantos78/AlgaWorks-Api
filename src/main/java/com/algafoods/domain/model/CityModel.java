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
import java.time.OffsetDateTime;
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


    @Column(name = "NM_CITY", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome da cidade


    @ManyToOne
    @JoinColumn(name = "ID_STATE") // Nome da coluna no banco de dados
    private StateModel state; // Estado da cidade

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private OffsetDateTime registrationDate;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private OffsetDateTime updateDate;

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
