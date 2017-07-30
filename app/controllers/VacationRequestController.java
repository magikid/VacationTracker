package controllers;

import forms.RequestForm;
import models.Request;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import play.mvc.Http.*;

/**
 * Created by chrisj on 7/28/17.
 */
public class VacationRequestController extends Controller {
    public static Result index() {
        return ok(views.html.requests.index.render(Request.find.all()));
    }

    public static Result newRequest(){
        return ok(views.html.requests.new_request.render(Form.form(Request.class)));
    }

    public static Result create(){
        Form<Request> vacationRequestForm = Form.form(Request.class).bindFromRequest();
        System.out.println("form: " + vacationRequestForm);
        if(vacationRequestForm.hasErrors()){
            return badRequest(views.html.requests.new_request.render(vacationRequestForm));
        }else{
            Request vacationRequest = vacationRequestForm.get();
            vacationRequest.setSupervisorApproved(Request.Status.PENDING);
            vacationRequest.save();
            return redirect(routes.VacationRequestController.show(vacationRequest.getId()));
        }
    }

    public static Result show(Long id){
        Request vacation = Request.find.byId(id);
        return ok(views.html.requests.show.render(vacation));
    }

    public static Result destroy(Long id){
        Request vacation = Request.find.byId(id);
        if(!vacation.supervisorApproved()){
            vacation.delete();
            return redirect(routes.VacationRequestController.index());
        }else{
            return badRequest("You're not allowed to delete a request your supervisor has already approved.");
        }
    }

    public static Result edit(Long id){
        Request vacation = Request.find.byId(id);
        Form vacationRequestForm = Form.form(Request.class).fill(vacation);
        return ok(views.html.requests.edit.render(vacationRequestForm, id));
    }

    public static Result update(Long id, String requestStatus){
        Request.Status status;

        if(requestStatus == "approved"){
            status = Request.Status.APPROVED;
        }else if(requestStatus == "denied"){
            status = Request.Status.DENIED;
        }else{
            status = Request.Status.PENDING;
        }

        Request vacationRequest = Request.find.byId(id);
        vacationRequest.setSupervisorApproved(status);
        vacationRequest.save();
        return redirect(routes.VacationRequestController.show(vacationRequest.getId()));
    }
}
