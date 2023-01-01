package pt.isec.api_tp_pd_2223.controller;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<List<Show>> getAllShows(@RequestParam(required = false) String initialDate,
                                                  @RequestParam(required = false) String finalDate){
        Specification<Show> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (initialDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("data_hora"), initialDate));
            }
            if (finalDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("data_hora"), finalDate));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return ResponseEntity.ok().body(showService.getAllShows(specification));
    }
}
