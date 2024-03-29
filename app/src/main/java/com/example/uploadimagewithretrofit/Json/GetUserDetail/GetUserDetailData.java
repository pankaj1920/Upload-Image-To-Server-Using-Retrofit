package com.example.uploadimagewithretrofit.Json.GetUserDetail;

public class GetUserDetailData {

    String id;
    String name;
    String mobile;
    String image;
    String email;
    String password;
    String address;

    public GetUserDetailData(String id, String name, String mobile, String image, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.image = image;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
