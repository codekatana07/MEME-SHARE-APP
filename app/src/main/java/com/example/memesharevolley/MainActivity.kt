package com.example.memesharevolley

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var CurrentImageUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        loadMeme()

    }

    private fun loadMeme(){
        val queue = Volley.newRequestQueue(this)
        var progressbar =findViewById<ProgressBar>(R.id.progressBar)
        progressbar.visibility=View.VISIBLE
        val url = "https://meme-api.com/gimme"
        var memeImageView =findViewById<ImageView>(R.id.memeImageView)
     // Request a string response from the provided URL.
         val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
            CurrentImageUrl = response.getString("url")
                Glide.with(this).load(CurrentImageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }


                }).into(memeImageView)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong nigga",Toast.LENGTH_LONG).show()
            }
        )

    // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }




    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"oyee ye wala meme dekh bey !! $CurrentImageUrl")
        val chooser= Intent.createChooser(intent, "kaha bhejna hai bey ??")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}