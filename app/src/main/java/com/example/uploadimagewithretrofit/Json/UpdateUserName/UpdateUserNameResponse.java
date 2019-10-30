package com.example.uploadimagewithretrofit.Json.UpdateUserName;

public class UpdateUserNameResponse {

    String status;
    UpdateUserNameData data;

    public UpdateUserNameResponse(String status, UpdateUserNameData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateUserNameData getData() {
        return data;
    }

    public void setData(UpdateUserNameData data) {
        this.data = data;
    }
}
