package me.zhengjie.infra.monitor;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Druid监控数据
 *
 * @date 2024-09-05
 */
@Api(tags = "系统：数据源监控")
@RestController
@RequestMapping(value = "/druid")
public class DruidMonitorController {
    @ApiOperation("获取数据源的监控数据")
    @GetMapping("/stat")
    public List<Map<String, Object>> druidStat() {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}