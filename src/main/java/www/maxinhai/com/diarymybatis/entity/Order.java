package www.maxinhai.com.diarymybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * 功能描述: 订单实体类
 * @Author: 15735400536
 * @Date: 2020/7/11 16:21
 */
@Data
public class Order extends BaseEntity {

    //订单id
    private Long orderId;

    //订单编号
    private String orderNo;

    //商品id
    private Long productId;

    //商品名称
    private String productName;

    //用户id
    private Long userId;

    //用户名称
    private String username;

    //发货地址
    private String address;

    //订单状态
    private Integer orderState;

    //有效时间
    private Integer maxTime;

    //失效时间
    private Date invalidTime;
}
