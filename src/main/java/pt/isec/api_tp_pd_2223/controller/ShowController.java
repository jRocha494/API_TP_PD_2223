package pt.isec.api_tp_pd_2223.controller;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isec.api_tp_pd_2223.model.Show;
import pt.isec.api_tp_pd_2223.service.ShowService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("show")
public class ShowController {
    private ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok().body(showService.getAllShows());
    }

    @GetMapping("filter")
    public ResponseEntity<List<Show>> getFilteredShows(@RequestParam(required = false) String initialDate,
                                                       @RequestParam(required = false) String duration){
        if(initialDate == null && duration == null)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Specification<Show> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (initialDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("dateTime"), initialDate));
            }
            if (duration != null) {
                predicates.add(criteriaBuilder.equal(root.get("duration"), duration));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return ResponseEntity.ok().body(showService.getAllShows(specification));
    }
}
