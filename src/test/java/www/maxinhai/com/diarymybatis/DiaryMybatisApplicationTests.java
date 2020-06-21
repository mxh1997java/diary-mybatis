package www.maxinhai.com.diarymybatis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import www.maxinhai.com.diarymybatis.util.BaseResponse;
import www.maxinhai.com.diarymybatis.util.CodeEnum;
import www.maxinhai.com.diarymybatis.util.ExcelUtils;
import www.maxinhai.com.diarymybatis.util.ResponseData;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiaryMybatisApplicationTests {

    /**
     * excel工具类测试
     */
    @Test
    public void excelUtilsTest() {

        try {
            List<List<Object>> list = ExcelUtils.readExcelFirstSheet("D:\\Users\\15735400536\\Downloads\\考勤-马鑫海-3月.xlsx");
            list.forEach(item -> {
                item.forEach(element -> {
                    System.out.print(element);
                });
                System.out.println("");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void ResponseTest() {
        BaseResponse out = BaseResponse.out(CodeEnum.SUCCESS);
        System.out.println("基本响应封装:\n" + JSON.toJSONString(out));
        List<String> list = new LinkedList<>();
        list.add(UUID.randomUUID().toString());
        ResponseData<List<String>> data = ResponseData.out(CodeEnum.FAIL, list);
        System.out.println("响应数据结构封装:\n" + JSON.toJSONString(data));
    }

}
