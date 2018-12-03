package com.anjoy.frozen.seller.config;

import com.anjoy.frozen.seller.rocketmq.RocketmqEvent;
import com.anjoy.frozen.seller.rocketmq.TransactionCheckListenerImpl;
import org.apache.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/*
 * rocketMQ集成配置
 * wuhy 20180618
 * 主要的作用是用于配置并启动2个生产者和一个消费者
 * rocketmq核心引用在api层内
 *
 *
 * */

@Configuration
public class RocketmqConfig {


    /********* 以下mq的配置详解请参照配置文件或者官方文档 ************/
    @Value("${rocketmq.enableProducer}")
    private boolean isEnableProducer;

    @Value("${rocketmq.enableConsumer}")
    private boolean isEnableConsumer;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producerGroupName}")
    private String producerGroupName;

    @Value("${rocketmq.transactionProducerGroupName}")
    private String transactionProducerGroupName;

    @Value("${rocketmq.consumerGroupName}")
    private String consumerGroupName;

    @Value("${rocketmq.producerInstanceName}")
    private String producerInstanceName;

    @Value("${rocketmq.consumerInstanceName}")
    private String consumerInstanceName;

    @Value("${rocketmq.producerTranInstanceName}")
    private String producerTranInstanceName;

    @Value("${rocketmq.consumerBatchMaxSize}")
    private int consumerBatchMaxSize;

    @Value("${rocketmq.consumerBroadcasting}")
    private boolean isConsumerBroadcasting;

    @Value("${rocketmq.enableHisConsumer}")
    private boolean isEnableHisConsumer;

    @Value("${rocketmq.enableOrderConsumer}")
    private boolean isEnableOrderConsumer;

    @Value("#{'${rocketmq.subscribe}'.split(',')}")
    private List subscribe;

    private static Logger log = LoggerFactory.getLogger(RocketmqConfig.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    private static boolean isFirstSub = true;

    private static long startTime = System.currentTimeMillis();

    /**
     * 初始化向rocketmq发送普通消息的生产者
     */
    @Bean
    public DefaultMQProducer defaultProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        if (!isEnableProducer) return null;

        DefaultMQProducer producer = new DefaultMQProducer(producerGroupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(producerInstanceName);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        log.info("RocketMq defaultProducer Started.");
        return producer;
    }

    /**
     * 初始化向rocketmq发送事务消息的生产者
     */
    @Bean
    public TransactionMQProducer transactionProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        if (!isEnableProducer) return null;

        TransactionMQProducer producer = new TransactionMQProducer(transactionProducerGroupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(producerTranInstanceName);
        producer.setRetryTimesWhenSendAsyncFailed(10);

        // 事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        // 事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        // 队列数
        producer.setCheckRequestHoldMax(2000);

        // wuhy 开源版本的rocketmq服务器阉割调了消息回查的功能，这个功能简单说就是在分布式事务中，rocketmq会定时回查那些unknown的事务消息，通过下面的接口进行回调询问执行结果，这里随便写一个就好，自己搭建的mq根本不会回调这个方法
        TransactionCheckListener transactionCheckListener = new
                TransactionCheckListenerImpl();
        producer.setTransactionCheckListener(transactionCheckListener);

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();

        log.info("RocketMq TransactionMQProducer Started.");
        return producer;
    }

    /**
     * 初始化rocketmq消息监听方式的消费者
     */
    @Bean
    public DefaultMQPushConsumer pushConsumer() throws MQClientException {
        if (!isEnableConsumer) return null;

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setInstanceName(consumerInstanceName);
        if (isConsumerBroadcasting) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }
        consumer.setConsumeMessageBatchMaxSize(
                consumerBatchMaxSize == 0 ? 1 : consumerBatchMaxSize);// 设置批量消费，以提升消费吞吐量，默认是1
        /**
         * 订阅指定topic下tags
         */
        List<String> subscribeList = subscribe;
        for (String sunscribe : subscribeList) {
            consumer.subscribe(sunscribe.split(":")[0], sunscribe.split(":")[1]);
        }

        /*
         *
         * wuhy 20181107
         * 注意，现在的rocketMQ4.2之后的集群消费策略改变了，原来是类似一个consumerGroup之内，不同的consumerInstance采用
         * 顺序分发消费的方式，一个消费失败之后才会发送给第二个，但是现在的策略是一个group内不管多少个instance都是同时收到的，
         * 这样在分布式系统中根本没办法做等幂处理，
         * 增加下面的设置可以将上述策略重新设置为集群内顺序消费的方式，但是要注意
         * 设置之后要保证consumerGroup内，每一个consumerinstance的instanceName都不一样，不然会处理出错
         * */
        AllocateMessageQueueStrategy aqs = new AllocateMessageQueueAveragelyByCircle();
        consumer.setAllocateMessageQueueStrategy(aqs);


        if (isEnableOrderConsumer) {
            //顺序消费监听
            consumer.registerMessageListener((List<MessageExt> msgs, ConsumeOrderlyContext context) -> {
                try {
                    context.setAutoCommit(true);
                    log.info("========== MQ收到新序列消息到达："+msgs.size()+"条 ===========");
                    msgs =filter(msgs);
                    if(msgs.size()==0) return ConsumeOrderlyStatus.SUCCESS;
                    //发布事件，分发处理
                    this.publisher.publishEvent(new RocketmqEvent(msgs, consumer));
                    log.info("其中"+msgs.size()+"条需要处理，列表如下：");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");
                    for (MessageExt me: msgs){
                        log.info("======================================");
                        log.info("TimeBorn:"+dateFormat.format(cal.getTime()));
                        log.info("Host:"+me.getBornHost().toString());
                        log.info("Topic:"+me.getTopic());
                        log.info("Tag:"+me.getTags());
                        log.info("keys:"+me.getKeys());
                        log.info("Body:"+new String(me.getBody()));
                        log.info("======================================");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                // 如果没有return success，consumer会重复消费此信息，直到success。
                return ConsumeOrderlyStatus.SUCCESS;
            });
        } else {
            //同步消费监听
            consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                try {
                    log.info("========== MQ收到新并发消息到达："+msgs.size()+"条 ===========");
                    msgs=filter(msgs);
                    if(msgs.size()==0) return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    this.publisher.publishEvent(new RocketmqEvent(msgs, consumer));
                    log.info("其中"+msgs.size()+"条需要处理，列表如下：");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");
                    for (MessageExt me: msgs){
                        cal.setTimeInMillis(me.getBornTimestamp());
//                        log.info("Topic:"+me.getTopic()+",Tag:"+me.getTags()+",Body:"+new String(me.getBody()));
                        log.info("======================================");
                        log.info("TimeBorn:"+dateFormat.format(cal.getTime()));
                        log.info("Host:"+me.getBornHost().toString());
                        log.info("Topic:"+me.getTopic());
                        log.info("Tag:"+me.getTags());
                        log.info("keys:"+me.getKeys());
                        log.info("Body:"+new String(me.getBody()));
                        log.info("======================================");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                // 如果没有return success，consumer会重复消费此信息，直到success。
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);// 延迟5秒再启动，主要是等待spring事件监听相关程序初始化完成，否则，回出现对RocketMQ的消息进行消费后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失
                    /**
                     * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
                     */
                    try {
                        consumer.start();
                    } catch (Exception e) {
                        log.info("RocketMq pushConsumer Start failure!!!.");
                        log.error(e.getMessage(), e);
                    }
                    log.info("RocketMq pushConsumer Started.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        return consumer;
    }

    private List<MessageExt> filter(List<MessageExt> msgs){
        if(isFirstSub&&!isEnableHisConsumer){
            msgs =msgs.stream().filter(item ->startTime - item.getBornTimestamp() < 0).collect(Collectors.toList());
        }
        if(isFirstSub && msgs.size()>0){
            isFirstSub = false;
        }
        return msgs;
    }


}

