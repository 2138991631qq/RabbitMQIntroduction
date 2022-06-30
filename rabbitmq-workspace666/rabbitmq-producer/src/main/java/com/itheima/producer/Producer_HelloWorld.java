package com.itheima.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 发送消息
* */
public class Producer_HelloWorld {

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
        /**
         * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
         * 参数：queue:队列名称；
         * durable:是否持久化？当mq重启后，还在
         * exclusive:
         *  是否独占？
         *  当connection关闭时，是否删除队列？
         * autoDelete:是否自动删除？没有consumer时，是否自动删除？
         *
         * */

        //如果没有一个叫hello world的队列，会自动创建，有就不创建
        channel.queueDeclare("hello_world",true,false,false,null);

        //6.发送消息给queue
        /**
         * String exchange, String routingKey, BasicProperties props, byte[] body
         * 1.exchange:交换机名称，简单模式下，交换机会使用默认的，空字符串  ""
         * 2.routingKey:路由名称
         * 3.props：配置信息：
         * 4body字节数组，真实的发送的消息数据。.getBytes()
         * */

        String body = "hello rabbitmq~~~";
        channel.basicPublish("","hello_world",null,body.getBytes());

        //7.释放资源：
        /**
         * 不关闭，rabbitmq浏览器端就会有connection和channel
         * */
//        channel.close();
//        connection.close();

    }
}
