package controllers;

import models.Request;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Created by chrisj on 7/28/17.
 */
public class VacationRequestController extends Controller {
    public static Result index() {
        return ok(views.html.requests.index.render(Request.find.all()));
    }
}
