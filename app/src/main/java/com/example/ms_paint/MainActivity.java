package com.example.ms_paint;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE=1;
    Bitmap enlarge;
    int clickp=0,clickm=0;
    private PaintView paintView;
    int color;
    Uri uri;
    String m_Text;
    ImageButton alphabet;
    ImageButton image;
    ImageButton yellow;
    ImageButton blue;
    ImageButton thick;
    ImageButton plus;
    ImageButton minus;
    ImageButton stroke;
    ImageButton crop,fill;
    ImageButton choose_color,eraser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color=Color.WHITE;
        thick = (ImageButton)findViewById(R.id.imageButton4);
        eraser =(ImageButton)findViewById(R.id.imageButton6);
        alphabet=(ImageButton)findViewById(R.id.imageButton9);
        fill=(ImageButton)findViewById(R.id.imageButton5);
        paintView = (PaintView) findViewById(R.id.paintView);
        image=(ImageButton)findViewById(R.id.imageButton2);
        blue=(ImageButton)findViewById(R.id.imageButton3);
        plus=(ImageButton)findViewById(R.id.imageButton16);
        minus=(ImageButton)findViewById(R.id.imageButton15);
        stroke=(ImageButton)findViewById(R.id.imageButton14);
        crop=(ImageButton)findViewById(R.id.imageButton19);

        choose_color=(ImageButton)findViewById(R.id.imageButton12);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              paintView.strokeWidth=10;
            }
        });
        thick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.strokeWidth=20;
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.currentColor=Color.WHITE;
                paintView.strokeWidth=20;
            }
        });
        fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cont=paintView;
                cont.setDrawingCacheEnabled(true);
                cont.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                Bitmap bitmap = cont.getDrawingCache();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String path = MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(),bitmap,"Tit",null);
                Uri uri = Uri.parse(path);
                CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(MainActivity.this);
            }
        });
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               paintView.strokeWidth=5;
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {clickp=1;
                paintView.mCanvas.scale(1.5f,1.5f);
                paintView.valida();
           // if(clickm==0){
             //   Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_in);
               // paintView.startAnimation(animation);

            //}
            //if(clickm==1){
              //  Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.nom);
                //paintView.startAnimation(animation);
                //clickm=0;

            //}
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {clickm=1;
                paintView.mCanvas.scale(0.5f,0.5f);
                paintView.valida();
              //  if(clickp==0){
                //    Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.normal);
                  //  paintView.startAnimation(animation);
                //} if(clickp==1){
                //Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zooom_outt);
                //paintView.startAnimation(animation);
                //clickp=0;
            //}
            }

        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { paintView.currentColor= Color.RED;
             //Intent in = new Intent(MainActivity.this,crop_Image.class);
             //startActivity(in);


              // paintView.currentColor= Color.BLUE;//Toast.makeText(MainActivity.this,"Heyy u clicked this",Toast.LENGTH_LONG).show();
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.currentColor=Color.BLUE;
            }
        });
        yellow=(ImageButton)findViewById(R.id.imageButton);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            paintView.currentColor=Color.YELLOW;
                //Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_in);
                //paintView.startAnimation(animation);
            }
        });
        choose_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(MainActivity.this, color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
paintView.currentColor=color;
                    }
                });
                ambilWarnaDialog.show();
            }
        });

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.eraser:
            { paintView.clear();}
            case R.id.split:{
                paintView.currentColor= Color.WHITE;
                paintView.strokeWidth=25;
            break;}
            case R.id.jpeg:{if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {makejpeg();}
            else{request();}

                break;
            }
            case R.id.png:{
                View con=paintView;
                con.setDrawingCacheEnabled(true);
                con.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                Bitmap bitmap = con.getDrawingCache();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(path+"/image.PNG");
                FileOutputStream outputStream;
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    file.createNewFile();
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(MainActivity.this,"Image exported as png",Toast.LENGTH_LONG).show();
                }catch (Exception e){e.printStackTrace();
                    Toast.makeText(MainActivity.this,"sorry,error somewhere",Toast.LENGTH_LONG).show();
                }
                break;
            }


        }
        return true;
    }
    public void request(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(MainActivity.this).setTitle("Permission needed").setMessage("Permission needed to store in the external storage").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Permission granted",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void makejpeg(){
        View con=paintView;
        con.setDrawingCacheEnabled(true);
        con.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = con.getDrawingCache();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path+"/image.PNG");
        FileOutputStream outputStream;
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            outputStream.flush();
            outputStream.close();
            Toast.makeText(MainActivity.this,"Image exported as jpeg",Toast.LENGTH_LONG).show();
        }catch (Exception e){e.printStackTrace();
            Toast.makeText(MainActivity.this,"sorry,error somewhere",Toast.LENGTH_LONG).show();
        }
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
                CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
            }
            CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
               // im.setImageURI(result.getUri());
                Intent in = new Intent(MainActivity.this,crop_Image.class);
               in.putExtra("image",result.getUri().toString());
               // View cont=paintView;
                //cont.setDrawingCacheEnabled(true);
                //cont.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                //Bitmap bitmap = cont.getDrawingCache();
                //in.putExtra("Bitmap",bitmap);
                startActivity(in);
            }}
    }

}

