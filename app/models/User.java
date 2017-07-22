package models;


// model
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.validation.*;

// added
import play.libs.Crypto; // crypt AES
import java.util.Date;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name="users")
public class User extends Model {
    @Id
    public Long id;

    @Constraints.Required(message="validation.required")
    @Constraints.Email(message="validation.email")
    @Column(unique = true, nullable = false)
    public String email;

    @Constraints.MinLength(value=8, message="validation.minLength")
    @JsonIgnore
    public String password;

    @JsonIgnore
    public Date lastSignIn;

    @JsonIgnore
    public boolean isHrApproved;

    public User(String email, String password){
        this.email = email;
        setDecryptedPassword(password);
    }

    public static Finder<Long, User> find = new Finder(
            Long.class, User.class
    );

    public void setDecryptedPassword(String password){
        this.password = new Crypto(null).encryptAES(password);
    }

    public String getDecryptedPassword(){
        return new Crypto(null).decryptAES(password);
    }
}