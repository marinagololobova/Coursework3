package ru.skypro.lessons.webdevelopment.service;

import jakarta.annotation.Nullable;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.webdevelopment.dto.*;
import ru.skypro.lessons.webdevelopment.entity.Bid;
import ru.skypro.lessons.webdevelopment.entity.Lot;
import ru.skypro.lessons.webdevelopment.exceptions.LotNotFoundException;
import ru.skypro.lessons.webdevelopment.exceptions.LotNotStartedException;
import ru.skypro.lessons.webdevelopment.mapper.LotMapper;
import ru.skypro.lessons.webdevelopment.repository.BidRepository;
import ru.skypro.lessons.webdevelopment.repository.LotRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LotService {

    private static final Logger LOG = LoggerFactory.getLogger(LotService.class);
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final LotMapper lotMapper;
    private final JsonUtil jsonUtil;

    public BidDto getFirstBidder(int id) {
        LOG.info("Was invoked method for getFirstBidder with parameter: {}", jsonUtil.toJson(id));
        return bidRepository.findFirstByLot_IdOrderByDateTime(id)
                .map(lotMapper::toBidDto)
                .orElseThrow(LotNotFoundException::new);
    }

    public BidDto getMostFrequentBidder(int id) {
        LOG.info("Was invoked method for getMostFrequentBidder with parameter: {}", jsonUtil.toJson(id));
        return bidRepository.findMostFrequentBidder(id)
                .orElseThrow(LotNotFoundException::new);
    }

    public FullLotDto getFullLot(int id) {
        LOG.info("Was invoked method for getFullLot with parameter: {}", jsonUtil.toJson(id));
        Tuple tuple = lotRepository.getFullLot(id)
                .orElseThrow(LotNotFoundException::new);
        BidDto lastBid = new BidDto(
                tuple.get("bidderName", String.class),
                tuple.get("bidDate", Instant.class).atOffset(ZonedDateTime.now().getOffset())
        );
        if (lastBid.getBidDate() == null && lastBid.getBidderName() == null) {
            lastBid = null;
        }
        return new FullLotDto(
                tuple.get("id", Integer.class),
                Status.valueOf(tuple.get("status", String.class)),
                tuple.get("title", String.class),
                tuple.get("description", String.class),
                tuple.get("startPrice", Integer.class),
                tuple.get("bidPrice", Integer.class),
                tuple.get("currentPrice", Long.class).intValue(),
                lastBid
        );
    }

    public void startBiddingLot(int id) {
        LOG.info("Was invoked method for startBiddingLot with parameter: {}", jsonUtil.toJson(id));
        Lot lot = lotRepository.findById(id)
                .orElseThrow(LotNotFoundException::new);
        lot.setStatus(Status.STARTED);
        lotRepository.save(lot);
    }

    public void createBid(int id, BidderDto bidderDto) {
        LOG.info("Was invoked method for createBid with parameters: {}, {}", jsonUtil.toJson(id), jsonUtil.toJson(bidderDto));
        Lot lot = lotRepository.findById(id)
                .orElseThrow(LotNotFoundException::new);
        if (lot.getStatus() == Status.CREATED || lot.getStatus() == Status.STOPPED) {
            throw new LotNotStartedException();
        }
        bidRepository.save(new Bid(bidderDto.getBidderName(), lot));
    }

    public void stopBiddingLot(int id) {
        LOG.info("Was invoked method for stopBiddingLot with parameter: {}", jsonUtil.toJson(id));
        Lot lot = lotRepository.findById(id)
                .orElseThrow(LotNotFoundException::new);
        lot.setStatus(Status.STOPPED);
        lotRepository.save(lot);
    }

    public LotDto createLot(CreateLotDto createLotDto) {
        LOG.info("Was invoked method for createLot with parameter: {}", jsonUtil.toJson(createLotDto));
        return lotMapper.toLotDto(lotRepository.save(lotMapper.toEntity(createLotDto)));
    }

    public List<LotDto> findLots(@Nullable Status status, int page) {
        LOG.info("Was invoked method for findLots with parameters: {}, {}", jsonUtil.toJson(status), jsonUtil.toJson(page));
        Pageable pageable = PageRequest.of(page, 10);
        return Optional.ofNullable(status)
                .map(st -> lotRepository.findAllByStatus(st, pageable))
                .orElseGet(() -> lotRepository.findAll(pageable)).stream()
                .map(lotMapper::toLotDto)
                .collect(Collectors.toList());
    }

    @Nullable
    public byte[] getCSVFile() {
        LOG.info("Was invoked method for getCSVFile");
        StringWriter stringWriter = new StringWriter();
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("id", "title", "status", "lastBidder", "currentPrice")
                .build();
        try (CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat)) {
            lotRepository.getLotsForExport().forEach(tuple -> {
                try {
                    csvPrinter.printRecord(
                            tuple.get("id", Integer.class),
                            tuple.get("title", String.class),
                            Status.valueOf(tuple.get("status", String.class)),
                            tuple.get("lastBidder", String.class),
                            tuple.get("currentPrice", Long.class)
                    );
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            return stringWriter.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
