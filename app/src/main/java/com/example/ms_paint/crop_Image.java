package com.example.ms_paint;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class crop_Image extends AppCompatActivity {
    Uri uri;
    private int STORAGE_PERMISSION_CODE=1;
ImageView im;
Button but;
Bitmap bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__image);
        im=(ImageView)findViewById(R.id.imageView);
        but = (Button)findViewById(R.id.Button);
       // bit=(Bitmap) getIntent().getParcelableExtra("Bitmap");
       // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //bit.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = getIntent().getStringExtra("image");
        Uri uri = Uri.parse(path);
        im.setImageURI(uri);
        try {
            bit = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void request(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(crop_Image.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(crop_Image.this).setTitle("Permission needed").setMessage("Permission needed to store in the external storage").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(crop_Image.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(crop_Image.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(crop_Image.this,"Permission granted",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(crop_Image.this,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void makejpeg(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path+"/image.PNG");
        FileOutputStream outputStream;
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            bit.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            outputStream.flush();
            outputStream.close();
            Toast.makeText(crop_Image.this,"Image exported as jpeg",Toast.LENGTH_LONG).show();
        }catch (Exception e){e.printStackTrace();
            Toast.makeText(crop_Image.this,"sorry,error somewhere",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.jpeg:{if(ContextCompat.checkSelfPermission(crop_Image.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {makejpeg();}
            else{request();}

                break;
            }
            case R.id.png:{

                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(path+"/image.PNG");
                FileOutputStream outputStream;
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    file.createNewFile();
                    outputStream = new FileOutputStream(file);
                    bit.compress(Bitmap.CompressFormat.PNG,100,outputStream);

                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(crop_Image.this,"Image exported as png",Toast.LENGTH_LONG).show();
                }catch (Exception e){e.printStackTrace();
                    Toast.makeText(crop_Image.this,"sorry,error somewhere",Toast.LENGTH_LONG).show();
                }
                break;
            }


        }
        return true;
    }
    private  void cropreq(Uri image){
        CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
                uri=imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
            else {
                cropreq(imageuri);
            }
          cropreq(imageuri);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                im.setImageURI(result.getUri());
            }}


    }

}
