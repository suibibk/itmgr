package cn.forever.test.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * Redis Java List(列表) 实例
 * @author Administrator
 *
 */
public class RedisJavaList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//连接本地的Redis服务
		Jedis jedis=ConnectUtil.connectRedis();
		//存储数据到列表中
		jedis.lpush("school", "白云小学");
		jedis.lpush("school", "天河中学");
		jedis.lpush("school", "海珠高中");
		jedis.lpush("school", "华农大学");
		List<String> lists=jedis.lrange("school", 0, 5);
		for (int i = 0; i < lists.size(); i++) {
			System.out.println("Stored string in redis:"+lists.get(i));
		}
		
	}

}
