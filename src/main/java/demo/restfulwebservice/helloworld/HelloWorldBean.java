package demo.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data                   //이거 하면 getter setter 필요없음
//@AllArgsConstructor     //이거 하면 생성자 필요없음
//@NoArgsConstructor
public class HelloWorldBean {
    private String message;
}
