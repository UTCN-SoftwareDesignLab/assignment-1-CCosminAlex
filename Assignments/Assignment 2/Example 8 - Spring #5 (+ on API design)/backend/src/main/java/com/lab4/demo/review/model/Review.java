package com.lab4.demo.review.model;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.user.model.Users;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
