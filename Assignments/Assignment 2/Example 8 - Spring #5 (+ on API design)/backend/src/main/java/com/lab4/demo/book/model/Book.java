package com.lab4.demo.book.model;

import com.lab4.demo.user.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String genre;

    @Column
    private int quantity;

    @Column
    private double price;

}
