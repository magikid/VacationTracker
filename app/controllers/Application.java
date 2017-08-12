package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import models.User;
import play.mvc.*;
import java.util.Date;
import java.util.concurrent.Callable;

import views.html.*;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result ping(){ return ok("Pong " + (new Date())); }

    public static Result oAuthDenied(final String providerKey) {
        flash(FLASH_ERROR_KEY,
                "You need to accept the OAuth connection in order to use this website!");
        return redirect(routes.Application.index());
    }

    public static User getLocalUser(final Http.Session session) {
        final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
        return User.findByAuthUserIdentity(currentAuthUser);
    }

    public static Result onlySupervisorsAuthorized(Callable onSuccess){
        return onlyAuthorized(User.EmployeeType.SUPERVISOR, onSuccess, () -> {
            flash(FLASH_ERROR_KEY, "Only supervisors are allowed to visit that page.  Not employees.");
            return redirect("/");
        });
    }

    public static Result onlyEmployeesAuthorized(Callable onSuccess){
        return onlyAuthorized(User.EmployeeType.EMPLOYEE, onSuccess, () -> {
            flash(FLASH_ERROR_KEY, "Only employees are allowed to visit that page.  Not supervisors or HR.");
            return redirect("/");
        });
    }

    public static Result onlyHrAuthorized(Callable onSuccess){
        return onlyAuthorized(User.EmployeeType.HR, onSuccess, () -> {
            flash(FLASH_ERROR_KEY, "Only HR members are allowed to visit that page.  Not supervisors or employees.");
            return redirect("/");
        });
    }

    private static Result onlyAuthorized(User.EmployeeType authorizedType, Callable onSuccess, Callable onFailure){
        User currentUser = Application.getLocalUser(session());
        if(currentUser.getType() != authorizedType){
            try {
                return (Result) onFailure.call();
            } catch (Exception e) {
                e.printStackTrace();
                return internalServerError();
            }
        }else{
            try {
                return (Result) onSuccess.call();
            } catch (Exception e) {
                e.printStackTrace();
                return internalServerError();
            }
        }
    }
}
