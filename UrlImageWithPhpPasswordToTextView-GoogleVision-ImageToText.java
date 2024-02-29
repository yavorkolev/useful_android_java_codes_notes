//implementation("com.google.android.gms:play-services-vision:20.1.3")
//<uses-permission android:name="android.permission.INTERNET"/>
//<?xml version="1.0" encoding="utf-8"?>
//<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//  xmlns:tools="http://schemas.android.com/tools"
//  android:layout_width="match_parent"
//  android:layout_height="match_parent"
//  tools:context=".MainActivity">
//
//<TextView
//  android:id="@+id/textView"
//  android:layout_width="wrap_content"
//  android:layout_height="wrap_content"
//  android:layout_centerInParent="true" />
//
//</RelativeLayout>
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadImageTask(findViewById(R.id.textView))
                .execute("https://www.vpnbook.com/password.php?bg=2&t=0.73395800%201709203178");
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        TextView bmImage;
        TextBlock textBlock;

        public DownloadImageTask(TextView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getBaseContext()).build();
                if(!textRecognizer.isOperational()) {
                    IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                    boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

                    if (hasLowStorage) {
                        // Do something!
                    }
                }

                Frame imageFrame = new Frame.Builder()
                        .setBitmap(result)
                        .build();

                SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
                for (int i = 0; i < textBlocks.size(); i++) {
                    textBlock = textBlocks.get(textBlocks.keyAt(i));
                }
            }
            bmImage.setText(textBlock.getValue());
        }
    }

}