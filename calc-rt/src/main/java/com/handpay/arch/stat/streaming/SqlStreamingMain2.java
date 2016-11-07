/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.handpay.arch.stat.streaming;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.filter.StreamFilter;
import com.handpay.arch.stat.manager.StreamManager;
import com.handpay.arch.stat.parser.StreamParser;
import com.handpay.arch.stat.processor.StreamPostProcessor;

import scala.Tuple2;

public final class SqlStreamingMain2 {

	private SqlStreamingMain2() {
	}

	//TODO 1.设置Duration👌  2.设置过滤，防止不同类型数据参与计算👌  3.支持多个计算任务并行👌  4.后置处理器👌
	//TODO A.所有原始数据都进同一个消息队列；B.不同类型数据进不同队列
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-application.xml");
		
		if (args.length < 3) {
			System.err.println("Usage: SqlStreamingMain <zkQuorum> <group> <statName>");
			System.exit(1);
		}

		SparkConf sparkConf = new SparkConf().setAppName("SqlStreamingMain").setMaster("spark://10.48.193.210:7077")
				.setJars(new String[] { "/Users/sxjiang/git/stat_github/calc-rt/target/calc-rt-0.0.1-SNAPSHOT.jar"
				,"/Users/sxjiang/git/stat_github/base-api/target/base-api-0.0.1-SNAPSHOT.jar"
				,"/Users/sxjiang/git/stat_github/app-ext/target/app-ext-0.0.1-SNAPSHOT.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/spark-examples-1.6.1-hadoop2.4.0.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/spring-core-3.1.4.RELEASE.jar"		
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/spring-beans-3.1.4.RELEASE.jar"		
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/spring-context-3.1.4.RELEASE.jar"		
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/guava-14.0.1.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/hive-metastore-1.2.1.spark.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/hive-exec-1.2.1.spark.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/hive-cli-1.2.1.spark.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/spark-hive_2.10-1.6.1.jar"
				,"/Users/sxjiang/git/stat/arch.stat/calc-rt/lib/fastjson-1.1.28.jar"
				});

		//int numThreads = Integer.parseInt(args[3]);
		int numThreads = 4;
		//String[] statNames = args[2].split(",");
		String statName = args[2];
		final StreamManager manager = context.getBean(StreamManager.class);
		final StreamPostProcessor processor = context.getBean(StreamPostProcessor.class);
		
		final StreamParser parser = manager.findParser(statName);
		final StreamFilter filter = manager.findFilter(statName);
		final StatBean bean = manager.findStat(statName);
		
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put(bean.getTopic(), numThreads);
		
