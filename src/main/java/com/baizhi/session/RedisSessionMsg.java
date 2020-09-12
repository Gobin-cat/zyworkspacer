package com.baizhi.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author fancz
 * @Title: ${file_name}
 * @date 2020/8/13 19:23
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionMsg {
}
