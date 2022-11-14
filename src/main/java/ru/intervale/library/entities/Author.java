package ru.intervale.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "printedProducts")
@ToString
@Builder
@Entity
@Table(name = "AUTHORS")
public class Author {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false, unique = true)
    private String lastName;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "PRODUCTS_AUTHORS", joinColumns = {@JoinColumn(name = "AUTHOR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")})
    @ToString.Exclude
    private List<PrintedProduct> printedProducts;
}
