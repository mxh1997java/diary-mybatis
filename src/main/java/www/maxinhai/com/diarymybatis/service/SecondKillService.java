package www.maxinhai.com.diarymybatis.service;

import www.maxinhai.com.diarymybatis.entity.Order;
import www.maxinhai.com.diarymybatis.entity.Product;
import java.util.List;
import java.util.Map;

public interface SecondKillService {

    int addProductInfo(Product product) throws Exception;

    boolean intiProductData(Long productId) throws Exception;

    boolean secondKill(Map<String, Object> params) throws Exception;

    int batchImportData(List<Order> orderList) throws Exception;

    int removeOrder(String productName, String orderNo) throws Exception;

    int createOrder(Order order) throws Exception;

    int scheduledTasks() throws Exception;

}
