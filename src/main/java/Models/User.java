package Models;

import Enums.UserRole;

import java.util.UUID;

public class User {

    private String id;
    private String fullName;
    private String email ;
    private String phone;
    private Boolean isLogged;
    private String password;
    private UserRole role ;

    private static  int counter = 0 ;

    public User(String id , String fullName , String email , String phone , Boolean isLogged,String password,UserRole role){
        this.id = generateID();
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isLogged = isLogged;
        this.password = password;
        this.role = role;
    }


    public String generateID(){
         UUID id = UUID.randomUUID();
         return  id.toString().substring(0 , 8)+ "-" + String.format("%04d" , counter++);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Boolean isLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return "User{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

