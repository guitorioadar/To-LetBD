package com.androvaid.guitorio.to_letbd.activity;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.ViewPagerLocalAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.model.categories.Categories;
import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.features.Features;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.postcreate.success.PostCreateResponse;
import com.androvaid.guitorio.to_letbd.model.signup.SignUpResponse;
import com.androvaid.guitorio.to_letbd.utils.FileUtils;
import com.androvaid.guitorio.to_letbd.widget.MultiSelectSpinner;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kosalgeek.android.imagebase64encoder.ImageBase64;
import com.scrat.app.selectorlibrary.ImageSelector;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerConst;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androvaid.guitorio.to_letbd.utils.FileUtils.isDownloadsDocument;
import static com.androvaid.guitorio.to_letbd.utils.FileUtils.isExternalStorageDocument;
import static com.androvaid.guitorio.to_letbd.utils.FileUtils.isGooglePhotosUri;
import static com.androvaid.guitorio.to_letbd.utils.FileUtils.isMediaDocument;

public class PostCreateActivity extends AppCompatActivity {

    private static final String TAG = "PostCreateActivity";

    private String Accept = "application/json";
    private String Authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjgzYWVmNDY5NjUxMTA2YWQzZDUxNTVmNjBhNjU4YjM0OWI2MWFjYjk0ZDRlNjhjOGQzZGQ4ZGY0ZTk0MzBhNjRjOWU4YjI0MTU4YTk5ZTMyIn0.eyJhdWQiOiIxIiwianRpIjoiODNhZWY0Njk2NTExMDZhZDNkNTE1NWY2MGE2NThiMzQ5YjYxYWNiOTRkNGU2OGM4ZDNkZDhkZjRlOTQzMGE2NGM5ZThiMjQxNThhOTllMzIiLCJpYXQiOjE1MzY1MTMwODIsIm5iZiI6MTUzNjUxMzA4MiwiZXhwIjoxNTY4MDQ5MDgyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.rOrPiMXXhmpseGp51dflx2Mfn2ytz6Fhlhuc9ZM8teIoBr5gpLb-6guHPazgsrx4TJ1AoIaGij1OFSySJpwE8S8NsvkwdpR_CBgw86CZskbmQrAFRvtNPo7NArSEDhMWA7MVwVbK9WlNLald3hXiENIvIxJq3TmlBsZLdmUpzcOcL1PHcgK31OKWLIVCfMXJe6SATeXiNHV5jwyzNcEaxGjPOVIsxeM0iz0CRmiJ7VxBHtnCfq6i86z6zwzolKHLHcdvc_6Yec5J6t1qN8n4-AZP57ImaCUDvXm8EBTKq1fL6UAm6B78MtBhTpu0dzWwdHG9uaDcRBdDUhM3UzFoC01Zbv7R6wbNkekfTghGP2Obh5xUYBwACLJh5XyI31PCT4mKbl5faWVxGPAH2QZgWIy-phR0m47rHEDUOCvmGwXIjVZ7NTZw9mAAZzQ5Bq6I_NDqy_YyVL_-9lag92ifP8TPj1JBKzdnGaBPopdunWQXvFUTLICH0ebhA6DgK4QBF0irKOGqQSZNIn7qQkvnPxyZZGBp1T4IF9gTTFtJb7PInW29yVvgoSoT_DUaIePBYqbLiiq0xZ3I4tQAsT4PeFzLr48xvWt6yMXh0FhAkOCanLGhqRz6upSNA_SOpe8obTvfOhww49dLXrXwHE9E_cV2UVPZvtHjik6dP5XU_ag";

    private final Context mContext = this;

    // Initializations

    @BindView(R.id.tvPropertyTitle)
    EditText tvPropertyTitle;
    @BindView(R.id.etLocation)
    EditText etLocation;
    @BindView(R.id.tvProductPrice)
    EditText tvProductPrice;
    @BindView(R.id.etArea)
    EditText etArea;
    @BindView(R.id.etBed)
    EditText etBed;
    @BindView(R.id.etBath)
    EditText etBath;
    @BindView(R.id.etPropertyOverView)
    EditText etPropertyOverView;

    private static final int REQUEST_CODE_SELECT_IMG = 1;

    private List<String> yourSelectImgPaths = new ArrayList<>();
    private List<String> imagesBase64 = new ArrayList<>();
    private ArrayList<String> photoPaths = new ArrayList<String>();


