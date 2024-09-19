package com.tenmo.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data // replaces getter and setter annotations
@Table(name = "transfer")
@Entity
public class Transfer {

    @Column(name = "transfer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long transferId;

    @Column(name = "transfer_type_id", nullable = false)
    private Integer typeId;

    @Column(name = "transfer_status_id", nullable = false)
    private Integer statusId;

    @Column(name = "account_from", nullable = false)
    private Long accountFrom;

    @Column(name = "account_to", nullable = false)
    private Long accountTo;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transfer_message")
    private String transferMessage;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    //Needed when creating a new transfer.
    public Transfer(
            Integer typeId,
            Integer statusId,
            Long accountFrom,
            Long accountTo,
            BigDecimal amount,
            String transferMessage,
            String currency){

        this.typeId = typeId;
        this.statusId = statusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.transferMessage = transferMessage;
        this.currency = currency != null ? currency.toUpperCase() : "na";
    }

    @PrePersist //ensures createdAt and updatedAt are set during the entity's initial creation.
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate//automatically updates updatedAt whenever the entity is modified.
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}