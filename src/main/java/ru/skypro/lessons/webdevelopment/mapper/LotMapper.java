package ru.skypro.lessons.webdevelopment.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.webdevelopment.dto.BidDto;
import ru.skypro.lessons.webdevelopment.dto.CreateLotDto;
import ru.skypro.lessons.webdevelopment.dto.LotDto;
import ru.skypro.lessons.webdevelopment.dto.Status;
import ru.skypro.lessons.webdevelopment.entity.Bid;
import ru.skypro.lessons.webdevelopment.entity.Lot;

@Component
public class LotMapper {

    public BidDto toBidDto(Bid bid) {
        BidDto bidDto = new BidDto();
        bidDto.setBidDate(bid.getDateTime());
        bidDto.setBidderName(bid.getName());
        return bidDto;
    }

    public Lot toEntity(CreateLotDto createLotDto) {
        Lot lot = new Lot();
        lot.setStatus(Status.CREATED);
        lot.setTitle(createLotDto.getTitle());
        lot.setDescription(createLotDto.getDescription());
        lot.setStartPrice(createLotDto.getStartPrice());
        lot.setBidPrice(createLotDto.getBidPrice());
        return lot;
    }

    public LotDto toLotDto(Lot lot) {
        LotDto lotDto = new LotDto();
        lotDto.setId(lot.getId());
        lotDto.setStatus(lot.getStatus());
        lotDto.setTitle(lot.getTitle());
        lotDto.setDescription(lot.getDescription());
        lotDto.setStartPrice(lot.getStartPrice());
        lotDto.setBidPrice(lot.getBidPrice());
        return lotDto;
    }
}
