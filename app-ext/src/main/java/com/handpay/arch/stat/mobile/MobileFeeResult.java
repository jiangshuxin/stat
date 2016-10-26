package com.handpay.arch.stat.mobile;

import java.io.Serializable;

import com.handpay.arch.stat.bean.CommonResult;

public class MobileFeeResult extends CommonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String totalCount;
	private String failedCount;
	private String tenMinutesRate;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(String failedCount) {
		this.failedCount = failedCount;
	}

	public String getTenMinutesRate() {
		return tenMinutesRate;
	}

	public void setTenMinutesRate(String tenMinutesRate) {
		this.tenMinutesRate = tenMinutesRate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MobileFeeResult [totalCount=").append(totalCount).append(", failedCount=").append(failedCount)
				.append(", tenMinutesRate=").append(tenMinutesRate).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((failedCount == null) ? 0 : failedCount.hashCode());
		result = prime * result + ((tenMinutesRate == null) ? 0 : tenMinutesRate.hashCode());
		result = prime * result + ((totalCount == null) ? 0 : totalCount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MobileFeeResult other = (MobileFeeResult) obj;
		if (failedCount == null) {
			if (other.failedCount != null)
				return false;
		} else if (!failedCount.equals(other.failedCount))
			return false;
		if (tenMinutesRate == null) {
			if (other.tenMinutesRate != null)
				return false;
		} else if (!tenMinutesRate.equals(other.tenMinutesRate))
			return false;
		if (totalCount == null) {
			if (other.totalCount != null)
				return false;
		} else if (!totalCount.equals(other.totalCount))
			return false;
		return true;
	}
}