    private ArrayList<String> mResults = new ArrayList<>();
    private static final int REQUEST_CODE = 123;


    /*@BindView(R.id.view_pager_post_create)
    ViewPager view_pager_post_create;*/

    private ViewPager viewPager;

    // Spinner

    private MultiSelectSpinner multiSelectSpinnerCategories, multiSelectSpinnerFeatures;

    ProgressDialog progressDialog;

    // Features
    private List<Features> features = new ArrayList<>();
    private List<String> featureIds = new ArrayList<>();
    private List<String> featureTitles = new ArrayList<>();

    // Categories
    private List<Categories> categories = new ArrayList<>();
    private List<String> categoryIds = new ArrayList<>();
    private List<String> categoryNames = new ArrayList<>();


    //@BindView(R.id.btnSelectImage) Button button;

    public void favAddImage(View view) {

        //Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();

        //ImageSelector.show(this, REQUEST_CODE_SELECT_IMG);

        /*// start multiple photos selector
        Intent intent = new Intent(PostCreateActivity.this, ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 15);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 10000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);*/

        droidninja.filepicker.FilePickerBuilder.getInstance().setMaxCount(5)
                .setSelectedFiles(photoPaths)
                .setActivityTheme(R.style.LibAppTheme)
                .pickPhoto(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);
        ButterKnife.bind(this);

        Fresco.initialize(PostCreateActivity.this);

        Log.d(TAG, "onCreate: inside");

        initView();

        progressDialog.show();

        // Categories
        getCategories();
        // Features
        getFeatures();

    }

