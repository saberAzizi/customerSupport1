package com.saber.site.model;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.Principal;

@Entity
@Table(name = "userPrincipal",
        uniqueConstraints = @UniqueConstraint(name = "userPrincipal_username",columnNames = "username"))
public class UserPrincipal implements Serializable, Principal,Cloneable {
    private static final String SESSION_ATTRIBUTE_KEY = "com.saber.site.principal";
    private long userId;
    private String username;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    @Basic
    @Column(name = "username",length = 35,nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password",length = 35,nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @Transient
    public String getName() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;

        if (userId != that.userId) return false;
        if (!username.equals(that.username)) return false;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    protected UserPrincipal clone() throws CloneNotSupportedException {
        return (UserPrincipal) super.clone();
    }

    public static void setPrincipal(HttpSession session,Principal principal){
        session.setAttribute(SESSION_ATTRIBUTE_KEY,principal);
    }
    public static Principal getPrincipal(HttpSession session){
        if (session==null){
            return null;
        }
        if (session.getAttribute(SESSION_ATTRIBUTE_KEY)==null){
            return null;
        }
        return (Principal) session.getAttribute(SESSION_ATTRIBUTE_KEY);
    }

    @Override
    public String toString() {
        return  this.username ;
    }
}