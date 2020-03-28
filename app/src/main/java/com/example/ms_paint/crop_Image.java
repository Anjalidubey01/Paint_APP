package com.example.ms_paint;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class crop_Image extends AppCompatActivity {
    Uri uri;
ImageView im;
Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__image);
        im=(ImageView)findViewById(R.id.imageView);
        but = (Button)findViewById(R.id.Button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(crop_Image.this);
            }
        });
    }

   // public void pick_image(){
     //   if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
       //     try{
         //       requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},555);
           // }catch (Exception e){}
        //}
        //else
        //{
         //   image_picker();
        //}

    //}
    private  void cropreq(Uri image){
        CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       //  super.onActivityResult(requestCode, resultCode, data);
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
            //    try {
              //      Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
               //     im.setImageBitmap(bitmap);
                //} catch (IOException e) {
                  //  e.printStackTrace();
                //}
            //}
        //}

    }

   // @Override
   // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     //   if(requestCode==555 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
       //     image_picker();
      //  else
       // {pick_image();
        //}
    //}
   // public void image_picker(){
    //    CropImage.startPickImageActivity(this);
    //}
}
