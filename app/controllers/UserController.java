package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by chrisj on 8/6/17.
 */
public class UserController extends Controller {

    public static Result index() {
        return ok(views.html.users.index.render(User.find.all()));
    }

    public static Result show(long id) {
        User user = User.find.byId(id);
        return ok(views.html.users.show.render(user));
    }

    public static Result edit(long id) {
        User user = User.find.byId(id);
        Form<User> userForm = Form.form(User.class).fill(user);
        return ok(views.html.users.edit.render(userForm, user));
    }

    public static Result update(long id) {
        User user = User.find.byId(id);
        Form<User> userForm = Form.form(User.class).bindFromRequest();

        System.out.println(userForm);

        if(userForm.hasErrors()){
            return badRequest(views.html.users.edit.render(userForm, user));
        }else{
            User userUpdate = userForm.get();
            user.setName(userUpdate.getName());
            user.setEmail(userUpdate.getEmail());
            user.setIsHrApproved(userUpdate.isHrApproved());
            user.setType(userUpdate.getType());
            user.save();
            System.out.println("user: " + user);
            System.out.println("userUpdate: " + userUpdate);

            return redirect(routes.UserController.show(user.id));
        }
    }

    public static Result destroy(long id) {
        User user = User.find.byId(id);
        user.delete();

        return redirect(routes.UserController.index());
    }
}
