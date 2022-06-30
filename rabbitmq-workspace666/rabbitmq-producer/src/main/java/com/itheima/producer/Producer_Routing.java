package com.itheima.producer;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 日志：
 * //5.创建交换机换位direct
 *
 *         //6.创建队列
 *
 *         //7.绑定队列和交换机
 *
 *         //8.发送消息
 *
 *         //9.释放资源
 * */

/*
* 发送消息
* */
public class Producer_Routing {

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


        /**
         * String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments
         * 1.exchange：交换机名称
         * 2.type:交换机类型：
         *      DIRECT("direct"), :定向
         *     FANOUT("fanout"), ：扇形，其实就是广播，发送消息到每一个与之绑定的队列
         *     TOPIC("topic"),：通配符
         *     HEADERS("headers"); ：参数匹配
         * 3.durable：是否持久化
         * 4.autoDelete：是否自动删除
         * 5.internal：内部使用，一般设置为false
         * 6.arguments：参数列表
         *
         * */

        //5.创建交换机
        String exchangeName ="test_direct";

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);


        //6.创建队列
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";

        channel.queueDeclare(queue1Name,true,false,false,null);
        channel.queueDeclare(queue2Name,true,false,false,null);


        //7.绑定队列和交换机
        /**
         * String queue, String exchange, String routingKey
         *  参数：
         *  1.queue:队列名称
         *  2.exchange：交换机名称、
         *  3.路由键，绑定规则。
         *      如果交换机的类型为fanout，rootKey设置为""，空字符串，交换机将消息分发给每一个绑定的queue
         *
         * */
        //队列1的绑定，error
        channel.queueBind(queue1Name,exchangeName,"error");
        //队列2 的绑定，info,error,warning
        channel.queueBind(queue2Name,exchangeName,"info");
        channel.queueBind(queue2Name,exchangeName,"error");
        channel.queueBind(queue2Name,exchangeName,"warning");

        //8.发送消息
        //String body ="日志信息：张三调用了findAll()方法..日志级别：info";
        String body ="日志信息：张三调用了delete()方法..出错误了。。。日志级别：error";
        //channel.basicPublish(exchangeName,"info",null,body.getBytes());
        channel.basicPublish(exchangeName,"error",null,body.getBytes());

        //9.释放资源
        channel.close();
        connection.close();


    }
}
