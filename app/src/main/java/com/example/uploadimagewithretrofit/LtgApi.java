package com.example.uploadimagewithretrofit;

import com.example.uploadimagewithretrofit.Json.GetUserDetail.GetUserDetailResponse;
import com.example.uploadimagewithretrofit.Json.UpdateUserName.UpdateUserNameResponse;
import com.example.uploadimagewithretrofit.Json.UserImageUploadResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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


    //Getting UserDetail
    @GET("user_details.php")
    Call<GetUserDetailResponse> getUserDetail(
            @Query("mobile") String mobile
    );

    //Update User Name
    @FormUrlEncoded
    @POST("update_user_name.php")
    Call<UpdateUserNameResponse> updateUserName(
            @Query("mobile") String mobile,
            @Field("name") String name
    );

}