package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chrisj on 7/28/17.
 */

@Entity
public class Request extends Model{
    @SuppressWarnings("unused")
    @Id
    public Long id;

    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-mm-dd")
    @Column(nullable = false)
    public Date startDate;

    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-mm-dd")
    @Column(nullable = false)
    public Date endDate;

    @Constraints.Required
    @Column(nullable = false)
    public String location;

    @Column(nullable = false)
    public String notes;

    @Column(nullable = false)
    public Status supervisorApproved;

    public String validate() {
        if(!startBeforeEndDate()){
            return "The end date must be after the start date";
        }
        return null;
    }

    private boolean startBeforeEndDate(){
        return this.startDate.before(this.endDate);
    }

    public static Finder<Long, models.Request> find = new Finder<>(models.Request.class);

    public String inNaturalLanguage(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.startDate) + " until " + formatter.format(this.endDate) + " in " + this.location;
    }

    public Boolean supervisorApproved() {
        return this.getSupervisorApproved() == Status.APPROVED;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateForForm(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.startDate);
    }

    public String getEndDateForForm(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.endDate);
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

    public Status getSupervisorApproved() {
        return supervisorApproved;
    }

    public void setSupervisorApproved(Status supervisorApproved) {
        this.supervisorApproved = supervisorApproved;
    }

    public Long getId() {
        return id;
    }

    public boolean approved(){
        return this.supervisorApproved == Status.APPROVED;
    }

    public boolean pending(){
        return this.supervisorApproved == Status.PENDING;
    }

    public boolean denied(){
        return this.supervisorApproved == Status.DENIED;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", notes='" + notes + '\'' +
                ", supervisorApproved=" + supervisorApproved +
                '}';
    }

    public enum Status {
        @EnumValue("approved")
        APPROVED,

        @EnumValue("denied")
        DENIED,

        @EnumValue("pending")
        PENDING
    }
}
