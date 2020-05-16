package com.local.redis.jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

public class RedisDelayingQueue<T> {

    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    private Type TaskType = new TypeReference<T>() {}.getType();

    private Jedis jedis;
    private String queueKey;

    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<>();
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        String s = JSON.toJSONString(task);
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, s);
    }

    public void loop() {
        while(!Thread.interrupted()) {
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0 , 1);
            if(values.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String s = values.iterator().next();
            if(jedis.zrem(queueKey, s) > 0) {
                TaskItem<T> task = JSON.parseObject(s, TaskType);
                this.handleMsg(task.msg);
            }
        }
    }

    private void handleMsg(T msg) {
        System.out.println(msg);
    }




}
