package com.example.pdfviewer

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.FileNameMap
import java.net.URLConnection
import java.util.Locale

class MainActivity : Activity() {  // 改为继承Activity而不是AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defaultMap = URLConnection.getFileNameMap()
        URLConnection.setFileNameMap(object : FileNameMap {
            override fun getContentTypeFor(fileName: String?): String? {
                if (fileName != null && fileName.lowercase(Locale.getDefault()).endsWith(".mjs")) {
                    return "application/javascript"
                }
                return defaultMap?.getContentTypeFor(fileName)
            }
        })
        val webView = WebView(this)
        setContentView(webView)
        val settings = webView.settings
        settings.javaScriptEnabled = true

        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            settings.allowFileAccess = true
        }
        webView.webViewClient = WebViewClient()

        val pdfUrl = "file:///android_asset/pdfjs/web/viewer.html?file=file:///android_asset/main.pdf"
        webView.loadUrl(pdfUrl)
    }
}
