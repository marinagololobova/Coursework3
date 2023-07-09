package ru.skypro.lessons.webdevelopment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.lessons.webdevelopment.dto.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "lots")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status;
    @Column(length = 64, nullable = false)
    private String title;
    @Column(length = 4096, nullable = false)
    private String description;
    @Column(name = "start_price")
    private int startPrice;
    @Column(name = "bid_price")
    private int bidPrice;
}
