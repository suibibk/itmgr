package cn.forever.test.redis;

import redis.clients.jedis.Jedis;

public class TestRedis {

	public static void main(String[] args) {
		Jedis jedis=RedisUtil.getJedis();
		//设置redis字符串数据
		jedis.set("username", "林文华");
		//获取存储的数据并输出
		System.out.println("Stored string in redis:"+jedis.get("username"));
	}

}
