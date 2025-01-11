package me.zhengjie;

import me.zhengjie.utils.CmdGenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 代码生成
 *
 * @author odboy
 * @date 2025-01-11
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodeGenApplicationTests {
    @Value("${spring.datasource.druid.url}")
    private String databaseUrl;
    @Value("${spring.datasource.druid.username}")
    private String databaseUsername;
    @Value("${spring.datasource.druid.password}")
    private String databasePassword;
    @Value("${spring.datasource.druid.driverClassName}")
    private String driverClassName;

    @Test
    public void contextLoads() {
        CmdGenHelper generator = new CmdGenHelper();
        generator.setDatabaseUrl(databaseUrl);
        generator.setDatabaseUsername(databaseUsername);
        generator.setDatabasePassword(databasePassword);
        generator.setDriverClassName(driverClassName);
        generator.gen("mnt_", List.of(
                "mnt_app",
                "mnt_deploy"
        ));
    }

    public static void main(String[] args) {
    }
}

