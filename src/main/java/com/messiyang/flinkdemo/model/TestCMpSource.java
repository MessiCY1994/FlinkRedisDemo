package com.messiyang.flinkdemo.model;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义source ，每隔2秒生成随机的testCMp数据
 */
class TestCMpSource implements SourceFunction<TestCMp> {

    private ReentrantLock reentrantLock;
    private Boolean flag=true;

    /**
     * 启动一个source，并且从source返回数据
     * 如果run方法结束，则数据流终止
     * @param ctx
     * @throws Exception
     */
    @Override
    public void run(SourceContext ctx) throws Exception {
        reentrantLock.lock();
        Random random = new Random();
        while (flag){
            for(int i=0;i<5;i++){
                TestCMp testCMp = new TestCMp();
                testCMp.setId(String.valueOf(i));
                testCMp.setAge(String.valueOf(i));
                testCMp.setName("test"+i);
                //发送数据到流
                ctx.collect(testCMp);
                Thread.sleep(2000);
            }
        }
    }

    //终止数据流
    @Override
    public void cancel() {
        flag=false;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.setParallelism(1);
        DataStream<TestCMp> streamSource = executionEnvironment.addSource(new TestCMpSource());
        streamSource.print();
        executionEnvironment.execute("自定义source");
    }
}
