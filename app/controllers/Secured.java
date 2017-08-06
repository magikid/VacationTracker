package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by chrisj on 8/5/17.
 */
public class Secured extends Security.Authenticator {
    @Override
    public String getUsername(final Http.Context ctx) {
        final AuthUser u = PlayAuthenticate.getUser(ctx.session());

        if (u != null) {
            return u.getId();
        } else {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(final Http.Context ctx) {
        ctx.flash().put(Application.FLASH_MESSAGE_KEY, "Nice try, but you need to log in first!");
        return redirect(routes.Application.index());
    }
}
