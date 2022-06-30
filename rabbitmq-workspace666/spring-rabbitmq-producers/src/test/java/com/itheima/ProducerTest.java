package com.itheima;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    //1.注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送helloworld消息
     * */
    @Test
    public  void textHelloWorld(){
        //2.发送消息
        //helloworld和workqueues使用如下的两个参数的方法
        rabbitTemplate.convertAndSend("spring_queue","Hello World Spring");

    }


    /**
     * 发送fanout消息
     * */
    @Test
    public  void textFanout(){
        //2.发送消息
        //pubsub必须有交换机，且路由键为""
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","spring fanout............");

    }



    /**
     * 发送topics消息
     * */
    @Test
    public  void textTopics(){
        //2.发送消息
        //pubsub必须有交换机，且路由键为""
        rabbitTemplate.convertAndSend("spring_topic_exchange","heima.hehe.haha","spring topic............");

    }




}
