package www.maxinhai.com.diarymybatis.entity;

import lombok.Data;

@Data
public class User extends BaseEntity {

    private Long user_id;

    private String username;

    private String password;

}
