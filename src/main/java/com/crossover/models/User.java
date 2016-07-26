package com.crossover.models;

import com.crossover.constant.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This is model class for user
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 */

@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Field(value = "user_name")
    @NotNull
    private String userName;

    @Field(value = "first_name")
    @NotNull
    private String firstName;

    @Field(value = "last_name")
    @NotNull
    private String lastName;

    @NotNull
    private String password;

    private List<Role>roles;

    @Field(value = "university_name")
    private String universityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
