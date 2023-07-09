package ru.skypro.lessons.webdevelopment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.webdevelopment.dto.BidDto;
import ru.skypro.lessons.webdevelopment.entity.Bid;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Integer> {
    Optional<Bid> findFirstByLot_IdOrderByDateTime(int id);

    @Query("""
            SELECT new ru.skypro.lessons.webdevelopment.dto.BidDto(b.name, b.dateTime) FROM Bid b WHERE b.name = (
            SELECT b.name FROM Bid b GROUP BY b.name ORDER BY count(b.name) DESC LIMIT 1
            ) ORDER BY b.dateTime DESC LIMIT 1
            """)
    Optional<BidDto> findMostFrequentBidder(@Param("lotId") int id);
}
