package com.learnings.github.demo.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learnings.github.demo.R
import com.learnings.github.demo.data.model.Video
import com.learnings.github.demo.data.network.ApiService
import com.learnings.github.demo.data.network.ApiServiceImpl
import com.learnings.github.demo.data.network.RetrofitClient
import com.learnings.github.demo.databinding.ActivityVideoListBinding
import com.learnings.github.demo.ui.adapter.VideoListAdapter
import com.learnings.github.demo.ui.viewmodel.VideoListViewModel
import com.learnings.github.demo.utlis.Status
import com.learnings.github.demo.utlis.ViewModelFactory


class VideoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoListBinding
    private var listAdapter: VideoListAdapter? = null
    private lateinit var viewModel: VideoListViewModel
    private var nextPageToken: String? = null
    private lateinit var layoutManager: LinearLayoutManager
    private var loading = true
    private var mDataList = ArrayList<Video>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_list)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiServiceImpl(RetrofitClient(this).retrofit.create(ApiService::class.java)))
        ).get(VideoListViewModel::class.java)

        prepareListAdapter()

        addScrollListener()

        observeViewModelData()
    }

    private fun addScrollListener() {
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = binding.rvList.layoutManager!!.childCount
                val totalItemCount = binding.rvList.layoutManager!!.itemCount
                val firstVisibleItemPosition =
                    (binding.rvList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (!loading && !nextPageToken.isNullOrEmpty()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= VideoListViewModel.VIDEO_LIST_PAGE_ITEM_COUNT
                    ) {
                        loading = true
                        observeViewModelData()
                        viewModel.popularVideos(nextPageToken)
                    }
                }
            }
        })
    }

    private fun observeViewModelData() {
        viewModel.popularVideos(nextPageToken).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading = false
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { videoResponse ->
                            nextPageToken = videoResponse.nextPageToken
                            videoResponse.videos?.let { videos ->
                                mDataList.addAll(videos)
                                listAdapter?.updateList(videos as ArrayList<Video>)
                            }
                        }
                    }
                    Status.ERROR -> {
                        loading = false
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun prepareListAdapter() {
        listAdapter = VideoListAdapter(mDataList)
        binding.rvList.adapter = listAdapter
        layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                binding.rvList.context,
                (binding.rvList.layoutManager as LinearLayoutManager).orientation
            )
        )

        listAdapter?.onItemClick = { video, imageView ->
            val activityOptionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    imageView,
                    "imageMain"
                )

            val intent = VideoPlayerActivity.createIntent(this, video.videoId!!)
            startActivity(intent, activityOptionsCompat.toBundle())
        }
    }

}