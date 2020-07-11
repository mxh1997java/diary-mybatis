package www.maxinhai.com.diarymybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import www.maxinhai.com.diarymybatis.entity.Order;
import www.maxinhai.com.diarymybatis.entity.Product;
import www.maxinhai.com.diarymybatis.service.Impl.SecondKillServiceImpl;
import java.util.List;
import java.util.Map;

@RequestMapping("api/secondKill/")
@RestController
public class SecondKillController extends AbstractController{

    @Autowired
    private SecondKillServiceImpl secondKillService;

    @RequestMapping(value = "intiProductData", method = RequestMethod.POST)
    public Map<String, Object> intiProductData(@RequestParam(value = "productId") Long productId) throws Exception {
        boolean result = secondKillService.intiProductData(productId);
        if(result) {
            return getSuccess();
        }
        return getFailure();
    }


    @RequestMapping(value = "secondKill", method = RequestMethod.POST)
    public Map<String, Object> secondKill(@RequestBody Map<String, Object> params) throws Exception {
        boolean result = secondKillService.secondKill(params);
        if(result) {
            return getSuccess();
        }
        return getFailure();
    }

    @Transactional
    @RequestMapping(value = "batchImportData", method = RequestMethod.POST)
    public Map<String, Object> batchImportData(@RequestBody List<Order> orderList) throws  Exception {
        int result = secondKillService.batchImportData(orderList);
        if(result == orderList.size()) {
            return getSuccess();
        }
        return getFailure();
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public Map<String, Object> addProduct(@RequestBody Product product) throws Exception {
        int result = secondKillService.addProductInfo(product);
        if(result > 0) {
            return getSuccess();
        }
        return getFailure();
    }

}
