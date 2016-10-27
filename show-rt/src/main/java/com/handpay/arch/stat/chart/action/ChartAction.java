package com.handpay.arch.stat.chart.action;

import com.google.common.collect.Lists;
import com.handpay.arch.common.ResultVO;
import com.handpay.arch.stat.chart.ChartConfig;
import com.handpay.arch.stat.chart.ChartType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sxjiang on 2016/10/27.
 */
@RestController
public class ChartAction {

    @RequestMapping("/chart/list")
    public ResultVO<List<ChartConfig>> listChart() {
        ChartConfig cc = new ChartConfig();
        cc.setInterval(3000L);
        cc.setName("测试图表");
        cc.setSaved(false);
        cc.setType(ChartType.LineArea);
        cc.setUrl("/show-rt/mock/linearea");
        List<ChartConfig> list = Lists.newArrayList();
        list.add(cc);
        return ResultVO.buildSucResult(list);
    }
}
