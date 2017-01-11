/*
 * Copyright (c) 2017.
 *
 * Owenn Pantry / Lucas Roulin
 *
 * owenn.pantry@gmail.com
 *
 * Ce logiciel est un programme informatique développé comme un projet d'étude.
 *
 *  Ce logiciel est régi par la licence CeCILL soumise au droit français et
 *  respectant les principes de diffusion des logiciels libres. Vous pouvez
 *  utiliser, modifier et/ou redistribuer ce programme sous les conditions
 *  de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 *  sur le site "http://www.cecill.info".
 *
 *  En contrepartie de l'accessibilité au code source et des droits de copie,
 *  de modification et de redistribution accordés par cette licence, il n'est
 *  offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 *  seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 *  titulaire des droits patrimoniaux et les concédants successifs.
 *
 *  A cet égard  l'attention de l'utilisateur est attirée sur les risques
 *  associés au chargement,  à l'utilisation,  à la modification et/ou au
 *  développement et à la reproduction du logiciel par l'utilisateur étant
 *  donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 *  manipuler et qui le réserve donc à des développeurs et des professionnels
 *  avertis possédant  des  connaissances  informatiques approfondies.  Les
 *  utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 *  logiciel à leurs besoins dans des conditions permettant d'assurer la
 *  sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 *  à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 *  Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 *  pris connaissance de la licence CeCILL, et que vous en avez accepté les
 *  termes.
 */

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
        setContentView(R.layout.activity_web_view);

        Button retourCarte=(Button)findViewById(R.id.buttonUrl);
        webView = (WebView) findViewById(R.id.webView1);
        //enable the use of javascript to manipulate the web page
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("http://iotubo.univ-brest.fr");
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
            public void onClick(View v) {
                Intent intent = new Intent(WebViewActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
