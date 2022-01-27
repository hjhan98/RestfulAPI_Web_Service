package demo.restfulwebservice.service;

import demo.restfulwebservice.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;
    static{
        users.add(new User(1, "한하정", new Date(), "0000", "1"));
        users.add(new User(2, "한하정2", new Date(), "0000", "1"));
        users.add(new User(3, "한하정3", new Date(), "0000", "1"));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id){
        for(User user : users){
            if(user.getId() == id){
                System.out.println(user);
                return user;
            }
        }
        return null;
    }

    public User save(User user){
        if(user.getId() == null){   //전달받은 user 객체에 ID 존재하지 않으면
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator(); //리스트를 순차적으로 사용하기 위해 열거형 데이터 타입으로 변환

        while(iterator.hasNext()){
            User user = iterator.next();    //users 안에 들어가있는 데이터들 순차적으로 user에 들어감

            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    //수정
    //PUT : 전체 수정 vs PATCH : 일부 수정
    public User update(User user, int id){
        System.out.println(user);
        User findUser = findOne(id);
        findUser.setId(id);
        findUser.setName(user.getName());
        findUser.setDateTime(user.getDateTime());

        return findUser;
    }
}
