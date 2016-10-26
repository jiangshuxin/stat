package com.handpay.arch.stat.streaming;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.handpay.arch.stat.mobile.MobileFeeResult;

public class Test {

	public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Person p = new Person();
		p.setName("sxjiang");
		Person c = new Person();
		c.setName("znjiang");
		p.setChild(c);
		System.out.println(JSON.toJSONString(c));
		System.out.println(JSON.toJSONString(p));
		
		Process pro = Runtime.getRuntime().exec("jps");
		System.out.println(IOUtils.readLines(pro.getInputStream()));
		System.out.println(IOUtils.readLines(pro.getErrorStream()));
		
		MobileFeeResult r = new MobileFeeResult();
		r.setFailedCount("test");
		System.out.println(PropertyUtils.describe(r));
	}

	static class Person{
		String name;
		Person child;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Person getChild() {
			return child;
		}
		public void setChild(Person child) {
			this.child = child;
		}
	}
}
