package www.maxinhai.com.diarymybatis.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RegisteredInfo extends BaseEntity {

    private Long id;

    private String username;

    private Date registerTime;

    private String creator;

}
