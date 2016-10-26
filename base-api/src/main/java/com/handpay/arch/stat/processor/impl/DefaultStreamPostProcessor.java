package com.handpay.arch.stat.processor.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.processor.StreamPostProcessor;

public class DefaultStreamPostProcessor implements StreamPostProcessor{
	
	private KafkaProducer producer;
	
	public DefaultStreamPostProcessor(){
		Properties prop = new Properties();
		//FIXME
		prop.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"10.48.193.210:9092,10.48.193.211:9092");
		prop.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		prop.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		producer = new KafkaProducer(prop);
	}

	public void process(StatBean statBean,List<Serializable> list) {
		
		for(Serializable obj : list){
			producer.send(new ProducerRecord(statBean.getResultTopic(),JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteClassName,SerializerFeature.WriteNullStringAsEmpty).getBytes()));
		}
	}
	
	public void destory(){
		producer.close();
	}
}
