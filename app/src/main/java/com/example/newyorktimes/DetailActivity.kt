package com.example.newyorktimes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra("title")
        val byline = intent.getStringExtra("byline")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.bylineTextView).text = byline
        findViewById<TextView>(R.id.urlTextView).text = url

        val imageView = findViewById<ImageView>(R.id.imageView)
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }

        findViewById<TextView>(R.id.seeMoreTextView).setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

    }
}