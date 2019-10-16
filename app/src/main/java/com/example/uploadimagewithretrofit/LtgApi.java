package com.example.uploadimagewithretrofit;

import com.example.uploadimagewithretrofit.Json.UserImageUploadResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LtgApi {

    @Multipart
    @POST("update_user_image.php")
    Call<UserImageUploadResponse> uploadImage(
            @Query("mobile") String mobile,
            @Part MultipartBody.Part image);
}
