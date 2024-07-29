package com.example.splash

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager

class Home : AppCompatActivity() {
    private lateinit var carousel: ViewPager
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        setupCarousel()
        startAutoScroll()

        val videoView: VideoView = findViewById(R.id.videoComercial)
        val mediaController = MediaController(this)

        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoUri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.videoplayback)
        videoView.setVideoURI(videoUri)

        videoView.start()

        val username = intent.getStringExtra("USERNAME")
        findViewById<TextView>(R.id.welcomeMessage).text = "Olá $username !!"
}

        private fun setupCarousel() {
            carousel = findViewById(R.id.carousel)
            val images = listOf(R.drawable.lanche1, R.drawable.lanche2, R.drawable.lanche4, R.drawable.lanche6, R.drawable.lanche7)
            carousel.adapter = CarouselAdapter(images)
        }

        // Inicia a rotação automática do carrossel
        private fun startAutoScroll() {
            val update = Runnable {
                if (currentPage == (carousel.adapter?.count ?: 1) - 1) {
                    currentPage = 0
                } else {
                    currentPage++
                }
                carousel.setCurrentItem(currentPage, true)
            }

            handler.postDelayed(object : Runnable {
                override fun run() {
                    update.run()
                    handler.postDelayed(this, 3000)
                }
            }, 3000)
        }

        override fun onDestroy() {
            super.onDestroy()
            handler.removeCallbacksAndMessages(null)
        }
    }