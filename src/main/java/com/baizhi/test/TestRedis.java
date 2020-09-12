package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.entity.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @author fancz
 * @Title: ${file_name}
 * @date 2020/8/11 19:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class TestRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test1(){
//        redisTemplate.opsForValue();字符串类型操作
//        redisTemplate.opsForHash();
//        redisTemplate.opsForList();
//        redisTemplate.opsForSet();
//        redisTemplate.opsForZSet();
        redisTemplate.opsForValue().set("pwd","password");
        System.out.println(redisTemplate.opsForValue().get("pwd"));
        stringRedisTemplate.opsForValue().set("name2","zhangsi");
        String name2 = stringRedisTemplate.opsForValue().get("name2");
        System.out.println(name2);
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setPassword("1234567");
        redisTemplate.opsForValue().set("user",user);
    }
    @Test
    public void test2(){
        //解决转义字符展示
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //设置value的序列化为json
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);

        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setPassword("1234567");
        redisTemplate.opsForValue().set("user",user);
    }
    /**
     * 创建索引(自动生成文档id)
     * @throws IOException
     */
    @Test
    public void testCreate(){
        TransportClient transportClient = null;
        XContentBuilder xContentBuilder =null;
        try {
            transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.119"), 9300));
            xContentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("name","中国人")
                    .field("age",23)
                    .field("sex","男")
                    .field("content","他是一个中国人,这个中国人怎么样,挺好的").endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        IndexResponse indexResponse = transportClient.prepareIndex("dangdang", "book").setSource(xContentBuilder).get();
        System.out.println(indexResponse.status());
    }

    /**
     * 创建索引(指定生成文档id)
     *
     * @throws IOException
     */
    @Test
    public void testCreate2(){
        TransportClient transportClient = null;
        XContentBuilder xContentBuilder =null;
        try {
            transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.119"), 9300));
            xContentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("name", "中国人")
                    .field("age", 23)
                    .field("sex", "男")
                    .field("content", "他是一个中国人,这个中国人怎么样,挺好的").endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        IndexResponse indexResponse = transportClient.prepareIndex("dangdang", "book","1").setSource(xContentBuilder).get();
        System.out.println(indexResponse.status());
    }

}