		//sparkConf.set("spark.cores.max", "2");
		if(bean.getPropertyMap().containsKey("spark_cores_max")){
			sparkConf.set("spark.cores.max", (String)bean.getPropertyMap().get("spark_cores_max"));
		}
		if(bean.getPropertyMap().containsKey("spark_executor_memory")){
			sparkConf.set("spark.executor.memory", (String)bean.getPropertyMap().get("spark_executor_memory"));
		}
		// Create the context with 2 seconds batch size
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		JavaStreamingContext jssc = new JavaStreamingContext(ctx, new Duration(bean.getBatchDuration()));
		JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, args[0], args[1],
				topicMap);
		//messages.print();

		JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> tuple2) {
				return tuple2._2();
			}
		});
		//lines.print();
		lines = lines.filter(new Function<String,Boolean>(){
			@Override
			public Boolean call(String line) throws Exception {
				if(filter == null) return true;
				return filter.isValid(line);
			}});
		//lines.print();

		JavaDStream<Serializable> parsedStream = lines.map(new Function<String,Serializable>(){
			private static final long serialVersionUID = 1L;

			@Override
			public Serializable call(String line) {
				return parser.parse(line, bean.getTableClass());
			}
		});
		//parsedStream.print();
		
		//final HiveContext sqlContext = new HiveContext(ctx);
		final SQLContext sqlContext = new SQLContext(ctx);
		parsedStream = parsedStream.window(new Duration(bean.getWindowDuration()), new Duration(bean.getSlideDuration()));
		String[] sqls = bean.getSql();
		JavaDStream<Serializable> allStream = null;
		for(final String sql : sqls){
			JavaDStream<Serializable> tempStream = parsedStream.transform(new Function<JavaRDD<Serializable>,JavaRDD<Serializable>>(){
				@Override
				public JavaRDD<Serializable> call(JavaRDD<Serializable> t) throws Exception {
					
					Class<?> targetClass = bean.getTableClass();
					DataFrame schemaPeople = sqlContext.createDataFrame(t, targetClass);
					schemaPeople.registerTempTable(targetClass.getSimpleName());
					
					//FIXME sql 处理
					String fSql = null;
					if(sql.indexOf(":NOW") > -1){
						fSql = StringUtils.replace(sql, ":NOW", String.valueOf(System.currentTimeMillis()));
					}else{
						fSql = sql;
					}
					
					DataFrame dataFrame = sqlContext.sql(fSql);
					final String[] columns = dataFrame.columns();//可能只包含Result中部分字段
					JavaRDD<Row> rdd = dataFrame.toJavaRDD();
					//System.out.println(rdd.collect());
					final Date now = new Date();//一批计算结果使用的日期
					return rdd.map(new Function<Row, Serializable>() {
						private static final long serialVersionUID = 1L;
						@Override
						public Serializable call(Row row) {
							return row2Obj(columns, row, now, bean.getResultClass());
						}
					});
					
				}});
			if(allStream == null){
				allStream = tempStream;
			}else{
				allStream = allStream.union(tempStream);
			}
		}
		if(sqls.length > 1){
			allStream = allStream.reduce(new Function2<Serializable,Serializable,Serializable>(){
				@Override
				public Serializable call(Serializable v1, Serializable v2) throws Exception {
					Map map = PropertyUtils.describe(v1);
					map.remove("class");
					List<String> keyList = Lists.newArrayList();
					for(Object key : map.keySet()){
						if(map.get(key) == null && key != null) keyList.add(key.toString());
					}
					BeanUtils.copyProperties(v1, v2, keyList.toArray(new String[0]));
					return v2;
				}});
		}

		allStream.foreachRDD(new VoidFunction<JavaRDD<Serializable>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(JavaRDD<Serializable> t) throws Exception {
				List<Serializable> list = t.collect();
				processor.process(bean,list);
			}
		});
		allStream.print();

		
		
		
		
		
		
		

		/*parsedStream.foreachRDD(new VoidFunction<JavaRDD<Serializable>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(JavaRDD<Serializable> t) throws Exception {
				Class<?> targetClass = bean.getTableClass();
				DataFrame schemaPeople = sqlContext.createDataFrame(t, targetClass);
				schemaPeople.registerTempTable(targetClass.getSimpleName());

				DataFrame dataFrame = sqlContext.sql(bean.getSql());
				final String[] columns = dataFrame.columns();
				JavaRDD<Row> rdd = dataFrame.toJavaRDD();
				List<Serializable> resultList = rdd.map(new Function<Row, Serializable>() {
					private static final long serialVersionUID = 1L;
					@Override
					public Serializable call(Row row) {
						Serializable obj = manager.row2Obj(columns, row, bean.getResultClass());
						producer.send(new ProducerRecord("user-behavior-topic-result",obj.toString().getBytes()));
						return obj;
					}
				}).collect();
				for (Object name : resultList) {
					System.out.println("########## "+name);
				}
				StatResult result = new StatResult();
				result.setName(bean.getName());
				result.setResultList(resultList);
				
				StreamPostProcessor processor = new DefaultStreamPostProcessor();
				processor.process(result);
				
			}
		});*/

		
		jssc.start();
		jssc.awaitTermination();
	}
	
	public static <T extends Serializable> T row2Obj(String[] columns,Row row, Date now, Class<T> clazz) {
		T obj = null;
		try {
			obj = clazz.newInstance();
			for(int i=0;i<columns.length;i++){
				org.apache.commons.beanutils.BeanUtils.setProperty(obj, columns[i], row.get(row.fieldIndex(columns[i])));
			}
			if(obj instanceof CommonResult){
				CommonResult re = (CommonResult)obj;
				re.setNow(now);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return obj;
	}
}
