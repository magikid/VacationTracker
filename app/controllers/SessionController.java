package controllers;

import models.User;
import play.mvc.*;
import play.data.*;

import forms.UserForm;
import views.html.login;
import play.i18n.Messages;
import java.util.Date;

public class SessionController extends Controller {
    public static Result newSession(){
        return ok(login.render());
    }
}
