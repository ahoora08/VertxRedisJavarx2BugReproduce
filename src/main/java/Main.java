import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.redis.client.Redis;
import io.vertx.reactivex.redis.client.RedisAPI;
import io.vertx.reactivex.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;

public class Main extends AbstractVerticle {
    private RedisConnection client;
    @Override
    public void start() {
        RedisOptions redisOpt = new RedisOptions()
                .setConnectionString("redis://localhost:6379");
        Redis.createClient(vertx, redisOpt)
                .connect(onConnect -> {
                    if (onConnect.succeeded()) {
                        client = onConnect.result();
                        System.out.println("Connected");
                    }
                    else onConnect.cause();
                });
        RedisAPI redis = RedisAPI.api(client);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Main());
    }
}
