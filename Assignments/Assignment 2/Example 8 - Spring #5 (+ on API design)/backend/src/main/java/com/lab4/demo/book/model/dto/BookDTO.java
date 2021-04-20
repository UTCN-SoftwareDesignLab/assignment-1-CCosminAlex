package com.lab4.demo.book.model.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private int quantity;
    private double price;
}
