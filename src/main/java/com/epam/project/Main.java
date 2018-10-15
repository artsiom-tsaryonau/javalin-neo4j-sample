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

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Neo4jOGM());
        var app = Javalin.create();
        app.routes(() -> {
           path("movies", () -> {
               get(ctx -> {
                    var session = injector.getInstance(SessionFactory.class).openSession();
                    ctx.json(session.loadAll(Movie.class));
               });

               get(":id", ctx -> {
                    var session = injector.getInstance(SessionFactory.class).openSession();
                    long value = Long.valueOf(ctx.pathParam("id"));
                    ctx.json(session.load(Movie.class, value));
               });
           });
        });
        app.start(8888);
    }
}
