package controllers;

import models.Request;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Objects;

/**
 * Created by chrisj on 7/28/17.
 */
@Security.Authenticated(Secured.class)
public class VacationRequestController extends Controller {
    public static Result index() {
        User currentUser = Application.getLocalUser(session());
        return ok(views.html.requests.index.render(Request.find.all(), currentUser));
    }

    public static Result newRequest(){
        return Application.onlyEmployeesAuthorized(() -> ok(views.html.requests.new_request.render(Form.form(Request.class))));
    }

    public static Result create(){
        return Application.onlyEmployeesAuthorized(() -> {
            Form<Request> vacationRequestForm = Form.form(Request.class).bindFromRequest();

            if(vacationRequestForm.hasErrors()){
                return badRequest(views.html.requests.new_request.render(vacationRequestForm));
            }else{
                Request vacationRequest = vacationRequestForm.get();
                vacationRequest.setSupervisorApproved(Request.Status.PENDING);
                vacationRequest.save();
                return redirect(routes.VacationRequestController.show(vacationRequest.getId()));
            }
        });
    }

    public static Result show(Long id){
        Request vacation = Request.find.byId(id);
        return ok(views.html.requests.show.render(vacation));
    }

    public static Result destroy(Long id){
        return Application.onlyEmployeesAuthorized(() -> {
            // User is an employee
            Request vacation = Request.find.byId(id);
            if (!vacation.supervisorApproved()) {
                vacation.delete();
                return redirect(routes.VacationRequestController.index());
            } else {
                return badRequest("You're not allowed to delete a request your supervisor has already approved.");
            }
        });
    }

    public static Result edit(Long id){
        return Application.onlySupervisorsAuthorized(() -> {
            Request vacation = Request.find.byId(id);
            Form<Request> vacationRequestForm = Form.form(Request.class).fill(vacation);
            return ok(views.html.requests.edit.render(vacationRequestForm, id));
        });
    }

    public static Result update(Long id, String requestStatus){
        return Application.onlySupervisorsAuthorized(() -> {
            Request.Status status;

            if(requestStatus.equals("approved")){
                status = Request.Status.APPROVED;
            }else if(requestStatus.equals("denied")){
                status = Request.Status.DENIED;
            }else{
                status = Request.Status.PENDING;
            }

            Request vacationRequest = Request.find.byId(id);
            vacationRequest.setSupervisorApproved(status);
            vacationRequest.save();
            return redirect(routes.VacationRequestController.show(vacationRequest.getId()));
        });
    }
}
