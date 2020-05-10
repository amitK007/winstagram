package com.winstagram.securityservice.model;

public class WinstaUser {
    private String userName;
    private Long id;
    private String role;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
