package com.handpay.arch.stat.mobile;

import java.io.Serializable;

import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.CommonResult;

public class SimpleCountResult extends CommonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ValueKey(order=20)
	private String orderCount;
	private String orderPaidCount;
	private String deliveredCount;
	private String deliverFailCount;

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getOrderPaidCount() {
		return orderPaidCount;
	}

	public void setOrderPaidCount(String orderPaidCount) {
		this.orderPaidCount = orderPaidCount;
	}

	public String getDeliveredCount() {
		return deliveredCount;
	}

	public void setDeliveredCount(String deliveredCount) {
		this.deliveredCount = deliveredCount;
	}

	public String getDeliverFailCount() {
		return deliverFailCount;
	}

	public void setDeliverFailCount(String deliverFailCount) {
		this.deliverFailCount = deliverFailCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DemoResult [orderCount=").append(orderCount).append(", orderPaidCount=").append(orderPaidCount)
				.append(", deliveredCount=").append(deliveredCount)
				.append(", deliverFailCount=").append(deliverFailCount)
				.append("]");
		return builder.toString();
	}
}
