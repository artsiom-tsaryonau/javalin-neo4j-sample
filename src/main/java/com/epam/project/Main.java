package com.epam.project;

import com.epam.project.domain.Movie;
import com.epam.project.module.Neo4jOGM;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import org.neo4j.ogm.session.SessionFactory;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {

    private static final Injector INJECTOR = Guice.createInjector(new Neo4jOGM());

    private static <T> T require(Class<T> clazz) {
        return INJECTOR.getInstance(clazz);
    }

    public static void main(String[] args) {
        var app = Javalin.create().start(8888);
        app.routes(() -> {
           path("movies", () -> {
               get(ctx -> {
                    var session = require(SessionFactory.class).openSession();
                    ctx.json(session.loadAll(Movie.class));
               });

               get(":id", ctx -> {
                    var session = require(SessionFactory.class).openSession();
                    long value = Long.valueOf(ctx.pathParam("id"));
                    ctx.json(session.load(Movie.class, value));
               });
           });
        });
    }
}
