package demo.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonFilter("UserInfoV2")
public class UserV2 extends User {
    private String grade;
}
