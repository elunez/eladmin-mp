package me.zhengjie.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import lombok.Data;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器入口
 *
 * @author odboy
 * @date 2025-01-11
 */
@Data
public class CmdGenHelper {
    /**
     * 数据库类型 MySQL
     */
    private static final IDbQuery DB_QUERY = new MySqlQuery();
    /**
     * 类型转换
     */
    private static final String TYPE_TINY_INT = "tinyint";
    private static final String TYPE_DATETIME = "datetime";
    private static final String TYPE_DATE = "date";
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String driverClassName;
    private String parentPackageName;
    private String parentPackageModuleName;

    /**
     * 代码生成函数
     *
     * @param tablePrefix 表前缀(可空)
     * @param tableNames  表名称, 多个用逗号分隔
     */
    public void gen(String tablePrefix, List<String> tableNames) {
        // 1.数据库配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig
                .Builder(this.databaseUrl, this.databaseUsername, this.databasePassword)
                .driverClassName(this.driverClassName);
        dataSourceConfigBuilder.dbQuery(DB_QUERY);
        dataSourceConfigBuilder.keyWordsHandler(new MySqlKeyWordsHandler());
        // 1.1.快速生成器
        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(dataSourceConfigBuilder);
        // 2.全局配置
        // 自定义类型转换
        fastAutoGenerator.dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
            if (typeCode == Types.SMALLINT) {
                return DbColumnType.INTEGER;
            }
            if (typeCode == Types.DATE) {
                return DbColumnType.DATE;
            }
            if (typeCode == Types.TIME) {
                return DbColumnType.DATE;
            }
            if (typeCode == Types.TIMESTAMP) {
                return DbColumnType.DATE;
            }
            return typeRegistry.getColumnType(metaInfo);
        }));
        // 覆盖已生成文件
        // 添加swagger注解
        // 设置注释的作者
        // 设置注释的日期格式
        // 使用java8新的时间类型
        fastAutoGenerator.globalConfig(globalConfigBuilder -> globalConfigBuilder
                .enableSwagger()
                .outputDir(System.getProperty("os.name").toLowerCase().contains("win") ? "C:\\CodeGen" : "/tmp/CodeGen")
                .author("codegen")
                .commentDate("yyyy-MM-dd")
                .dateType(DateType.TIME_PACK));
        // 3.包配置
        // 设置父包名
        // 设置父包模块名
        // 设置MVC下各个模块的包名
        // 设置XML资源文件的目录
        fastAutoGenerator.packageConfig(packageConfigBuilder -> packageConfigBuilder
                .parent(this.parentPackageName == null ? "cn.codegen" : this.parentPackageName)
                .moduleName(this.parentPackageModuleName == null ? "moduleName" : this.parentPackageModuleName)
                .entity("domain")
                .service("service")
                .mapper("mapper")
                .serviceImpl("service.impl")
                .controller("rest")
                .pathInfo(Collections.singletonMap(OutputFile.xml, "CodeGen\\resources\\mapper")));
        // 4.模板配置
        // 使用Freemarker引擎模板, 默认的是Velocity引擎模板
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        fastAutoGenerator.templateEngine(templateEngine);
        // 5.策略配置
        // 设置需要生成的表名
        // 设置过滤表前缀
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude(tableNames)
                .mapperBuilder()
                .build()
        );
        if (tablePrefix != null) {
            // 表前缀, 可多个
            fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.addTablePrefix(tablePrefix));
        }
        // 5.1.Entity策略配置
        // 生成实体时生成字段的注解, 包括@TableId注解等
        // 数据库表和字段映射到实体的命名策略, 为下划线转驼峰
        // 全局主键类型为None
        // 实体名称格式化为XXXEntity
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder
                .entityBuilder()
                .enableLombok()
                // 如果不需要生成注解, 去掉.enableTableFieldAnnotation()
                .enableTableFieldAnnotation()
                .enableFileOverride()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .idType(IdType.AUTO));
        //                .formatFileName("%sPO"));
        // 5.2.Controller策略配置
        // 开启生成@RestController控制器
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.controllerBuilder().enableRestStyle());
        // 如果不需要生成Controller
        // fastAutoGenerator.templateConfig(templateConfig -> templateConfig.controller(""));
        // 5.3.Service策略配置
        // 格式化service接口和实现类的文件名称, 去掉默认的ServiceName前面的I
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder
                .serviceBuilder()
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImpl"));
        // 5.4.Mapper策略配置
        // 格式化 mapper文件名,格式化xml实现类文件名称
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder
                .mapperBuilder()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper"));
        // 6.生成代码
        fastAutoGenerator.execute();
    }
}
