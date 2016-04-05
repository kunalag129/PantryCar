package com.pantrycar.system;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

/**
 * Created by kunal.agarwal on 01/11/15.
 */
public class PantryCarModule implements Module {
    @Override
    public void configure(Binder binder) {

    }

    @Provides
    @Singleton
    public SessionFactory getSessionFactory() throws Exception {
        SessionFactory sessionFactory = PantryCarApplication.getHibernateBundle().getSessionFactory();
        ManagedSessionContext.bind(sessionFactory.openSession());
        return sessionFactory;
    }

}
