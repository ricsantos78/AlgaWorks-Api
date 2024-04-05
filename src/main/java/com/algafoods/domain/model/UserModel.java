package com.algafoods.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TB_USER") // Nome da tabela no banco de dados
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_USER") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "CD_USER", nullable = false, unique = true) // Nome da coluna no banco de dados
    private Long cdUser; // Código do usuário

    @Column(name = "NM_USER", nullable = false) // Nome da coluna no banco de dados
    private String nmUser; // Nome do usuário

    @Column(name = "DS_EMAIL", nullable = false) // Nome da coluna no banco de dados
    private String dsEmail; // Email do usuário

    @Column(name = "DS_PASSWORD", nullable = false) // Nome da coluna no banco de dados
    private String dsPassword; // Senha do usuário

    @CreationTimestamp // Sempre que o registro for criado, vai ser atribuida data atual a variavel
    @Column(name = "DT_REGISTRATION", nullable = false)
    private LocalDateTime dtRegistration;


    @UpdateTimestamp // Sempre que o registro for atualizada, vai ser atribuida data atual a variavel
    @Column(name = "DT_UPDATE", nullable = false)
    private LocalDateTime dtUpdate;

    @ManyToMany
    @JoinTable(name = "TB_USER_GROUP",
            joinColumns = @JoinColumn(name = "CD_USER", referencedColumnName = "CD_USER"),
            inverseJoinColumns = @JoinColumn(name = "CD_GROUP", referencedColumnName = "CD_GROUP"))
    @ToString.Exclude//sera criado uma nova tabela criando um relacionamento de n/n com user e role.
    private List<GroupModel> group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserModel userModel = (UserModel) o;
        return id != null && Objects.equals(id, userModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean senhaCoincideCom(String senha) {
        return getDsPassword().equals(senha);
    }

    public boolean senhaNaoCoincideCom(String senha) {
        return !senhaCoincideCom(senha);
    }

    public boolean senhaAtualCoincideComNovaSenha(String senha) {
        return getDsPassword().equals(senha);
    }

}
