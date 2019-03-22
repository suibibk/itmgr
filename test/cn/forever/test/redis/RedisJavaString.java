package cn.forever.test.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * Redis Java String(字符串) 实例
 * @author Administrator
 *
 */
public class RedisJavaString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//连接本地的Redis服务
		Jedis jedis=ConnectUtil.connectRedis();
		//设置redis字符串数据
		jedis.set("username", "林文华");
		//获取存储的数据并输出
		System.out.println("Stored string in redis:"+jedis.get("username"));
	}

}
