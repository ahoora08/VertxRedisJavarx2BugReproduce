import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.redis.client.Redis;
import io.vertx.reactivex.redis.client.RedisAPI;
import io.vertx.reactivex.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;

import java.util.Collections;

public class Main extends AbstractVerticle {

    private RedisAPI redis;
    RedisOptions redisOpt = new RedisOptions()
            .setConnectionString("redis://localhost:6379");

    @Override
    public Completable rxStart() {
        return Redis
                .createClient(vertx, redisOpt)
                .rxConnect()
                .flatMapMaybe(client -> {
                    System.out.println("Connected");
                    redis = RedisAPI.api(client);
                    return redis.rxInfo(Collections.emptyList());
                })
                .doOnSuccess(System.out::println)
                .ignoreElement();
    }

    public static void main(String[] args) {
        Vertx.vertx().rxDeployVerticle(new Main()).subscribe();
    }
}
