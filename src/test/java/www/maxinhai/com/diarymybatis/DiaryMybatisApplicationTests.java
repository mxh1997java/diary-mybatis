package www.maxinhai.com.diarymybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import www.maxinhai.com.diarymybatis.util.ExcelUtils;
import java.io.IOException;
import java.util.List;

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

}
