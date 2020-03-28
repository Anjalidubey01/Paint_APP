package com.example.ms_paint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    int clickp=0,clickm=0;
    private PaintView paintView;
    ImageButton image;
    ImageButton yellow;
    ImageButton blue;
    ImageButton plus;
    ImageButton minus;
    ImageButton stroke;
    ImageButton crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paintView = (PaintView) findViewById(R.id.paintView);
        image=(ImageButton)findViewById(R.id.imageButton2);
        blue=(ImageButton)findViewById(R.id.imageButton3);
        plus=(ImageButton)findViewById(R.id.imageButton6);
        minus=(ImageButton)findViewById(R.id.imageButton5);
        stroke=(ImageButton)findViewById(R.id.imageButton4);
        crop=(ImageButton)findViewById(R.id.imageButton9);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,crop_Image.class);
                startActivity(in);
            }
        });
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paintView.strokeWidth==20)
                paintView.strokeWidth=10;
                else
                    paintView.strokeWidth=20;
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {clickp=1;
            if(clickm==0){
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_in);
                paintView.startAnimation(animation);
            }
            if(clickm==1){
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.nom);
                paintView.startAnimation(animation);
                clickm=0;
            }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {clickm=1;
                if(clickp==0){
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.normal);
                    paintView.startAnimation(animation);
                } if(clickp==1){
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zooom_outt);
                paintView.startAnimation(animation);
                clickp=0;
            }
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
            case R.id.jpeg:{
                View con=paintView;
                con.setDrawingCacheEnabled(true);
                con.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                Bitmap bitmap = con.getDrawingCache();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(path+"/image.JPEG");
                FileOutputStream outputStream;
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    file.createNewFile();
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(MainActivity.this,"Image exported as JPEG image",Toast.LENGTH_LONG).show();
                }catch (Exception e){e.printStackTrace();
                    Toast.makeText(MainActivity.this,"ghghhgugu",Toast.LENGTH_LONG).show();
                }
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
                    Toast.makeText(MainActivity.this,"ghghhgugu",Toast.LENGTH_LONG).show();
                }
                break;
            }


        }
        return true;
    }

}

