package demo.restfulwebservice.Controller;

import demo.restfulwebservice.Exception.UserNotFoundException;
import demo.restfulwebservice.domain.User;
import demo.restfulwebservice.service.UserDaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    //DI : (생성자 통해서)의존성 주입
    // 생성자의 매개변수를 통해 우리가 전달하고자 하는 객체의 인스턴스를 선언
    public UserController(UserDaoService service) {
        //전달되어진 service를 할당
        this.service = service;
    }

    //전체 사용자 목록 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    //사용자 한명 조회
    //Get /users/{id}
    @GetMapping("users/{id}")
    public User retriveUser(@PathVariable int id) {
        User user = service.findOne(id);                    //ctrl + alt + v : Introduce Variable

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return user;
    }

//    @PostMapping("/users")
//    public void createUser(@RequestBody User user){
//        User savedUser = service.save(user);
//}
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = service.save(user);

        //응답 코드 제어  ex. Status : '201 Crated'
       URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id){
        System.out.println(user);
        return service.update(user, id);
    }

}
