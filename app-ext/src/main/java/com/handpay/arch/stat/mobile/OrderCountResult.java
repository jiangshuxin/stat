package com.handpay.arch.stat.mobile;

import java.io.Serializable;

import com.handpay.arch.stat.bean.CommonResult;

public class OrderCountResult extends CommonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String totalCount;
	private String supplierCount;
	private String channelCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(String supplierCount) {
		this.supplierCount = supplierCount;
	}

	public String getChannelCount() {
		return channelCount;
	}

	public void setChannelCount(String channelCount) {
		this.channelCount = channelCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderCountResult [totalCount=").append(totalCount).append(", supplierCount=")
				.append(supplierCount).append(", channelCount=").append(channelCount).append("]");
		return builder.toString();
	}
}