    private void getCategories() {

        Log.d(TAG, "getCategories: inside");

        Call<CategoriesResponse> call = RetrofitClient.getInstance().getApi().getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {

                categories = response.body().getCategories();

                for (Categories category : categories) {

                    Log.d(TAG, "onResponse: Categories getName: " + category.getName());

                    categoryIds.add(category.getId().toString());
                    categoryNames.add(category.getName());
                }

                multiSelectSpinnerCategories.setItems(categoryNames, categoryIds, "Select-Categories", "Select Multiple Categories");
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });

    }

    private void getFeatures() {

        Log.d(TAG, "getFeatures: inside");

        Call<FeaturesResponse> call = RetrofitClient.getInstance().getApi().getFeatures();
        call.enqueue(new Callback<FeaturesResponse>() {
            @Override
            public void onResponse(Call<FeaturesResponse> call, Response<FeaturesResponse> response) {

                progressDialog.dismiss();

                features = response.body().getFeatures();

                for (Features feature : features) {

                    Log.d(TAG, "onResponse: Features getTitle: " + feature.getTitle());

                    featureIds.add(feature.getId().toString());
                    featureTitles.add(feature.getTitle());
                }

                multiSelectSpinnerFeatures.setItems(featureTitles, featureIds, "Select Features", "Select Multiple Features");

            }

            @Override
            public void onFailure(Call<FeaturesResponse> call, Throwable t) {

            }
        });

    }


    private void initView() {

        // Progress
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        // viewPager
        viewPager = findViewById(R.id.view_pager_post_create);

        // spn
        multiSelectSpinnerCategories = findViewById(R.id.multiSelectSpinnerCategories);
        multiSelectSpinnerFeatures = findViewById(R.id.multiSelectSpinnerFeatures);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            yourSelectImgPaths = ImageSelector.getImagePaths(data);
            Log.d("imgSelector", "paths: " + yourSelectImgPaths);

            /*
             * ----------- base64 ------------
             * */
            for (String yourSelectImgPath : yourSelectImgPaths) {
                try {
                    String encodedImage = ImageBase64
                            .with(getApplicationContext())
                            .noScale()
                            .encodeFile(yourSelectImgPath);

                    imagesBase64.add(encodedImage);

                    Log.d(TAG, "onActivityResult: Image Path: " + yourSelectImgPath + " Encoded To Base64: " + encodedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            /*
             * ----------- base64 ------------
             * */

            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, yourSelectImgPaths);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "Path: " + yourSelectImgPaths.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            return;
        } else if (requestCode == REQUEST_CODE) {

            mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
            assert mResults != null;

            // show results in textview
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
            for (String result : mResults) {
                sb.append(result).append("\n");
            }

            Log.d(TAG, "onActivityResult: " + mResults);

            /*Glide.with(SignupActivity.this)
                    .load(mResults.get(0))
                    .into(profileImage);*/
            //tvResults.setText(sb.toString());


            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, mResults);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "Path: " + mResults.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        } else if (FilePickerConst.REQUEST_CODE_PHOTO == 233) {

            //photoPaths = new ArrayList<>();

            if (photoPaths == null) {
                photoPaths = new ArrayList<String>();
            }

            photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, photoPaths);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "FilePickerConst: Path: " + photoPaths.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnUpload(View view) {


        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", Accept);
        headers.put("Authorization", Authorization);

        ArrayList<Integer> categories = multiSelectSpinnerCategories.getSelectedIdsAsArray();
        ArrayList<Integer> features = multiSelectSpinnerFeatures.getSelectedIdsAsArray();

        Log.d(TAG, "btnUpload: Categori Ids Array:" + categories);
        Log.d(TAG, "btnUpload: Features Ids: Array: " + features);
        Log.d(TAG, "btnUpload: Images array: " + photoPaths);
        Log.d(TAG, "btnUpload: Images array: featured image uri: " + Uri.parse(photoPaths.get(0)));


        /*
         * -------------- okHttp post Create single featured Image Working with MultipartBody -----------
         * */

        progressDialog.show();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("title", "3 room Khawon Free")
                .addFormDataPart("location", "Dhaka")
                .addFormDataPart("latitude", "23.7615")
                .addFormDataPart("longitude", "90.3525")
                .addFormDataPart("condition", "1")
                .addFormDataPart("rent_amount", "123456")
                .addFormDataPart("is_negotiable", "0")
                .addFormDataPart("available_from", "2018-10-15");

        // Categories
        for (int categoryId : categories) {
            builder.addFormDataPart("categories[]", String.valueOf(categoryId));
        }
        // Features
        for (Integer featureId : features) {
            builder.addFormDataPart("features[]", String.valueOf(featureId));
        }

        // featured Image
        if (photoPaths.get(0) != null) {
            File featured_image = new File(photoPaths.get(0));
            if (featured_image.exists()) {
                builder.addFormDataPart("featured_photo", featured_image.getName(), RequestBody.create(MultipartBody.FORM, featured_image));
            }
        }

        // Images
        for (String photoPath : photoPaths) {
            if (photoPath != null) {
                File images = new File(photoPath);
                if (images.exists()) {
                    builder.addFormDataPart("images[]", images.getName(), RequestBody.create(MultipartBody.FORM, images));
                }
            }
        }

        RequestBody requestBody = builder.build();
        Call<PostCreateResponse> call = RetrofitClient.getInstance().getApi().getPostCreateBodyResponse(Accept, Authorization, requestBody);
        call.enqueue(new Callback<PostCreateResponse>() {
            @Override
            public void onResponse(Call<PostCreateResponse> call, Response<PostCreateResponse> response) {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: response code: retrofit: " + response.code());
                //Toast.makeText(PostCreateActivity.this, "Post Id: " + response.body().getResponse().getPost().getId() + " " + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostCreateResponse> call, Throwable t) {

            }
        });

        /*
         * ---------------- okHttp post Create single featured Image Working with MultipartBody----------------
         * */

        String featuredImagePath = getRealPathFromUri(Uri.parse(photoPaths.get(0)));
        Log.d(TAG, "btnUpload: featuredImagePath: " + featuredImagePath);
        if (photoPaths.get(0) != null) {
            File file = new File(photoPaths.get(0));
            if (file.exists()) {



                /*
                 * ------------ okHttp post Create single featured Image Working Direct Request body -------------
                 * */

                // For low quality images

                /*RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "hello khulna")
                        .addFormDataPart("location", "khulna")
                        .addFormDataPart("latitude", "23.7617")
                        .addFormDataPart("longitude", "90.3527")
                        .addFormDataPart("featured_photo", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                        .addFormDataPart("condition","1")
                        .addFormDataPart("rent_amount","123456")
                        .addFormDataPart("is_negotiable","0")
                        .addFormDataPart("available_from","2018-10-13")
                        .build();


                OkHttpClient okHttpClient = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://to-let.androvaid.com/api/v1/post-create")
                        .addHeader("Accept", Accept)
                        .addHeader("Authorization", Authorization)
                        .post(requestBody)
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
                });*/


                /*
                 * ------------ okHttp post Create single featured Image Working Direct Request body -------------
                 * */

            } else {
                Toast.makeText(mContext, "No Image File", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mContext, "No Image path", Toast.LENGTH_SHORT).show();
        }


    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        //MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
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

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

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


}
