package com.pantrycar.system;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.pantrycar.system.configurations.PantryCarConfiguration;
import com.pantrycar.system.core.*;
import com.pantrycar.system.health.ApplicationHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by kunal.agarwal on 27/10/15.
 */
public class PantryCarApplication extends Application<PantryCarConfiguration> {

    public static void main(String[] args) throws Exception {
        new PantryCarApplication().run(args);
    }

    private static Injector injector;

    @Override
    public String getName() {
        return "hello-world1";
    }

    private static final HibernateBundle<PantryCarConfiguration> hibernate = new HibernateBundle<PantryCarConfiguration>(
            BankDetail.class,
            Customer.class,
            FacebookLogin.class,
            GoogleLogin.class,
            Location.class,
            MenuCategory.class,
            MenuItem.class,
            Offer.class,
            Order.class,
            OrderItem.class,
            Password.class,
            Restaurant.class,
            SocialAuth.class,
            Station.class,
            TaxDetail.class,
            Contact.class,
            Designation.class,
            Payment.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(PantryCarConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    private final MigrationsBundle<PantryCarConfiguration> migrationsBundle = new MigrationsBundle<PantryCarConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(PantryCarConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    public static <T> T getInstance(Class<T> c) {
        return injector.getInstance(c);
    }

    @Override
    public void initialize(Bootstrap<PantryCarConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(migrationsBundle);
        GuiceBundle<PantryCarConfiguration> guiceBundle = GuiceBundle.<PantryCarConfiguration>newBuilder()
                .addModule(new PantryCarModule())
                .setConfigClass(PantryCarConfiguration.class)
                .enableAutoConfig(getClass().getPackage().getName())
                .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        injector = guiceBundle.getInjector();
    }

    public static HibernateBundle<PantryCarConfiguration> getHibernateBundle() {
        return hibernate;
    }

    @Override
    public void run(PantryCarConfiguration pantryCarConfiguration, Environment environment) throws Exception {
        final ApplicationHealthCheck healthCheck =
                new ApplicationHealthCheck(pantryCarConfiguration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}
