package pt.isec.api_tp_pd_2223.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.isec.api_tp_pd_2223.model.User;
import pt.isec.api_tp_pd_2223.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public List<User> getAllRegisteredUsers(){
        return userRepository.findByIsAuthenticated(1);
    }
    public User createUser(User user) {
        user.setId(null);
        user.setIsAuthenticated(0);
        user.setIsAdministrator(0);
        return userRepository.save(user);
    }

    public User deleteUser(Integer id){
        User curContact = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return curContact;
    }

    public boolean loginUser(String username, String password){
        var user = userRepository.findByUsernameAndPassword(username, password);
        if(user == null)
            return false;

        if(user.getIsAuthenticated() != 0)
            return false;

        var curUser = userRepository.findById(user.getId()).get();
        curUser.setIsAuthenticated(1);
        userRepository.save(curUser);

        return true;
    }
}
