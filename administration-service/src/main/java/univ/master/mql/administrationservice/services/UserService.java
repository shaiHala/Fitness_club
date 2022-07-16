package univ.master.mql.administrationservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.administrationservice.DTO.UserDTO;
import univ.master.mql.administrationservice.entity.User;
import univ.master.mql.administrationservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;



    public User addUser(User user) {
        return   repo.save(user);
    }

    public List<User> getUsers(){
        List<User> users= repo.findAll();
        return users;
    }

    public Optional<User> getUser(String id){
        return repo.findUserById(id);
    }

    public  Optional<User> getUserByUsername(String username){
        return repo.findUserByUsername(username);
    }

    public User editUser (User user){
        return repo.findUserById(user.getId())
                .map(tr -> {
                    if(user.getLastname()!="") tr.setLastname(user.getLastname());
                    if(user.getFirstname()!="")tr.setFirstname(user.getFirstname());
                    if(user.getBirthdate()!=null)tr.setBirthdate(user.getBirthdate());
                    if(user.getCin()!="")tr.setCin(user.getCin());
                    if(user.getGender()!="")tr.setGender(user.getGender());
                    if(user.getAddress()!="")tr.setAddress(user.getAddress());
                    if(user.getEmail()!="")tr.setEmail(user.getEmail());
                    if(user.getPhone()!="")tr.setPhone(user.getPhone());
                    if(user.getUsername()!="")tr.setUsername(user.getUsername());
                    if(user.getPassword()!="")tr.setPassword(user.getPassword());
                    if(user.getStatus()!= tr.getStatus()) tr.setStatus(user.getStatus());

                    return repo.save(user);
                })
                .orElseGet(() -> {
                    return repo.save(user);
                });
    }

    public void removeUserById(String id){
        repo.deleteUserById(id);
    }
    public void removeUserByusername(String username){
        repo.deleteUserByUsername(username);
    }


}
