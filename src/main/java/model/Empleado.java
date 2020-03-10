/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author arnauu
 */
public class Empleado {
    private String userName;
    private String password;
    private String fullName;
    private String phone;

    public Empleado(String userName, String password, String fullName, String phone) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Empleado() {
       
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

}
