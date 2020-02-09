package com.example.browser

import android.content.DialogInterface
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        //Loading the url in the webView
        webView.loadUrl("https://www.google.com")

        webView!!.webViewClient = WebViewClient()
        webView!!.webChromeClient = WebChromeClient()

        webView!!.webViewClient = object: WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress_bar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progress_bar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }

        webView!!.webChromeClient =object: WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                supportActionBar?.setTitle(title)
            }
        }


    }


    // handles the events when the device's back button is pressed
    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            //Creating alert dialog box to confirm the exit app action
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit Browser")
            builder.setMessage("Do you want to exit the app ?")
            builder.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int ->
                finish()
            })
            builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int ->})
                builder.show()

            //super.onBackPressed()
        }
    }




    // Inflating the created menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }

    // Assigning functionality to the back ,forward ,refresh options from the options menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == R.id.menu_back){
            if(webView.canGoBack()){
                webView.goBack()
            }else{
                Toast.makeText(this,"No more pages to load ", Toast.LENGTH_LONG).show()
            }
            return true
        }else if(id== R.id.menu_forward){
            if(webView.canGoForward()){
                webView.goForward()
            }else{
                Toast.makeText(this,"No more pages to load ", Toast.LENGTH_LONG).show()
            }
        }else if(id==R.id.refresh_btn){
                webView.reload()
        }
        return super.onOptionsItemSelected(item)
    }



}
