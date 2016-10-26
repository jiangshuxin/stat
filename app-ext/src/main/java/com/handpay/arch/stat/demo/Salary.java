package com.handpay.arch.stat.demo;

import java.io.Serializable;

public class Salary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serialNo;
	private String name;
	private String salary;
	private String department;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Salary [serialNo=").append(serialNo).append(", name=").append(name).append(", salary=")
				.append(salary).append(", department=").append(department).append("]");
		return builder.toString();
	}
}
