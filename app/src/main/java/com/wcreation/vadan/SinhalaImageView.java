package com.wcreation.vadan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.facebook.ads.*;

public class SinhalaImageView extends AppCompatActivity {

    ImageView img;
    Button btnDownload,btnShare;
    Bitmap bitmap;
    private static final int WRITE_EXTERNAL_STORAGE_CODE=0;
    private AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinhala_image_view);

        String image_url = getIntent().getExtras().getString("anime_img") ;

        img = (ImageView) findViewById(R.id.svimg);



        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        Glide.with(this).load(image_url).apply(requestOptions).into(img);

        //bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        btnDownload = (Button)findViewById(R.id.btnSave);
        btnShare =(Button)findViewById(R.id.btnViewShare);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,WRITE_EXTERNAL_STORAGE_CODE);
                    }else {
                        saveImage();
                    }
                }else {
                    saveImage();
                }
            }
        });



        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //

                try {
                    //File file = new File(SinhalaImageView.this.getExternalCacheDir(),"wcreation.png");
                    //File file = Environment.getExternalStorageDirectory();

                    bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

                    File file = new File(getExternalCacheDir(),"wcreation.png");//add

                    Uri uri = FileProvider.getUriForFile(getApplicationContext(),"com.wcreation.vadan.provider",file);

                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    file.setReadable(true,false);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
                    intent.putExtra(Intent.EXTRA_STREAM,uri);
                    intent.setType("image/png");
                    startActivity(Intent.createChooser(intent,"Share Image Via"));
                }
                catch (Exception e){
                    Toast.makeText(SinhalaImageView.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                //catch (FileNotFoundException e) {
                //  e.printStackTrace();
                //   Toast.makeText(SinhalaImageView.this, "File Not Found", Toast.LENGTH_SHORT).show();
                // }catch (IOException e){
                //     e.printStackTrace();
                // }catch (Exception e){
                //     e.printStackTrace();
                // }
            }
        });

        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "3286945664701006_3292794897449416", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();



    }

    private void saveImage() {


        bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

        // File path = Environment.getExternalStorageDirectory();
        // File path = new File(Environment.getExternalStorageDirectory().toString(),"Love");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // File dir = new File(path+"/DCIM/");
        String imagename = timeStamp+".png";
        File file = new File(path,imagename);
        // File file2 = new File(dir,imagename);
        OutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

            outputStream.flush();
            outputStream.close();

            Toast.makeText(SinhalaImageView.this, "Save Image In To Gallery", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(SinhalaImageView.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveImage();

                }else {
                    Toast.makeText(this, "enable permission to save images", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
