package com.androvaid.guitorio.to_letbd.activity;


import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.ViewPagerLocalAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.meta.Meta;
import com.androvaid.guitorio.to_letbd.model.features.Features;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.regauth.RegAuthResponse;
import com.androvaid.guitorio.to_letbd.model.signin.SignInResponse;
import com.androvaid.guitorio.to_letbd.model.signup.SignUpResponse;
import com.androvaid.guitorio.to_letbd.widget.GetDummyValues;
import com.androvaid.guitorio.to_letbd.widget.MultiSelectSpinner;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.scrat.app.selectorlibrary.ImageSelector;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestActivity extends AppCompatActivity {

    private final Context mContext = this;
    private static final String TAG = "TestActivity";
    ProgressDialog progressDialog;

    public static final String IMAGE_UPLOAD_URL = "https://khulna-food.herokuapp.com/api/v1/auth";
    //public static final String IMAGE_DIRECTORY_NAME = "imageuploadtest";
    public static final String IMAGE_DIRECTORY_NAME = "/assets/user/";

    String token = null;

    @BindView(R.id.buttonPress)
    Button buttonPress;

    @BindView(R.id.profileImageTest)
    CircleImageView circleImageViewTest;

    String picturePath;

    String client;
    String uid;
    String access_token;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        circleImageViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }

    private void selectImage(Context context) {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri fileUri = data.getData();
        if (fileUri != null) {
            uploadFile(fileUri); // uploads the file to the web service
        }

        /*if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        circleImageViewTest.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);
                                circleImageViewTest.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }*/
    }

    private void uploadFile(Uri fileUri) {

        String filePath = getRealPathFromUri(fileUri);
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {

                Log.d(TAG, "uploadFile: file name: " + file.getName());

                progressDialog.show();

                /*
                 * ------------ okHttp to let registration working  -----------------
                 * */

                OkHttpClient okHttpClient = new OkHttpClient();

                MediaType mediaType = MediaType.parse("multipart/form-data");
                RequestBody body = RequestBody.create(mediaType, file);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", "test6@gmail.com")
                        .addFormDataPart("password", "123456")
                        .addFormDataPart("name", "asdf")
                        .addFormDataPart("phone", "321654987")
                        .addFormDataPart("image", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                        .build();

                Request request = new Request.Builder()
                        .url("https://to-let.androvaid.com/api/v1/sign-up")
                        .post(requestBody)
                        .build();

                okhttp3.Call call = okHttpClient.newCall(request);
                call.enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        progressDialog.dismiss();

                        if (response.code() == 200) {
                            Log.d(TAG, "onResponse: okHttp Response: " + response.body().string());
                        }

                    }
                });


                /*
                 * ------------ okHttp to let registration working  ------------------
                 * */


                /*
                 * ------------- Ion Method working food -------------------
                 * */

                /*
                MediaType mediaType = MediaType.parse("multipart/form-data");
                RequestBody body = RequestBody.create(mediaType, file);

                        Ion.with(TestActivity.this)
                        .load("POST",IMAGE_UPLOAD_URL)
                        .uploadProgressHandler(new ProgressCallback() {
                            @Override
                            public void onProgress(long uploaded, long total) {
                            }
                        })
                        .setMultipartParameter("email", "asd@gmail.com")
                        .setMultipartParameter("first_name", "asdfasdf")
                        .setMultipartFile("avatar", "image/jpeg", file)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                progressDialog.dismiss();
                                if (result != null) {
                                    //Upload Success
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onCompleted: REsult: "+result);

                                } else {
                                    //Upload Failed
                                    Toast.makeText(mContext, "Not Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/

                /*
                 * ------------- Ion Method working food-------------------
                 * */


                /*
                 * --------- Multipart  working food -----------
                 * */

                /*RequestBody namePart = RequestBody.create(MultipartBody.FORM, "Wasi");
                RequestBody emailPart = RequestBody.create(MultipartBody.FORM, "wasi@gmail.com");

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                Call<RegAuthResponse> call = RetrofitClient.getInstance().getApi().getRegAuthResponse(emailPart, namePart, body);
                call.enqueue(new Callback<RegAuthResponse>() {
                    @Override
                    public void onResponse(Call<RegAuthResponse> call, Response<RegAuthResponse> response) {
                        progressDialog.dismiss();
                        Log.i(TAG, "Response: "+response.body().getStatus());
                        Log.d(TAG, "onResponse: Headers: uid: "+response.headers().get("Uid")
                                +" Client: "+response.headers().get("Client")
                                +" Expiry: "+response.headers().get("Expiry"));
                    }

                    @Override
                    public void onFailure(Call<RegAuthResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });*/

                /*
                 * ----------- Multipart working food ---------
                 * */

                /*
                 * ---------------- RequestBody working food ----------------------
                 * */

                /*MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("email", "abc@gmail.com");
                builder.addFormDataPart("first_name", "abc");
                final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");
                builder.addFormDataPart("avatar", file.getName(), RequestBody.create(MEDIA_TYPE, file));

                RequestBody requestBody = builder.build();

                Call<RegAuthResponse> call = RetrofitClient.getInstance().getApi().getRegAuthRequestBodyResponse(requestBody);
                call.enqueue(new Callback<RegAuthResponse>() {
                    @Override
                    public void onResponse(Call<RegAuthResponse> call, Response<RegAuthResponse> response) {
                        progressDialog.dismiss();
                        Log.i(TAG, "Response: " + response.code());

                        if (response.code() == 200) {

                            try {
                                Log.d(TAG, "onResponse: json: " + response.body().getStatus());

                                Log.d(TAG, "onResponse: client: "+response.headers().get("Client")+" uid: "+response.headers().get("Uid")+" token: "+response.headers().get("Access-Token"));
                                
                                client = response.headers().get("Client");
                                uid = response.headers().get("Uid");
                                access_token = response.headers().get("Access-Token");


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //Log.d(TAG, "onResponse: Headers: uid: "+response.headers().get("Uid")+" Client: "+response.headers().get("Client")+" Expiry: "+response.headers().get("Expiry"));
                        }else {
                            Toast.makeText(TestActivity.this, "Data Problem", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegAuthResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });*/


                /*
                 * ---------------- RequestBody working  food ----------------------
                 * */


            }
        }
    }


    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(mContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(mContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void CheckAuthentication(View view) {

        Log.d(TAG, "onResponse: client: " + client + " uid: " + uid + " token: " + access_token);

        progressDialog.show();

        Map<String, String> headers = new HashMap<>();
        headers.put("client", client);
        headers.put("uid", uid);
        headers.put("access-token", access_token);

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://khulna-food.herokuapp.com/api/v1/users/check_user")
                .get()
                .addHeader("client", client)
                .addHeader("uid", uid)
                .addHeader("access-token", access_token)
                .build();

        okhttp3.Call response = okHttpClient.newCall(request);
        response.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: Response: " + response.body().string());
            }
        });

        /*Call<ResponseBody> regAuthResponseCall = RetrofitClient.getInstance().getApi().getAuthHeaderResponse(headers);
        //Call<ResponseBody> regAuthResponseCall = RetrofitClient.getInstance().getApi().getAuthHeaderResponse(client,uid,access_token);
        regAuthResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressDialog.dismiss();
                    Log.d(TAG, "onResponse: Authentication: "+response.body());
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: Exception: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });*/

    }
}