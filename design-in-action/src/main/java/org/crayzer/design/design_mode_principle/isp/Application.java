package org.crayzer.design.design_mode_principle.isp;

public class Application {
    static ConfigSource configSource = new ZookeeperConfigSource(/*省略参数*/);
    public static final RedisConfig redisConfig = new RedisConfig(configSource);
    public static final KafkaConfig kafkaConfig = new KafkaConfig(configSource);
    public static final MysqlConfig mysqlConfig = new MysqlConfig(configSource);

    public static void main(String[] args) {
        ScheduledUpdater redisConfigUpdater =
                new ScheduledUpdater(redisConfig, 300, 300);
        redisConfigUpdater.run();
        ScheduledUpdater kafkaConfigUpdater =
                new ScheduledUpdater(kafkaConfig, 60, 60);
        redisConfigUpdater.run();

        SimpleHttpServer simpleHttpServer = new SimpleHttpServer("127.0 .0 .1",2389);
        simpleHttpServer.addViewers("/config", redisConfig);
        simpleHttpServer.addViewers("/config", mysqlConfig);
        simpleHttpServer.run();
    }
}
