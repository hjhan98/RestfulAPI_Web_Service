package demo.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data               //필드만 정의함으로써 클래스가 가지고 있어야할 생성자, getter, setter 메소드 자동 정의됨
@AllArgsConstructor //모든 필드에 대해 파라미터로 갖는 생성자 추가
@NoArgsConstructor  //default 생성자 처리 (이거 없으면 상속받는데 에러남)
//@JsonIgnoreProperties(value = {"password", "ssn"})
@Entity
//@JsonFilter("UserInfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Incresement
    private Integer id;
    private String name;
    private Date dateTime;
    private String password;
    private String ssn;
}
