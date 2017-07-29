package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Bool;
import play.data.validation.Constraints;
import play.libs.Crypto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by chrisj on 7/28/17.
 */

@Entity
public class Request extends Model{
    @Id
    public Long id;

    @Constraints.Required
    @Column(unique = false, nullable = false)
    public String startDate;

    @Constraints.Required
    @Column(unique = false, nullable = false)
    public String endDate;

    @Constraints.Required
    @Column(unique = false, nullable = false)
    public String location;

    @Constraints.Required
    @Column(unique = false, nullable = false)
    public String notes;

    @Constraints.Required
    @Column(unique = false, nullable = false)
    public Boolean supervisorApproved;

    public static Finder<Long, models.Request> find = new Finder(
            Long.class, models.Request.class
    );
}
