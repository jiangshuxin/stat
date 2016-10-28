package com.handpay.arch.stat.chart.action;

import com.google.common.collect.Lists;
import com.handpay.arch.common.ResultVO;
import com.handpay.arch.stat.chart.ChartConfig;
import com.handpay.arch.stat.chart.ChartType;
import com.handpay.arch.stat.chart.LineArea;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sxjiang on 2016/10/27.
 */
@RestController
public class MockAction {

    @RequestMapping("/mock/linearea")
    public ResultVO<LineArea> listChart() {
        LineArea la = new LineArea();
        la.setTitle("公司产品销售表");
        la.setxAxisDataList(Lists.newArrayList("周一","周二"));

        List<LineArea.Series> seriesList = Lists.newArrayList();
        LineArea.Series series = new LineArea.Series();
        series.setName("Top");
        series.setDataList(Lists.newArrayList(RandomStringUtils.randomNumeric(2),RandomStringUtils.randomNumeric(2)));
        seriesList.add(series);
        LineArea.Series series1 = new LineArea.Series();
        series1.setName("ICS");
        series1.setDataList(Lists.newArrayList(RandomStringUtils.randomNumeric(2),RandomStringUtils.randomNumeric(2)));
        seriesList.add(series1);
        LineArea.Series series2 = new LineArea.Series();
        series2.setName("IBS");
        series2.setDataList(Lists.newArrayList(RandomStringUtils.randomNumeric(2),RandomStringUtils.randomNumeric(2)));
        seriesList.add(series2);

        la.setSeriesList(seriesList);
        return ResultVO.buildSucResult(la);
    }
}
