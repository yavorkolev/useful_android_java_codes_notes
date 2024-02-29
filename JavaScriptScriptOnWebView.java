public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView =  findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("https://www.vpnbook.com/freevpn");
    }
}
//
public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        String script = "(" +
                "function() {" +
                "var result = \"result: \";" +
                "if(document.querySelector('button.fc-button.fc-cta-consent.fc-primary-button') !=null) {" +
                "document.querySelector('button.fc-button.fc-cta-consent.fc-primary-button').click();" +
                "result = result + \"FirstSuccess\"; " +
                "} " +
                " return Array.from(document.getElementsByTagName(\"img\")).map(i => i.src); " +
                "})();";

        view.evaluateJavascript(script, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                view.scrollTo(0, 2000);
                Log.i("MyTag", value);
            }
        });
    }
}
//
//<?xml version="1.0" encoding="utf-8"?>
//<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    tools:context=".MainActivity">
//
//    <WebView
//        android:id="@+id/webview"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        />
//
//</RelativeLayout>
