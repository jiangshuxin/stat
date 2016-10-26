package com.handpay.arch.stat.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class CommonResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private Date now = new Date();
	private Long nowLong;
	private String yyyyMMdd;
	private String hhmmss;
	
	private void fillTime(){
		nowLong = now.getTime();
		String complete = DateFormatUtils.format(now, DATE_PATTERN);
		String[] array = complete.split(" ");
		if (array.length == 2) {
			yyyyMMdd = array[0];
			hhmmss = array[1];
		}
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
		fillTime();
	}

	public String getYyyyMMdd() {
		return yyyyMMdd;
	}

	public void setYyyyMMdd(String yyyyMMdd) {
		this.yyyyMMdd = yyyyMMdd;
	}

	public String getHhmmss() {
		return hhmmss;
	}

	public void setHhmmss(String hhmmss) {
		this.hhmmss = hhmmss;
	}
	
	public Long getNowLong() {
		return nowLong;
	}

	public void setNowLong(Long nowLong) {
		this.nowLong = nowLong;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonResult [now=").append(now).append(", nowLong=").append(nowLong).append(", yyyyMMdd=")
				.append(yyyyMMdd).append(", hhmmss=").append(hhmmss).append("]");
		return builder.toString();
	}
}
