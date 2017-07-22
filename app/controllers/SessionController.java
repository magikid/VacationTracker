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
        if(session("email") != null){
            return redirect(routes.Application.ping());
        }
        Form<UserForm> userForm = Form.form(UserForm.class);
        return ok(login.render(userForm));
    }

    public static Result create(){
        Form<UserForm> userForm = Form.form(UserForm.class).bindFromRequest();

        if(userForm.hasErrors()){
            return badRequest(login.render(userForm));
        }else{
            User u = User.find.where()
                    .eq("email", userForm.field("email").value())
                    .findUnique();
            u.lastSignIn = new Date();
            u.save();
            session().clear();
            session("email", u.email);

            response().setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response().setHeader("Pragma", "no-cache");
            flash("success", Messages.get("login.correct"));
            return redirect(routes.Application.index());
        }
    }

    public static Result destroy(){
        return ok("destroy session");
    }
}
