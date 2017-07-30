package forms;

import java.util.*;
import play.data.validation.*;
import models.*;

public class UserForm {
    @Constraints.Required(message="validation.required")
    @Constraints.Email(message="validation.email")
    protected String email;

    @Constraints.Required(message = "validation.required")
    protected String password;

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        User u = User.find.where().eq("email", email).findUnique();

        if (u == null) {
            errors.add(new ValidationError("password", "logIn.incorrect"));
        } else {
            if(!password.equals(u.getDecryptedPassword())){
                errors.add(new ValidationError("password", "logIn.incorrect"));
            }
        }

        return errors.isEmpty() ? null : errors;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
