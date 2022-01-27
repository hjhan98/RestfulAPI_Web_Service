package demo.restfulwebservice.helloworld;

//import demo.restfulwebservice.Bean.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    //GET Method
    // URI: /hello-world(endpoint)
    @GetMapping("/hello-world")
    public String helloWorld(){
        return "안녕 하정아";
    }

//    // 자바 빈 형태로 데이터 반환시키기 (Json 형태로 변환하여 반환)
//    @GetMapping("/hello-world-bean")
//    public HelloWorldBean helloWorldBean(){
//        return new HelloWorldBean("안녕 하정아");
//    }
//
//    //Path Variable
//    @GetMapping("/hello-world-bean/{name}")
//    public HelloWorldBean helloWorldBean(@PathVariable String name){
//        return new HelloWorldBean(String.format("안녕 %s아", name));
//    }
}


