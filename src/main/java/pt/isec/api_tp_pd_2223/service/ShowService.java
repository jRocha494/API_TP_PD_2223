package pt.isec.api_tp_pd_2223.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pt.isec.api_tp_pd_2223.model.Show;
import pt.isec.api_tp_pd_2223.model.User;
import pt.isec.api_tp_pd_2223.repository.ShowRepository;
import pt.isec.api_tp_pd_2223.repository.UserRepository;

import java.util.List;

@Service
public class ShowService {
    private ShowRepository showRepository;

    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> getAllShows(Specification<Show> specification){
        return showRepository.findAll(specification);
    }
}
