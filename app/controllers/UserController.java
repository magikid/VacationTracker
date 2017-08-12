package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by chrisj on 8/6/17.
 */
@Security.Authenticated(Secured.class)
public class UserController extends Controller {

    public static Result index() {
        return Application.onlyHrAuthorized(() -> ok(views.html.users.index.render(User.find.all())));
    }

    public static Result show(long id) {
        return Application.onlyHrAuthorized(() -> ok(views.html.users.show.render(User.find.byId(id))));
    }

    public static Result edit(long id) {
        return Application.onlyHrAuthorized(() -> {
            User user = User.find.byId(id);
            Form<User> userForm = Form.form(User.class).fill(user);

            return ok(views.html.users.edit.render(userForm, user));
        });
    }

    public static Result update(long id) {
        return Application.onlyHrAuthorized(() -> {
            User user = User.find.byId(id);
            Form<User> userForm = Form.form(User.class).bindFromRequest();

            if(userForm.hasErrors()){
                return badRequest(views.html.users.edit.render(userForm, user));
            }else{
                User userUpdate = userForm.get();
                user.setName(userUpdate.getName());
                user.setEmail(userUpdate.getEmail());
                user.setIsHrApproved(userUpdate.isHrApproved());
                user.setType(userUpdate.getType());
                user.save();

                return redirect(routes.UserController.show(user.id));
            }
        });
    }

    public static Result destroy(long id) {
        return Application.onlyHrAuthorized(() -> {
            User user = User.find.byId(id);
            user.delete();

            return redirect(routes.UserController.index());
        });

        return redirect(routes.UserController.index());
    }
}
