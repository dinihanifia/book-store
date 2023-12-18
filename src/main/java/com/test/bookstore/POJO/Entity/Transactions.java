package com.test.bookstore.POJO.Entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
@Builder
public class Transactions {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(updatable = false)
    private UUID transactionId;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "book_id")
    private Books books;
    private Long quantity;
    private Long totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;

    //localdatetime
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
