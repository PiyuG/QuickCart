package com.quickcart.paymentservice.entity;

import com.quickcart.paymentservice.enums.PaymentMethod;
import com.quickcart.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment", indexes = {
          @Index(columnList = "orderId"),
          @Index(columnList = "transactionId")
        })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false, precision = 12,scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false,unique = true)
    private String transactionId;
    private String gatewayPaymentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void createAt(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt(){
        this.updatedAt=LocalDateTime.now();
    }
}
