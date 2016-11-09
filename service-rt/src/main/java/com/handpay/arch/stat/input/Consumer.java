package com.handpay.arch.stat.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.output.StreamWriter;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class Consumer implements Runnable {
	private final ConsumerConnector consumer;
	private final StatBean statBean;
	private StreamWriter writer;

	public Consumer(StatBean statBean) {
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
		this.statBean = statBean;
	}

	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect", "10.48.193.210:2181");// ,10.48.193.211:2181
		props.put("group.id", "myGroup-" + System.getProperty("env.type") + "-consumer-group");
		props.put("zookeeper.session.timeout.ms", "4000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");

		return new ConsumerConfig(props);

	}

	public void run() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(statBean.getTopic(), new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(statBean.getTopic()).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {
			writer.write(statBean, (CommonResult) JSON.parse(new String(it.next().message())));
		}
	}

	public void setWriter(StreamWriter writer) {
		this.writer = writer;
	}
}
