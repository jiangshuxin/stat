package com.handpay.arch.stat.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.handpay.arch.common.ResultVO;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.manager.StreamManager;
import com.handpay.arch.stat.provider.StreamProvider;

@Controller
@RequestMapping({ "/stat" })
public class StreamAction {
	private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private StreamProvider streamProvider;
	
	@Autowired
	private StreamManager streamManager;

	@RequestMapping({ "/days.json" })
	@ResponseBody
	public String[] days(String statName){
		Set<String> set = streamProvider.days(statName);
		return set.toArray(new String[0]);
	}
	
	@RequestMapping({ "/timeValueSet.json" })
	@ResponseBody
	public CommonResult[] timeValueSet(String statName,String day,String fromDate,String toDate) throws ParseException{
		List<?> list = null;
		if(StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)){
			list = streamProvider.timeValueSet(statName, day);
		}else{
			list = streamProvider.timeValueSet(statName,dateFormat.parse(fromDate),dateFormat.parse(toDate));
		}
		return list.toArray(new CommonResult[0]);
	}
	
	@RequestMapping({ "/stats.json" })
	@ResponseBody
	public ResultVO<Collection<StatBean>> stats(String statName,String day){
		return ResultVO.buildSucResult(streamManager.stats());
	}
	
	@RequestMapping({ "/sum.json" })
	@ResponseBody
	public Map<String,Map<String,BigDecimal>> sum(String statName,String fromDate,String toDate,String day,String[] column,String groupKey) throws ParseException{
		if(StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)){
			return streamProvider.sum(statName, dateFormat.parse(fromDate), dateFormat.parse(toDate), groupKey,column);
		}else if(StringUtils.isNotEmpty(day)){
			return streamProvider.sum(statName, day, groupKey,column);
		}
		return null;
	}
}
