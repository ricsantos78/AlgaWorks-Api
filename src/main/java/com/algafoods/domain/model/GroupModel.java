package com.algafoods.domain.model;

import lombok.*;
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
@Table(name = "TB_GROUP")
public class GroupModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_GROUP")
    private UUID idGroup;

    @Column(name = "CD_GROUP", nullable = false, unique = true)
    private Long cdGroup;

    @Column(name = "NM_GROUP", nullable = false)
    private String nmGroup;

    @ManyToMany
    @JoinTable(name = "TB_GROUP_PERMISSION",
    joinColumns = @JoinColumn(name = "CD_GROUP", referencedColumnName = "CD_GROUP"),
    inverseJoinColumns = @JoinColumn(name = "CD_PERMISSION" , referencedColumnName = "CD_PERMISSION"))
    @ToString.Exclude
    private List<PermissionModel> permissions = new ArrayList<>();

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
        GroupModel that = (GroupModel) o;
        return idGroup != null && Objects.equals(idGroup, that.idGroup);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
