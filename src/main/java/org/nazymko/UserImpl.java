package org.nazymko;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class UserImpl implements User {
    private transient String user;
    private transient String password;

    public UserImpl(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String user() {
        return user;
    }

    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user1 = (UserImpl) o;

        if (user != null ? !user.equals(user1.user) : user1.user != null) return false;
        return password != null ? password.equals(user1.password) : user1.password == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
