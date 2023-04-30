package com.algafoods.domain.model;

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
@Table(name = "TB_GROUP")
public class GroupModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_GROUP")
    private UUID id;

    @Column(name = "NM_GROUP", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "TB_GROUP_PERMISSION",
    joinColumns = @JoinColumn(name = "ID_GROUP"),
    inverseJoinColumns = @JoinColumn(name = "ID_PERMISSION"))
    @ToString.Exclude
    private List<PermissionModel> permissions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupModel that = (GroupModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
