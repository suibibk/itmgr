package cn.forever.test.redis;

import redis.clients.jedis.Jedis;

public class ConnectUtil {
	public static Jedis connectRedis(){
		//连接本地redis服务器，127.0.0.1:6379
		Jedis jedis = new Jedis("119.23.78.237", 6379);
		//权限认证
		jedis.auth("ysyhl9t"); 
		System.out.println("Connection to server sucessfully");
		//查看服务是否运行
		System.out.println("Server is running: "+jedis.ping());
		return jedis;
	}
}
