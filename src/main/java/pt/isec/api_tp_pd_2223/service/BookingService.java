package pt.isec.api_tp_pd_2223.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pt.isec.api_tp_pd_2223.model.Booking;
import pt.isec.api_tp_pd_2223.repository.BookingRepository;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getPaidBookings(Integer paid){
        Specification<Booking> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pago"), paid);

        return bookingRepository.findAll(specification);
    }
}
