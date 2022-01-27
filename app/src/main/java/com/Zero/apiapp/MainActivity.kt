package com.Zero.apiapp

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var memeImg:ImageView? = null
    var currentImg:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context: Context = this
        memeImg = findViewById<ImageView>(R.id.MemeImage)
        loadMeme()
    }
    private fun loadMeme(){
        var pgCIrcle:ProgressBar =findViewById(R.id.progressCircle)
        pgCIrcle.visibility  = View.VISIBLE
// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener{ response ->
                currentImg = response.getString("url")
                memeImg?.let { Glide.with(this).load(currentImg).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgCIrcle.visibility  = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgCIrcle.visibility  = View.GONE
                        return false
                    }
                }).into(it) }
            },
            Response.ErrorListener { Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show() })

// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)

    }
    fun shareBtnAction(view: View) {
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Checkout this cool meme! $currentImg")
            val chooser = Intent.createChooser(intent, "Checkout this cool meme!")
            startActivity(chooser)
    }
    fun nextBtnAction(view: View) {
        loadMeme()
    }
    fun backBtnAction(view: View) {

    }


}