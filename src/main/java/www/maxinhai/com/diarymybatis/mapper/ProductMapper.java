package www.maxinhai.com.diarymybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import www.maxinhai.com.diarymybatis.entity.Product;

import java.util.Map;

@Repository
@Mapper
public interface ProductMapper {

    /**
     * 功能描述: 添加产品数据
     * @Param: [product]
     * @Return: int
     * @Author: 15735400536
     * @Date: 2020/7/11 16:55
     */
    int addProduct(Product product);

    /**
     * 功能描述: 根据条件查询出一个条产品数据
     * @Param: [params]
     * @Return: www.maxinhai.com.diarymybatis.entity.Product
     * @Author: 15735400536
     * @Date: 2020/7/11 16:55
     */
    Product findOneByCondition(Map<String, Object> params);

}
