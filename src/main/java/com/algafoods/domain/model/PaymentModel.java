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
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TB_PAYMENT") // Nome da tabela no banco de dados
public class PaymentModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerador de chave primária
    @Column(name = "ID_PAYMENT") // Nome da coluna no banco de dados
    private UUID idPayment;

    @Column(name = "CD_PAYMENT", nullable = false, unique = true) // Nome da coluna no banco de dados
    private Long cdPayment; // Código do pagamento

    @Column(name = "NM_PAYMENT", nullable = false) // Nome da coluna no banco de dados
    private String nmPayment; // Nome do pagamento

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
        PaymentModel that = (PaymentModel) o;
        return idPayment != null && Objects.equals(idPayment, that.idPayment);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
