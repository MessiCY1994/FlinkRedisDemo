package com.messiyang.flinkdemo.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * @author messiyang
 * @desc
 */
public class KafkaProducerDemo {
    public static void main(String[] args) throws InterruptedException {

        Map<String, Object> props = new HashMap<>();
        props.put("acks", "1");
        props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("bootstrap.servers", "localhost:9092");
        String topic = "zt_test_01";

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);


        for(int i = 0 ;i<5;i++) {
            String line = "{\"table\":\"test"+i+"\",\"op_type\":\"I\",\"op_ts\":\"2020-01-15 09:18:46.001083\",\"current_ts\":\"2020-01-15T17:18:50.870000\",\"pos\":\"00000010710012859128\",\"primary_keys\":[\"TERMINAL_ID\"]," +
                    "\"after\":{\"TERMINAL_ID\":100000000003477,\"TERMINAL_CLASS\":\"01\",\"TERMINAL_STATUS\":\"03\",\"ORG_NO\":\"374040301\"," +
                    "\"REFRESH_TIME\":\"2020-02-06 17:18:46\"}}\n";
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, line);
            producer.send(record);
            System.out.println("数据输出成功" + line);
            Thread.sleep(10000);
        }
        producer.close();


    }
}
