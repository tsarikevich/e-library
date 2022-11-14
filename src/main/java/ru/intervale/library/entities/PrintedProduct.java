package ru.intervale.library.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"authors", "publisher"})
@SuperBuilder
@Entity(name = "PRINTED_PRODUCTS")
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PRODUCT_TYPE",
        discriminatorType = DiscriminatorType.STRING)
public abstract class PrintedProduct {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;
    @Column(name = "DATE_PUBLICATION")
    private LocalDate date;
    @ManyToMany
    @JoinTable(name = "PRODUCTS_AUTHORS", joinColumns = {@JoinColumn(name = "PRODUCT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID")})
    @ToString.Exclude
    private Set<Author> authors;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUBLISHER_ID", referencedColumnName = "ID")
    @ToString.Exclude
    private Publisher publisher;
}
