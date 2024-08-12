package com.gola.webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupWebView()
        setupObservers()
    }

    private fun setupWebView() {
        val webSettings = webView.settings

        // Only enable JavaScript if your web content requires it.
        // JavaScript can be a vector for cross-site scripting (XSS) attacks.
        webSettings.javaScriptEnabled = true

        webSettings.builtInZoomControls = false
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(false)

        // Limit Access to WebView APIs
        // Be careful when enabling WebView APIs and features.
        webSettings.setGeolocationEnabled(false)

        // Use Safe WebView Settings
        // Apply best practices for other settings to avoid vulnerabilities,
        // such as enabling safe browsing features if supported.
        // webSettings.javaScriptCanOpenWindowsAutomatically = false;
        webSettings.setSupportMultipleWindows(false);

        // Be cautious with the allowFileAccess setting.
        // Disabling file access prevents your app from accessing local files via WebView.
        webSettings.allowFileAccess = false;

        webSettings.domStorageEnabled = true

        //  If your application needs to load both HTTP and HTTPS content, enable mixed content mode carefully.
        //  Preferably, only enable it if absolutely necessary and consider using MIXED_CONTENT_NEVER_ALLOW for stricter security.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
        }

        webView.webViewClient = GolaWebViewClient()
    }

    private fun setupObservers() {
        viewModel.url.observe(this, Observer { url ->
            webView.loadUrl(url)
        })

        viewModel.progressVisibility.observe(this, Observer { visibility ->
            progressBar.visibility = visibility
        })

        viewModel.progress.observe(this, Observer { progress ->
            progressBar.progress = progress
        })
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    private inner class GolaWebViewClient : WebViewClient() {
        /**
         * Only load content from trusted URLs and domains.
         * Avoid loading untrusted content to mitigate the risk of content injection attacks.
         */
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url.toString()
            return when {
                url.startsWith("https://") -> {
                    // Allow the WebView to load the URL
                    false
                }

                else -> {
                    // Handle URL
                    Log.e("WebViewClient_Error", "Can't load this url: = ${url}")
                    true
                }
            }
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            viewModel.onPageStarted()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            viewModel.onPageFinished()
        }


        /**
         * Override onReceivedSslError in WebViewClient to handle SSL certificate errors.
         * You should avoid blindly accepting all SSL certificates to prevent man-in-the-middle (MITM) attacks.
         */
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler,
            error: SslError?
        ) {
            // Show an error dialog or handle the error appropriately
            handler.cancel() // Default behavior
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            Log.e("WebViewClient_Error", "request: = ${request?.url.toString()} error = ${error?.errorCode}")
        }
    }
}
