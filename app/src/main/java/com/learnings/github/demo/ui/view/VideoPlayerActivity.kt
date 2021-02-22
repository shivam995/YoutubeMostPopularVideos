package com.learnings.github.demo.ui.view

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import com.google.android.youtube.player.YouTubePlayerView
import com.learnings.github.demo.BuildConfig
import com.learnings.github.demo.R
import com.learnings.github.demo.databinding.ActivityVideoPlayerBinding
import com.learnings.github.demo.ui.base.YouTubePlayerBaseActivity

class VideoPlayerActivity : YouTubePlayerBaseActivity(), OnFullscreenListener {
    private var fullScreen: Boolean = false
    private var player: YouTubePlayer? = null
    private lateinit var playerView: YouTubePlayerView
    private var mVideoId = ""
    private lateinit var binding: ActivityVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)
        mVideoId = intent.getStringExtra(ARG_VIDEO_ID)!!
        playerView = findViewById(R.id.player)
        playerView.initialize(BuildConfig.API_KEY, this)


    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        this.player = player
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
        player.setOnFullscreenListener(this)

        if (!wasRestored) {
            player.cueVideo(mVideoId)
        }
    }

    override fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return playerView
    }

    companion object {
        const val ARG_VIDEO_ID = "ARG_VIDEO_ID"
        fun createIntent(context: Context, videoId: String): Intent {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(ARG_VIDEO_ID, videoId)
            return intent
        }


    }

    private fun doLayout() {
        val playerParams =
            playerView.layoutParams as LinearLayout.LayoutParams
        if (fullScreen) {
            playerParams.width = LinearLayout.LayoutParams.MATCH_PARENT
            playerParams.height = LinearLayout.LayoutParams.MATCH_PARENT
        } else {
            playerParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            playerParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        }
    }

    override fun onFullscreen(isFullScreen: Boolean) {
        this.fullScreen = isFullScreen
        doLayout()
    }
}