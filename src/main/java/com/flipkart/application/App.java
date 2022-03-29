package com.flipkart.application;

import com.flipkart.configuration.SampleConfiguration;
import com.flipkart.dao.UserEntityDao;
import com.flipkart.entity.PaymentEntity;
import com.flipkart.entity.UserEntity;
import com.flipkart.exceptions.LowBalanceExceptionMapper;
import com.flipkart.exceptions.VersionConflictExceptionMapper;
import com.flipkart.exceptions.WrongCredentialsExceptionMapper;
import com.flipkart.resources.SampleResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DataSourceFactory.TransactionIsolation;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Hello world!
 *
 */
public class App extends Application<SampleConfiguration> {
 
    @Override
    public void run(SampleConfiguration c, Environment e) throws Exception {
        UserEntityDao sampleEntityDao = new UserEntityDao(hibernate.getSessionFactory());
        e.jersey().register(new SampleResource(sampleEntityDao));
        e.jersey().register(WrongCredentialsExceptionMapper.class);
        e.jersey().register(LowBalanceExceptionMapper.class);
        e.jersey().register(VersionConflictExceptionMapper.class);
    }
 
    public static void main(String[] args) throws Exception {
        new App().run(new String[] {"server", "properties.yaml"});
    }
    
    private final HibernateBundle<SampleConfiguration> hibernate = new HibernateBundle<SampleConfiguration>(UserEntity.class,PaymentEntity.class) {
        public DataSourceFactory getDataSourceFactory(SampleConfiguration configuration) {
        	configuration.getDataSourceFactory().setDefaultTransactionIsolation(TransactionIsolation.REPEATABLE_READ);
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }
}
