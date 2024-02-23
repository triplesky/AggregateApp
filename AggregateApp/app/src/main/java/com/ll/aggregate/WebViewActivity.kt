package com.ll.aggregate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.lzyzsd.jsbridge.BridgeWebView

/**
 ***************************************
 * 项目名称: AggregateApp
 * @Author ll
 * 创建时间: 2024/1/3    14:49
 * 用途
 ***************************************

 */
class WebViewActivity : AppCompatActivity() {
    var webView: BridgeWebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity_layout)

        webView = findViewById<View>(R.id.webView) as BridgeWebView
        webView?.setWebChromeClient(object : WebChromeClient() {
            fun openFileChooser(
                uploadMsg: ValueCallback<Uri?>,
                AcceptType: String?,
                capture: String?,
            ) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri?>, AcceptType: String?) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri?>) {

            }

            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams,
            ): Boolean {

                return true
            }
        })


        webView?.loadUrl("https://fx.cmbchina.com/hq/")

        findViewById<TextView>(R.id.refresh_btn).setOnClickListener {
            webView?.reload()
        }
    }
}