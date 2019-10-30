package com.example.uploadimagewithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uploadimagewithretrofit.Json.GetUserDetail.GetUserDetailData;
import com.example.uploadimagewithretrofit.Json.GetUserDetail.GetUserDetailResponse;
import com.example.uploadimagewithretrofit.Json.UpdateUserName.UpdateUserNameResponse;

import org.reactivestreams.Subscription;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    TextView textView;
    EditText updateNameEditText;
    Button updateBtn;
    public String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textView = (TextView) findViewById(R.id.textView);
        updateNameEditText = (EditText) findViewById(R.id.updateNameEditText);
        updateBtn = (Button) findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserName();
            }
        });
           getUserDetail();

    }


    @Override
    protected void onResume() {
        super.onResume();

//        getUserDetail();

        // textView.setText(name);
    }



    private void updateUserName() {


        final String Uname = updateNameEditText.getText().toString();

        if (Uname.isEmpty()) {

            updateNameEditText.setError("Enter Name");
            updateNameEditText.requestFocus();
            return;
        }

        LtgApi ltgApi = BaseClient.getBaseClient().create(LtgApi.class);
        Call<UpdateUserNameResponse> call = ltgApi.updateUserName("8755420120", Uname);
        call.enqueue(new Callback<UpdateUserNameResponse>() {
            @Override
            public void onResponse(Call<UpdateUserNameResponse> call, Response<UpdateUserNameResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    UpdateUserNameResponse nameResponse = response.body();
                    String n = nameResponse.getData().getMobile();


                    Toast.makeText(UserDetailsActivity.this, n, Toast.LENGTH_SHORT).show();
                    Toast.makeText(UserDetailsActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();

                    //To update the value in textview from api
                    //every time name is updated this method will be called and below textView will be updated
                    getUserDetail();

                } else {

                    Toast.makeText(UserDetailsActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserNameResponse> call, Throwable t) {

                Toast.makeText(UserDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UserDetailsActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void getUserDetail() {

        LtgApi ltgApi = BaseClient.getBaseClient().create(LtgApi.class);

        Call<GetUserDetailResponse> call = ltgApi.getUserDetail("8755420120");

        call.enqueue(new Callback<GetUserDetailResponse>() {
            @Override
            public void onResponse(Call<GetUserDetailResponse> call, Response<GetUserDetailResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    GetUserDetailResponse userDetailResponse = response.body();

                    List<GetUserDetailData> dataResponse = userDetailResponse.getData();

                    for (GetUserDetailData data : dataResponse){

                        name = data.getName();
                        textView.setText(name);
                    }

                    Toast.makeText(UserDetailsActivity.this, name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(UserDetailsActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(UserDetailsActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserDetailResponse> call, Throwable t) {

                Toast.makeText(UserDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UserDetailsActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });




    }









    //
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Handler handler = new Handler();
//
//        final Runnable r = new Runnable() {
//            public void run() {
//                //Initiate your API here
//
//                handler.postDelayed(this, 5000);
//                getUserDetail();
//
//            }
//        };
//
//        handler.postDelayed(r, 5000);
//
//    }

}