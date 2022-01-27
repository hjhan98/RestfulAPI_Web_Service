package demo.restfulwebservice.Controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import demo.restfulwebservice.Exception.UserNotFoundException;
import demo.restfulwebservice.domain.User;
import demo.restfulwebservice.domain.UserV2;
import demo.restfulwebservice.service.UserDaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")   //Prefix : /admin
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    //전체 사용자 목록 조회
    @GetMapping("/users")   // /admin/users
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        // Filter로 전체 사용자 조회 (or @JsonIgnore)
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "dateTime", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    //개별 사용자 조회
    //GET /admin/users/1 -> /admin/v1/users/1
    //@GetMapping("/v1/users/{id}")  // /admin/users/{id}
    //@GetMapping(value = "/users/{id}/", params = "version=1")
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    public MappingJacksonValue retriveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        // Filter로 개별 사용자 조회 (or @JsonIgnore)
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "dateTime", "ssn");
        //위의 필터로 거른 내용 우리가 사용하게 바꿈
        //domain에 써놨던 JsonFilter 값으로 id
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    //URI 이용한 REST API 버전 관리  http://localhost:8080/admin/v2/users/1
    //@GetMapping("/v2/users/{id}")  // /admin/users/{id}
    //Request Parmaeter를 이용한 API 버전 관리  http://localhost:8080/admin/users/1/?version=2
    //@GetMapping(value = "/users/{id}/", params = "version=2")
    //header를 이용한 버전관리  //http://localhost:8080/admin/users/1 (header KEY: X-API-VERSION VAULE : 2)
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    public MappingJacksonValue retriveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User-> User2 객체 복사
        UserV2 v2 = new UserV2();
        BeanUtils.copyProperties(user, v2);
        v2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "dateTime", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(v2);
        mapping.setFilters(filters);

        return mapping;
    }

}
