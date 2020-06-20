package br.com.guilhermealvessilve.infrastructure.testcontainer;

import io.quarkus.arc.AlternativePriority;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import org.jboss.logging.Logger;
import org.testcontainers.containers.MySQLContainer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class MySQLTestcontainer implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOGGER = Logger.getLogger(MySQLTestcontainer.class);

    private static volatile MySQLContainer<?> container;

    private final Vertx vertx;

    @Inject
    public MySQLTestcontainer(final Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public Map<String, String> start() {

        LOGGER.info("Starting MySQLTestcontainer...");

        container = new MySQLContainer<>()
                .withInitScript("db/migration/init_all_db.sql");
        container.start();
        return Collections.emptyMap();
    }

    @Produces
    @AlternativePriority(value = 1)
    public Pool getTestcontainersPool() {
        final var connectionOptions = new MySQLConnectOptions()
                .setHost(container.getHost())
                .setPort(container.getFirstMappedPort())
                .setUser(container.getUsername())
                .setPassword(container.getPassword())
                .setDatabase(container.getDatabaseName());
        final var poolOptions = new PoolOptions();
        return MySQLPool.pool(vertx, connectionOptions, poolOptions);
    }

    @Override
    public void stop() {
        if (container != null) {
            LOGGER.info("Stopping MySQLTestcontainer...");
            container.close();
        }
    }
}
