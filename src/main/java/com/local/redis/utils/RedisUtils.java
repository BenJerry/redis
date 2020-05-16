package com.local.redis.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {

    private RedisUtils() {}

    public static Jedis startJedis() {
        Jedis jedis = new Jedis("xxxxxxx", 6379);
        jedis.auth("xxxx");
        jedis.connect();
        return jedis;
    }

    public static void closeJedis(Jedis jedis) {
        jedis.disconnect();
        jedis.close();
    }

}
