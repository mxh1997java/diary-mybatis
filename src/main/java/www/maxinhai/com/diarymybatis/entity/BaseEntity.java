package www.maxinhai.com.diarymybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类
 */
public abstract class BaseEntity implements Serializable {

    @Setter
    @Getter
    protected static final long serialVersionUID = 1L;

    @Setter
    @Getter
    protected String name;

    @Setter
    @Getter
    protected String description;

    /**
     * 创建时间
     */
    @Setter
    @Getter
    protected Date createTime;

    /**
     * 修改时间
     */
    @Setter
    @Getter
    protected Date modifyTime;

}
