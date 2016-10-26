package com.handpay.arch.stat.mobile;

import java.io.Serializable;

public class SimpleCountTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private String orderNumber;
	private String orderPaidNumber;
	private String deliveredNumber;
	private String deliverFailNumber;
	private String type;//supplier topup
	private String supplier;
	private String channel;
	private String createTime;
	
	{
		orderNumber = "0";
		orderPaidNumber = "0";
		deliveredNumber = "0";
		deliverFailNumber = "0";
		createTime = String.valueOf(System.currentTimeMillis());
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderPaidNumber() {
		return orderPaidNumber;
	}

	public void setOrderPaidNumber(String orderPaidNumber) {
		this.orderPaidNumber = orderPaidNumber;
	}

	public String getDeliveredNumber() {
		return deliveredNumber;
	}

	public void setDeliveredNumber(String deliveredNumber) {
		this.deliveredNumber = deliveredNumber;
	}

	public String getDeliverFailNumber() {
		return deliverFailNumber;
	}

	public void setDeliverFailNumber(String deliverFailNumber) {
		this.deliverFailNumber = deliverFailNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DemoTable [orderNumber=").append(orderNumber).append(", orderPaidNumber=")
				.append(orderPaidNumber)
				.append(", deliveredNumber=").append(deliveredNumber).append(", deliverFailNumber=")
				.append(deliverFailNumber).append(", supplier=").append(supplier).append(", channel=").append(channel).append(", createTime=").append(createTime)
				.append("]");
		return builder.toString();
	}
}
