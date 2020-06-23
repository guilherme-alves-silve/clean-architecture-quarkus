package br.com.guilhermealvessilve.academic.infrastructure.db;

import br.com.guilhermealvessilve.academic.infrastructure.util.Profile;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class FlywayMigration {

    private final String url;

    private final String user;

    private final String password;

    public FlywayMigration(
            @ConfigProperty(name = "flyway_migration.url") String url,
            @ConfigProperty(name = "flyway_migration.username") String user,
            @ConfigProperty(name = "flyway_migration.password") String password
    ) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void onStartup(@Observes StartupEvent event) {

        if (Profile.isTestProfile()) {
            return;
        }

        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .load();
        flyway.migrate();
    }
}
