package www.maxinhai.com.diarymybatis.entity;

import lombok.Data;

/**
 * 功能描述: 产品实体类
 * @Author: 15735400536
 * @Date: 2020/7/11 16:21
 */
@Data
public class Product extends BaseEntity {

    //产品id
    private Long productId;

    private String productName;

    //产品单价
    private Double productPrice;

    //产品数量
    private Integer productNum;

}
