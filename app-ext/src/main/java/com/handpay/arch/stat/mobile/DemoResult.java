package com.handpay.arch.stat.mobile;

import java.io.Serializable;

import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.CommonResult;

public class DemoResult extends CommonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ValueKey(order=20)
	private String orderCount;
	private String orderPaidCount;
	private String deliveringCount;
	private String deliveredCount;
	private String deliverFailCount;
	private String threeMinCount;
	private String tenMinCount;
	@ValueKey(order=10)
	private String deliveredRatio;

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

	public String getDeliveringCount() {
		return deliveringCount;
	}

	public void setDeliveringCount(String deliveringCount) {
		this.deliveringCount = deliveringCount;
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

	public String getThreeMinCount() {
		return threeMinCount;
	}

	public void setThreeMinCount(String threeMinCount) {
		this.threeMinCount = threeMinCount;
	}

	public String getTenMinCount() {
		return tenMinCount;
	}

	public void setTenMinCount(String tenMinCount) {
		this.tenMinCount = tenMinCount;
	}

	public String getDeliveredRatio() {
		return deliveredRatio;
	}

	public void setDeliveredRatio(String deliveredRatio) {
		this.deliveredRatio = deliveredRatio;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DemoResult [orderCount=").append(orderCount).append(", orderPaidCount=").append(orderPaidCount)
				.append(", deliveringCount=").append(deliveringCount).append(", deliveredCount=").append(deliveredCount)
				.append(", deliverFailCount=").append(deliverFailCount).append(", threeMinCount=").append(threeMinCount)
				.append(", tenMinCount=").append(tenMinCount).append(", deliveredRatio=").append(deliveredRatio).append("]");
		return builder.toString();
	}
}
