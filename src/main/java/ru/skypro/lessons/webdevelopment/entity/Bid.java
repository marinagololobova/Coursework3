package ru.skypro.lessons.webdevelopment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20, nullable = false)
    private String name;
    @CreationTimestamp
    @Column(name = "date_time", nullable = false, updatable = false)
    private OffsetDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    public Bid(String name, Lot lot) {
        this.name = name;
        this.lot = lot;
    }

}
