package forms;

import models.Request;
import play.data.validation.Constraints;

import java.util.Date;

public class RequestForm {

    @Constraints.Required
    private Date startDate;

    @Constraints.Required
    private Date endDate;

    @Constraints.Required
    private String location;

    private String notes;
    private Request.Status supervisorApproved;

    public RequestForm(){}

    public String validate() {
        if(!this.startDate.before(this.endDate)){
            return "The last day must be after the start.";
        }
        return null;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Request.Status getSupervisorApproved() {
        return supervisorApproved;
    }

    public void setSupervisorApproved(Request.Status supervisorApproved) {
        this.supervisorApproved = supervisorApproved;
    }
}
