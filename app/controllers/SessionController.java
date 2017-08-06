package controllers;

import play.mvc.*;

import views.html.login;

public class SessionController extends Controller {
    public static Result newSession(){
        return ok(login.render());
    }
}
