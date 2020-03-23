package com.example.login;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


            imageView = findViewById(R.id.image);

            String url = "https://firebasestorage.googleapis.com/v0/b/login-2811f.appspot.com/o/Wallpaper.png?alt=media&token=77c5877f-3806-4c88-8d0b-419fa7a52ab0";//Retrieved url as mentioned above

            Glide.with(getApplicationContext()).load(url).into(imageView);

        }
    }

