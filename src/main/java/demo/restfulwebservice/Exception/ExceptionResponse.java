package demo.restfulwebservice.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor //전체 필드가 있는 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;

}
