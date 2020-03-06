package com.messiyang.flinkdemo.demo;

import com.alibaba.fastjson.JSONObject;
import com.messiyang.flinkdemo.model.OggBean;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 实时获取kafka数据并且回写到redis
 * 配套使用KafkaProducerDemo类
 */
public class KafkaFlinkRedisDemo {

    public static void main(String[] args) throws Exception {
        //加载配置文件
        Properties pop = new Properties();
        pop.load(new FileInputStream(new File("/Users/messi/Desktop/kafka.properties")));


        StreamExecutionEnvironment exEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        exEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        exEnv.enableCheckpointing(2000);
        // 获取表操作环境对象
        StreamTableEnvironment taEnv = StreamTableEnvironment.create(exEnv);

        Properties kafkaProps = new Properties();
        //kafka的一些属性
        //kafka的节点的IP或者hostName，多个使用逗号分隔
        kafkaProps.setProperty("bootstrap.servers", pop.getProperty("bootstrap.servers"));
        //flink consumer flink的消费者的group.id
        kafkaProps.setProperty("group.id", pop.getProperty("group.id"));
        String  topic = pop.getProperty("topic");
        String  result_topic = pop.getProperty("result_topic");
        //topic是kafka中开启的topic

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), kafkaProps);
        consumer.setStartFromEarliest();
        DataStream<String> stream = exEnv.addSource(consumer);
        DataStream<Tuple2<String, String>> mapStream = stream.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String value) throws Exception {
                OggBean oggBean = JSONObject.parseObject(value, OggBean.class);
                String tableName = oggBean.getTable();
                return new Tuple2<>(tableName, value);
            }
        });
        consumer.setCommitOffsetsOnCheckpoints(true);

        FlinkJedisPoolConfig config =  new FlinkJedisPoolConfig.Builder().setDatabase(3).setHost("127.0.0.1").setPort(6379).build();
        RedisSink<Tuple2<String, String>> redisSink = new RedisSink<Tuple2<String, String>>(config,new MyRedisMapper());
        mapStream.addSink(redisSink);
        exEnv.execute("start");


    }

    public static class MyRedisMapper implements RedisMapper<Tuple2<String, String>> {

        //List集合中存放数据
        @Override
        public RedisCommandDescription getCommandDescription() {
            return new RedisCommandDescription(RedisCommand.SADD);
        }

        //从接收的数据中获取key
        @Override
        public String getKeyFromData(Tuple2<String, String> data) {
            return data.f0;
        }

        //从接收的数据中获取value
        @Override
        public String getValueFromData(Tuple2<String, String> data) {
            return data.f1;
        }
    }


}
