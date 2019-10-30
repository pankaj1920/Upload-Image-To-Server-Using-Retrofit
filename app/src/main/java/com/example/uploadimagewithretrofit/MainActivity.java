package com.example.uploadimagewithretrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uploadimagewithretrofit.Json.UserImageUploadResponse;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.HttpURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button upladBtn;
    ImageButton ibpick;
    CircleImageView ProfileImage;
    String part_image;
    String photoPath;
    ProgressDialog pd;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        upladBtn = (Button) findViewById(R.id.upladBtn);

        ibpick = (ImageButton) findViewById(R.id.ibpick);


        ProfileImage = (CircleImageView) findViewById(R.id.ProfileImage);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);


        ibpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Permission for to access Gallery
                requestMediaFilePermission();
                //Choose file from gallery
                showFileChooser();


            }
        });

        upladBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUploadImage();
            }
        });


    }

    private void showFileChooser() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                photoPath = cursor.getString(columnIndex);
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                ProfileImage.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                cursor.close();

            }
            // When an Video is picked
            else {
                progressDialog.dismiss();
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }


    }

    // this is for image croping

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                Uri resultUri = result.getUri();
//                ProfileImage.setImageURI(resultUri);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }


    private void setUploadImage() {

        progressDialog.show();

        MultipartBody.Part profilePic = null;
        if (photoPath != null) {

            File file = new File(photoPath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            profilePic = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        }

        LtgApi ltgApi = BaseClient.getBaseClient().create(LtgApi.class);

        Call<UserImageUploadResponse> call = ltgApi.uploadImage("8755420120", profilePic);

        call.enqueue(new Callback<UserImageUploadResponse>() {
            @Override
            public void onResponse(Call<UserImageUploadResponse> call, Response<UserImageUploadResponse> response) {

                UserImageUploadResponse uploadResponse = response.body();
                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Toast.makeText(MainActivity.this, uploadResponse.getData().getMobile(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(MainActivity.this, "Sucessfully Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "UnSucess", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, uploadResponse.getStatus(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserImageUploadResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    //Runtime Permission so that it can AutoVerify The Otp in EnterOtpActivity
    private void requestMediaFilePermission() {
        if (askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1)) {

            //this is for croping image
//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .start(this);

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            displayPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        }
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 1) {
                requestMediaFilePermission();
            }

        }
        // this else statment will run wen user click on dont ask again and deined the permission and still click on allow button
        else {
            Toast.makeText(this, "Enable Message permission from Setting", Toast.LENGTH_SHORT).show();
        }
    }


}

