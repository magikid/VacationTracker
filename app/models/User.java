package models;


// model
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
import com.feth.play.module.pa.user.AuthUserIdentity;
import play.data.validation.*;
import javax.persistence.OneToMany;
import com.feth.play.module.pa.user.AuthUser;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;


import java.util.*;


@Entity
@Table(name="users")
public class User extends Model {
    @Id
    public Long id;

    @Constraints.Email
    @Column(unique = true, nullable = false)
    public String email;

    public String name;

    @Temporal(TemporalType.DATE)
    public Date lastSignIn;

    public boolean isHrApproved;

    public boolean emailValidated;

    public boolean active;

    public EmployeeType type;

    @OneToMany(cascade = CascadeType.ALL)
    public List<LinkedAccount> linkedAccounts;

    public static final Finder<Long, User> find = new Finder<>(
            Long.class, User.class);

    public static boolean existsByAuthUserIdentity(
            final AuthUserIdentity identity) {
        final ExpressionList<User> exp = getAuthUserFind(identity);
        return exp.findRowCount() > 0;
    }

    private static ExpressionList<User> getAuthUserFind(
            final AuthUserIdentity identity) {

        ExpressionList<User> userList = find.where().eq("active", true)
                .eq("linkedAccounts.providerUserId", identity.getId())
                .eq("linkedAccounts.providerKey", identity.getProvider());
        User user = userList.findUnique();
        user.lastSignIn = new Date();
        user.save();

        return userList;
    }

    public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
        if (identity == null) {
            return null;
        }
        return getAuthUserFind(identity).findUnique();
    }

    public void merge(final User otherUser) {
        for (final LinkedAccount acc : otherUser.linkedAccounts) {
            this.linkedAccounts.add(LinkedAccount.create(acc));
        }
        // do all other merging stuff here - like resources, etc.

        // deactivate the merged user that got added to this one
        otherUser.active = false;
        Ebean.save(Arrays.asList(new User[] { otherUser, this }));
    }

    public static User create(final AuthUser authUser) {
        final User user = new User();
        user.active = true;
        user.type = EmployeeType.EMPLOYEE;
        user.isHrApproved = false;
        user.lastSignIn = new Date();
        user.linkedAccounts = Collections.singletonList(LinkedAccount
                .create(authUser));

        if (authUser instanceof EmailIdentity) {
            final EmailIdentity identity = (EmailIdentity) authUser;
            // Remember, even when getting them from FB & Co., emails should be
            // verified within the application as a security breach there might
            // break your security as well!
            user.email = identity.getEmail();
            user.emailValidated = false;
        }

        if (authUser instanceof NameIdentity) {
            final NameIdentity identity = (NameIdentity) authUser;
            final String name = identity.getName();
            if (name != null) {
                user.name = name;
            }
        }

        user.save();
        return user;
    }

    public static void merge(final AuthUser oldUser, final AuthUser newUser) {
        User.findByAuthUserIdentity(oldUser).merge(
                User.findByAuthUserIdentity(newUser));
    }

    public Set<String> getProviders() {
        final Set<String> providerKeys = new HashSet<String>(
                linkedAccounts.size());
        for (final LinkedAccount acc : linkedAccounts) {
            providerKeys.add(acc.providerKey);
        }
        return providerKeys;
    }

    public static void addLinkedAccount(final AuthUser oldUser,
                                        final AuthUser newUser) {
        final User u = User.findByAuthUserIdentity(oldUser);
        u.linkedAccounts.add(LinkedAccount.create(newUser));
        u.save();
    }

    public static User findByEmail(final String email) {
        return getEmailUserFind(email).findUnique();
    }

    private static ExpressionList<User> getEmailUserFind(final String email) {
        return find.where().eq("active", true).eq("email", email);
    }

    public LinkedAccount getAccountByProvider(final String providerKey) {
        return LinkedAccount.findByProviderKey(this, providerKey);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastSignIn=" + lastSignIn +
                ", isHrApproved=" + isHrApproved +
                ", emailValidated=" + emailValidated +
                ", active=" + active +
                ", type=" + type +
                ", linkedAccounts=" + linkedAccounts +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Date getLastSignIn() {
        return lastSignIn;
    }

    public boolean isHrApproved() {
        return isHrApproved;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastSignIn(Date lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public void setIsHrApproved(boolean hrApproved) {
        this.isHrApproved = hrApproved;
    }

    public boolean isEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public void setType(String type){
        switch (type){
            case "HR":
                this.type = EmployeeType.HR;
                break;
            case "SUPERVISOR":
                this.type = EmployeeType.SUPERVISOR;
                break;
            case "EMPLOYEE":
                this.type = EmployeeType.EMPLOYEE;
                break;
            default:
                this.type = EmployeeType.EMPLOYEE;
        }
    }

    public List<LinkedAccount> getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(List<LinkedAccount> linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
    }

    public static Finder<Long, User> getFind() {
        return find;
    }

    public enum EmployeeType {
        @EnumValue("employee")
        EMPLOYEE,

        @EnumValue("supervisor")
        SUPERVISOR,

        @EnumValue("hr")
        HR
    }
}