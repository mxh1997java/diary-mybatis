package www.maxinhai.com.diarymybatis.config.hasor;

import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.spring.SpringModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

/**
 * 功能描述: Spring Boot 和 Hasor 本是两个独立的容器框架，
 *           我们做整合之后为了使用 Dataway 的能力需要把 Spring 中的数据源设置到 Hasor 中。
 * @Author: 15735400536
 * @Date: 2020/7/28 10:35
 */
@DimModule
@Component
public class HasorModule implements SpringModule {

    @Autowired
    private DataSource dataSource = null;

    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        // .DataSource form Spring boot into Hasor
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }

}
