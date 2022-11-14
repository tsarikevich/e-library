package ru.intervale.library.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@DiscriminatorValue("BOOK")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends PrintedProduct {
    @Column(name = "EDITION")
    private int edition;
}
