package demo.restfulwebservice.Controller;

import demo.restfulwebservice.Exception.UserNotFoundException;
import demo.restfulwebservice.Repository.UserRepository;
import demo.restfulwebservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/jpa")     //prefix value
public class UserJpaController {
    @Autowired      //DI위해
    private UserRepository userRepository;

    //http://localhost:8080/jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/users/{id}")
    public User retriveOneUsers(@PathVariable  int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        //HATEOAS 기능 구현
//        Resource<User> resource = new Resource<>(user.get());
//        ControllerLinkBulder linkTo = linkTo(methodOn(this.getClass()).retriveAllUsers());
//        resource.add(linkTo.withRel("all-users"));
//        return resource;

        return user.get();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User saveUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{id}")
    @Transactional
    public User update(@RequestBody User user, @PathVariable int id){
        Optional<User> updateUser = userRepository.findById(id);

        if(updateUser.isEmpty()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        updateUser.get().setId(id);
        updateUser.get().setName(user.getName());
        updateUser.get().setDateTime(user.getDateTime());
        updateUser.get().setPassword(user.getPassword());
        updateUser.get().setSsn(user.getSsn());

        return updateUser.get();
//        return userRepository.save(user);
    }
}
