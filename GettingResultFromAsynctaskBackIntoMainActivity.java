package com.example.getresultfromasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AsyncResponse{
    MyAsyncTask asyncTask =new MyAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncTask.delegate = this;
        asyncTask.execute();
    }

    @Override
    public void processFinish(String output) {
        Log.i("MyTag", "output:"+output);
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, String> {
        TextBlock textBlock;
        public AsyncResponse delegate = null;
        ProgressDialog progressDialog;
        URL ImageUrl = null;
        InputStream is = null;
        Bitmap bmImg = null;
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...downloading password!");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                ImageUrl = new URL("https://www.vpnbook.com/password.php?bg=2&t=0.73395800%201709203178");
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
                //
                if(bmImg != null) {
                    TextRecognizer textRecognizer = new TextRecognizer.Builder(getBaseContext()).build();
                    if(!textRecognizer.isOperational()) {
                        IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                        boolean isStorageLow = registerReceiver(null, lowStorageFilter) != null;
                        if (isStorageLow) {
                            // Do something on low memory!
                        }
                    }
                    Frame imageFrame = new Frame.Builder()
                            .setBitmap(bmImg)
                            .build();
                    SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
                    for (int i = 0; i < textBlocks.size(); i++) {
                        textBlock = textBlocks.get(textBlocks.keyAt(i));
                    }
                }
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
            return textBlock.getValue();
        }

        @Override
        protected void onPostExecute(String result) {
            delegate.processFinish(result);
            progressDialog.hide();
        }
    }
}
// Create interface
//public interface AsyncResponse {
//    void processFinish(String output);
//}
// Add library for Image to text
//    implementation("com.google.android.gms:play-services-vision:20.1.3")
// Add INTERNET permission
//<uses-permission android:name="android.permission.INTERNET" />