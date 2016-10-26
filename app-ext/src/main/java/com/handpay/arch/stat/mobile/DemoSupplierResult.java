package com.handpay.arch.stat.mobile;

import com.handpay.arch.stat.anno.GroupKey;

public class DemoSupplierResult extends DemoResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GroupKey
	private String supplier;

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DemoSupplierResult [orderCount=").append(getOrderCount()).append(", orderPaidCount=").append(getOrderPaidCount())
		.append(", deliveringCount=").append(getDeliveringCount()).append(", deliveredCount=").append(getDeliveredCount())
		.append(", deliverFailCount=").append(getDeliverFailCount()).append(", threeMinCount=").append(getThreeMinCount())
		.append(", tenMinCount=").append(getTenMinCount()).append(", deliveredRatio=").append(getDeliveredRatio());
		builder.append(", supplier=").append(supplier).append("]");
		return builder.toString();
	}
}
