package com.test.bookstore.POJO.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "publisher")
public class Publishers {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(updatable = false)
    private UUID publisherId;
    private String publisherName;
    private String publisherAddress;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "publishers")
    @JsonIgnore
    private Set<Books> books = new HashSet<Books>();
}
