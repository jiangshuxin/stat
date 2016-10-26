package com.handpay.arch.stat.demo;

import java.io.Serializable;
import java.util.Date;

public class AvgSalaryDept implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String department;
	private String salary;
	private Date date = new Date();

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AvgSalaryDept [department=").append(department).append(", salary=").append(salary)
				.append(", date=").append(date).append("]");
		return builder.toString();
	}
}
