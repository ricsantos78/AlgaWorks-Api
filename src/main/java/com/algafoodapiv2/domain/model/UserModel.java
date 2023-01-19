package com.algafoodapiv2.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_USER") // Nome da tabela no banco de dados
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_USER") // Nome da coluna no banco de dados
    private UUID id;

    @Column(name = "NM_USER", nullable = false) // Nome da coluna no banco de dados
    private String name; // Nome do usuário

    @Column(name = "DS_EMAIL", nullable = false) // Nome da coluna no banco de dados
    private String email; // Email do usuário

    @Column(name = "DS_PASSWORD", nullable = false) // Nome da coluna no banco de dados
    private String password; // Senha do usuário

    @Column(name = "DT_REGISTRATION", nullable = false) // Nome da coluna no banco de dados
    private LocalDateTime registrationDate; // Data de registro do usuário

    @ManyToMany
    @JoinTable(name = "TB_USER_ROLES",
            joinColumns = @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE"))//sera criado uma nova tabela criando um relacionamento de n/n com user e role.
    private List<RoleModel> roles;

}
