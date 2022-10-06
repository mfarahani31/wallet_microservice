package com.leovegas.walletmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TRANSACTIONS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Transaction extends BaseEntity implements Serializable{
    @Id
    private Long transaction_id;

    @Enumerated
    @Column(name = "type")
    private TransactionType type;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


}
