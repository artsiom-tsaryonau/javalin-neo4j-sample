package com.epam.project.module;

import com.google.inject.AbstractModule;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jOGM extends AbstractModule {
    @Override
    protected void configure() {
        var configuration = new Configuration.Builder()
                .uri("bolt://localhost:7687")
                .credentials("neo4j", "secret")
                .build();
        var sessionFactory = new SessionFactory(configuration,
            "com.epam.project.domain");
        bind(SessionFactory.class).toInstance(sessionFactory);
    }
}
