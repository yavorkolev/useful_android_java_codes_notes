public class MainActivity extends AppCompatActivity {
    private WebView mWv;

    private static final String HTML_AS_STRING =
            "<!DOCTYPE html>"+
                    "<html>"+
                    "<body>"+
                    "<button class=\"fc-button fc-cta-consent fc-primary-button\" role=\"button\" aria-label=\"Consent\" tabindex=\"0\">"+
                    "<div class=\"fc-button-background\"></div>"+
                    "<p class=\"fc-button-label\">Consent</p>"+
                    "</button>"+
                    "</body>"+
                    "</html>";
    private static final String JS_AS_STRING =
                    "<script src=\"index.js\"></script>";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWv = findViewById(R.id.webview);
        mWv.getSettings().setJavaScriptEnabled(true);

        mWv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                mWv.loadUrl("javascript:showAlert()");
            }
        });
        mWv.setWebChromeClient(new WebChromeClient());

        mWv.loadDataWithBaseURL("file:///android_asset/www/", HTML_AS_STRING+ JS_AS_STRING, "text/html", "UTF-8", null);
//----------------------------------------------------------------------------------------------------------------------
// "file:///android_asset/www/" -> index.js
//function showAlert() {
//    alert("A simple alert!");
//}
//------------------------------
//<WebView
//  android:id="@+id/webview"
//  android:layout_width="match_parent"
//  android:layout_height="match_parent"
///>