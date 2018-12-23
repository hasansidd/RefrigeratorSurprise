package com.siddapps.android.refrigeratorsurprise.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.utils.httpToHttps
import kotlinx.android.synthetic.main.fragment_recipe_webview.*

class RecipeWebViewFragment : Fragment() {
    companion object {
        val TAG = "RecipeWebViewFragment"

        fun newInstance(recipeUrl: String): RecipeWebViewFragment {
            var fragment = RecipeWebViewFragment()
            fragment.recipeURL = recipeUrl
            return fragment
        }
    }
    lateinit var recipeURL: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recipe_webview, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.webViewClient = WebViewClient()
        val url = recipeURL.httpToHttps()
        webView.loadUrl(url)
    }
}