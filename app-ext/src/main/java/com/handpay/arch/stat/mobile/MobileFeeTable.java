package com.handpay.arch.stat.mobile;

import java.io.Serializable;

public class MobileFeeTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serialNo;
	private String orderNo;
	private String orderDate;
	private String deliveryFlag;//0等待发货 1发货中 2发货完成 3发货失败
	private Long deliveryDate;
	private String refundFlag;
	private Long refundDate;
	private String blockFlag;
	private Long blockDate;
	private String payFlag;
	private Long payDate;
	private String supplier;
	private String channel;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public Long getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Long deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public Long getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Long refundDate) {
		this.refundDate = refundDate;
	}

	public String getBlockFlag() {
		return blockFlag;
	}

	public void setBlockFlag(String blockFlag) {
		this.blockFlag = blockFlag;
	}

	public Long getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Long blockDate) {
		this.blockDate = blockDate;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public Long getPayDate() {
		return payDate;
	}

	public void setPayDate(Long payDate) {
		this.payDate = payDate;
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
}

//select count(1) as totalCount,(select count(1) from MobileFeeTable where deliveryFlag='3') as failedCount,(select count(1) from MobileFeeTable where (deliveryDate-payDate<600000))/totalCount as tenMinutesRate from MobileFeeTable