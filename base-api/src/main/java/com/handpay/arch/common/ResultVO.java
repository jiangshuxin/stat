package com.handpay.arch.common;

import java.io.Serializable;

public class ResultVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7336078000462718603L;
	public static final String SUCCESS = "0";
	public static final String FAILED = "1";
	private String ret;
	private String errmsg;
	private T data;
	
	public static <T> ResultVO<T> buildErrResult(String errmsg){
		ResultVO<T> re = new ResultVO<T>();
		re.setErrmsg(errmsg);
		re.setRet(FAILED);
		return re;
	}
	
	public static <T> ResultVO<T> buildSucResult(T data){
		ResultVO<T> re = new ResultVO<T>();
		re.setData(data);
		re.setRet(SUCCESS);
		return re;
	}
	
	public static <T> ResultVO<T> buildSucResult(){
		ResultVO<T> re = new ResultVO<T>();
		re.setRet(SUCCESS);
		return re;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public boolean isSuccess(){
		return SUCCESS.equals(getRet());
	}
}
