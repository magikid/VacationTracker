package models;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

import com.avaje.ebean.Model;
import com.feth.play.module.pa.user.AuthUser;

/**
 * Created by chrisj on 8/5/17.
 */
@Entity
public class LinkedAccount extends Model {
    @Id
    public Long id;

    @ManyToOne
    public User user;

    public String providerUserId;
    public String providerKey;

    public static final Model.Finder<Long, LinkedAccount> find = new Model.Finder<>(LinkedAccount.class);

    public static LinkedAccount findByProviderKey(final User user, String key) {
        return find.where().eq("user", user).eq("providerKey", key)
                .findUnique();
    }

    public static LinkedAccount create(final AuthUser authUser) {
        final LinkedAccount ret = new LinkedAccount();
        ret.update(authUser);
        return ret;
    }

    public void update(final AuthUser authUser) {
        this.providerKey = authUser.getProvider();
        this.providerUserId = authUser.getId();
    }

    public static LinkedAccount create(final LinkedAccount acc) {
        final LinkedAccount ret = new LinkedAccount();
        ret.providerKey = acc.providerKey;
        ret.providerUserId = acc.providerUserId;

        return ret;
    }
}
