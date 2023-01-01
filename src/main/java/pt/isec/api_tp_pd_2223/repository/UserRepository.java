package pt.isec.api_tp_pd_2223.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.isec.api_tp_pd_2223.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    User findByUsernameAndPassword(String Username, String Password);
    List<User> findByIsAuthenticated(int isAuthenticated);
}
