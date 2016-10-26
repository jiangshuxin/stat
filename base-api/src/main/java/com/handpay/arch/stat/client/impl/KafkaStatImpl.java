package com.handpay.arch.stat.client.impl;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.client.Stat;
import com.handpay.arch.stat.manager.StreamManager;

public class KafkaStatImpl implements Stat {
	private KafkaProducer strProducer;
	private KafkaProducer byteProducer;
	@Autowired
	private StreamManager manager;

	@Override
	public void submit(String statName, Object target) {
		StatBean statBean = manager.findStat(statName);
		if(String.class.isAssignableFrom(target.getClass())){
			strProducer.send(new ProducerRecord(statBean.getTopic(),(String)target));
		}else{//json方式处理
			byteProducer.send(new ProducerRecord(statBean.getTopic(),JSON.toJSONStringWithDateFormat(target, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteClassName,SerializerFeature.WriteNullStringAsEmpty).getBytes()));
		}
	}

	public KafkaStatImpl(){
		Properties prop1 = new Properties();
		//FIXME
		prop1.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"10.48.193.210:9092,10.48.193.211:9092");
		prop1.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		prop1.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		strProducer = new KafkaProducer(prop1);
		
		Properties prop2 = new Properties();
		//FIXME
		prop2.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"10.48.193.210:9092,10.48.193.211:9092");
		prop2.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		prop2.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		byteProducer = new KafkaProducer(prop2);
	}

	public void destory(){
		strProducer.close();
	}
}
