package me.zhengjie.infra.context;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import me.zhengjie.constant.ElConstant;
import me.zhengjie.utils.SpringBeanHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 应用上下文
 *
 * @author odboy
 * @date 2025-01-12
 */
@Data
@Configuration
public class ElContext {
    public static String applicationName;

//    @Value("${spring.application.name}")
//    public void setApplicationName(String name) {
//        ElContext.applicationName = name;
//    }

    @Bean
    public BeanFactoryPostProcessor configLoader(ConfigurableEnvironment environment) {
        return configurableListableBeanFactory -> {
            String propertyValue = environment.getProperty("spring.application.name", String.class, ElConstant.APP_NAME);
            if (StrUtil.isBlank(propertyValue)) {
                ElContext.applicationName = ElConstant.APP_NAME;
            } else {
                ElContext.applicationName = propertyValue;
            }
        };
    }

    @Bean
    public SpringBeanHolder springContextHolder() {
        return new SpringBeanHolder();
    }
}
