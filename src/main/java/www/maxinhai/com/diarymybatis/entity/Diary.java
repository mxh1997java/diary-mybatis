package www.maxinhai.com.diarymybatis.entity;

import lombok.Data;

@Data
public class Diary extends BaseEntity {

    private Long id;

    private String title;

    private String content;

    private String author_id;

    private String author;

}
