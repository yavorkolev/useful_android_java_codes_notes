package com.example.asynctaskexample;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.image);
        AsyncTaskExample asyncTask=new AsyncTaskExample();
        asyncTask.execute("https://www.learningcontainer.com/wp-content/uploads/2020/07/Large-Sample-Image-download-for-Testing.jpg");
    }
    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }
    }
}
//<LinearLayout xmlns:android = "http://schemas.android.com/apk/res/android"
//        xmlns:tools = "http://schemas.android.com/tools"
//        android:id = "@+id/rootview"
//        android:layout_width = "match_parent"
//        android:layout_height = "match_parent"
//        android:orientation = "vertical"
//        android:background = "#c1c1c1"
//        android:gravity = "center_horizontal"
//        tools:context = ".MainActivity">
//<ImageView
//        android:id = "@+id/image"
//                android:layout_width = "300dp"
//                android:layout_height = "300dp" />
//</LinearLayout>
//<uses-permission android:name = "android.permission.INTERNET"/>