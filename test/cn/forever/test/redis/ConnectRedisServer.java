package cn.forever.test.redis;

import redis.clients.jedis.Jedis;

/**
 * 连接到 redis 服务
 * 我们需要确保已经安装了 redis 服务及 Java redis 驱动，且你的机器上能正常使用 Java
 * @author Administrator
 *
 */
public class ConnectRedisServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//连接本地redis服务器，127.0.0.1:6379
		Jedis jedis = new Jedis("119.23.78.237", 6379);
		//权限认证
		jedis.auth("ysyhl9t"); 
		System.out.println("Connection to server sucessfully");
		//查看服务是否运行
		System.out.println("Server is running: "+jedis.ping());
	}

}
