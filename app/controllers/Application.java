package controllers;

import play.*;
import play.mvc.*;
import java.util.Date;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result ping(){ return ok("Pong " + (new Date())); }

    public static Result oAuthDenied(final String providerKey) {
        flash("error",
                "You need to accept the OAuth connection in order to use this website!");
        return redirect(routes.Application.index());
    }
}
