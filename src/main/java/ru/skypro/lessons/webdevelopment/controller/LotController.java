package ru.skypro.lessons.webdevelopment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.webdevelopment.dto.*;
import ru.skypro.lessons.webdevelopment.service.LotService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/lot")
public class LotController {

    private final LotService lotService;

    @GetMapping("/{id}/first")
    public BidDto getFirstBidder(@PathVariable Integer id) {
        return lotService.getFirstBidder(id);
    }

    @GetMapping("/{id}/frequent")
    public BidDto getMostFrequentBidder(@PathVariable Integer id) {
        return lotService.getMostFrequentBidder(id);
    }

    @GetMapping("/{id}")
    public FullLotDto getFullLot(@PathVariable Integer id) {
        return lotService.getFullLot(id);
    }

    @PostMapping("/{id}/start")
    public void startBiddingLot(@PathVariable Integer id) {
        lotService.startBiddingLot(id);
    }

    @PostMapping("/{id}/bid")
    public void createBid(@PathVariable Integer id, @RequestBody @Valid BidderDto bidderDto) {
        lotService.createBid(id, bidderDto);
    }

    @PostMapping("{id}/stop")
    public void stopBiddingLot(@PathVariable Integer id) {
        lotService.stopBiddingLot(id);
    }

    @PostMapping
    public LotDto createLot(@RequestBody @Valid CreateLotDto createLotDto) {
        return lotService.createLot(createLotDto);
    }

    @GetMapping
    public List<LotDto> findLots(@RequestParam(value = "status", required = false, defaultValue = "STARTED") Status status,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return lotService.findLots(status, page);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> getCSVFile() {
        byte[] result = lotService.getCSVFile();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(result);
    }

}
