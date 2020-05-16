package com.local.redis.depthHomework.chapt1_2_5.p1;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Set;

public class JdkSerializeTest {

    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis("106.54.45.57", 6379);
        jedis.auth("bsy@123");
        jedis.connect();

        User user = createUser();
        jedis.sadd("user", toJson(user));
        Set<String> jsonSet = jedis.smembers("user");
        for(String s : jsonSet) {
            User user1 = (User) fromJson(s);
            System.out.println(user1.getUsername());
        }

        jedis.hset("users", "user1", toJson(user));
        String s = jedis.hget("users", "user1");
        User user1 = (User) fromJson(s);
        System.out.println(user1.getNick());

        jedis.disconnect();
        jedis.close();
    }

    public static User createUser() {
        User user = new User();
        user.setNick("BenJerry");
        user.setUsername("benjerry@123");
        user.setUsername("bj@123");
        return  user;
    }

    /**
     * 将对象序列化到本地文件
     * @param t
     * @param <T>
     * @throws IOException
     */
    public static<T> void serializeToFile(T t, String filePath) throws IOException {
        try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(filePath)))) {
            oo.writeObject(t);
        }
        System.out.println("序列化");
    }

    /**
     * 从本地文件反序列化成对象
     * @param filePath
     * @param <T>
     * @return
     * @throws IOException
     */
    public static<T> T deserializeFromFile(String filePath) throws IOException {
        T t = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filePath)))) {
            t = (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 使用fastJson将对象序列化成字符串
     * @param t
     * @param <T>
     * @return
     */
    public static<T> String toJson(T t) {
        String text = JSON.toJSONString(t);
        return text;
    }

    /**
     * 使用fastJson将对象反序列化成对象
     * @param s
     * @return
     */
    public static Object fromJson(String s) {
        Object t = JSON.parseObject(s, User.class);
        return t;
    }
}
