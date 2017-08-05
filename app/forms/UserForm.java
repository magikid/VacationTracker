package forms;

import java.util.*;
import play.data.validation.*;
import models.*;

public class UserForm {
    @Constraints.Email
    protected String email;

    protected String password;

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
