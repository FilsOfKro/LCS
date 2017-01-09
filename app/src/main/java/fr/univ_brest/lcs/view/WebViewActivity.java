package fr.univ_brest.lcs.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.annotation.SuppressLint;
import android.app.Activity;

import fr.univ_brest.lcs.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private Button retourCarte;

    private String password = "projetubo";
    private String username = "bounceur";
    @SuppressLint("SetJavaScriptEnabled")

    public void setPwd(String pwd){
        this.password = pwd;
    }
    public void setUsername(String login){
        this.username = login;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Button retourCarte=(Button)findViewById(R.id.buttonUrl);
        webView = (WebView) findViewById(R.id.webView1);
        //enable the use of javascript to manipulate the web page
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("http://iotubo.univ-brest.fr/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            /**
             * function that insert the password and login of the camera into the web page
             */
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                final String js = "javascript:" +
                        "document.getElementById('login_pwd').value = '" + password + "';" +
                        "document.getElementById('login_user').value = '" + username + "';" +
                        "document.getElementById('login_botton').click()";

            }
        });
        //button to return to the main activity
        retourCarte.setOnClickListener(new OnClickListener() {
            @Override
            void onClick(View v) {
                Intent intent = new Intent(this, MapsActivity.class);
                this.finish();
                startActivity(intent);
            }
        });
    }
}
