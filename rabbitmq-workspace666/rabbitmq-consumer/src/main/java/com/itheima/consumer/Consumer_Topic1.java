package com.itheima.consumer;

/**
 * 日志：
 * 仅将pubsub换queueName
 * */

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Topic1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.通过工厂建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //2.设置参数。虚拟机、用户名、密码。。。。
        factory.setHost("192.168.178.131");//默认localhost
        factory.setPort(5672);//端口，默认5672
        factory.setVirtualHost("/itcast");//虚拟机，默认值-虚拟机
        factory.setUsername("root");//默认guest
        factory.setPassword("kdy20010226"); //默认guest

        //3.获取(创建)连接
        Connection connection  = factory.newConnection();

        //4.创建channel
        Channel channel = connection.createChannel();

        //5.创建队列。queue
        String queue1Name = "test_topics_queue1";
        String queue2Name = "test_topics_queue2";


        //接收消息

        /**
         * String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback
         * 1.queue 队列名称；
         * 2. autoAck:自动确认
         * 3. callback:回调函数。
         * */

        Consumer consumer = new DefaultConsumer(channel){

            /**
             * 这是一个回调方法，当收到消息后就会自动执行改方法。
             * 1.consumerTag：标识
             * 2.envelope:获取一些信息，交换机，路由key
             * 3.properties:配置信息
             * 4.body:真实的数据
             * */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               /* System.out.println("consumerTag:"+consumerTag);
                System.out.println("envelopeExchange:"+envelope.getExchange());
                System.out.println("envelopeRoutingKey:"+envelope.getRoutingKey());
                System.out.println("properties:"+properties);*/
                System.out.println("body:"+new String(body));
                System.out.println("将日志信息存到数据库....");

            }
        };
        channel.basicConsume(queue1Name,true,consumer);



        //关闭资源，消费者是一个监听程序，所以消费者不要去关闭资源

    }
}
