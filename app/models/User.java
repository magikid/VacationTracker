package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

public class User extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String name;
}