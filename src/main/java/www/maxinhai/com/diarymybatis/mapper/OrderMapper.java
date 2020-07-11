package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.Order;
import java.util.List;

@Repository
@Mapper
public interface OrderMapper {

    /**
     * 功能描述: 添加订单数据
     * @Param: [order]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 16:54
     */
    int addOrder(Order order);

    /**
     * 功能描述: 批量添加订单数据
     * @Param: [orderList]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 16:54
     */
    int addOrderBatch(List<Order> orderList);

}
