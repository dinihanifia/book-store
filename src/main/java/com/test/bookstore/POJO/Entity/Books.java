package com.test.bookstore.POJO.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
@Builder
public class Books {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(updatable = false)
    private UUID bookId;
    private String bookTitle;
    @Enumerated(EnumType.STRING)
    private ECategories categories;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id")
    private Publishers publishers;
    private String datePublished;
    private Long bookPrice;
    private Long bookStock;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "books")
    @JsonIgnore
    private Set<Transactions> transactions = new HashSet<Transactions>();
}
