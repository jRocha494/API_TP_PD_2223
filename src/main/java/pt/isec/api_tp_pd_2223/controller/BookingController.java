package pt.isec.api_tp_pd_2223.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.isec.api_tp_pd_2223.model.Booking;
import pt.isec.api_tp_pd_2223.service.BookingService;
import java.util.List;

@RestController
@RequestMapping("booking")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Booking>> getAllBooking(@RequestParam(required = false) Integer paid){
        if(paid == 1 || paid == 0){
            return ResponseEntity.ok().body(bookingService.getPaidBookings(paid));
        }

        return ResponseEntity.ok().body(bookingService.getAllBookings());
    }
}
