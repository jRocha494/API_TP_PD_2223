package pt.isec.api_tp_pd_2223.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.isec.api_tp_pd_2223.model.Show;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
    List<Show> findAll(Specification<Show> specification);
}
