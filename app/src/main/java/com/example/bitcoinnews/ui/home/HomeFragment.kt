package com.example.bitcoinnews.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoinnews.R
import com.example.bitcoinnews.databinding.FragmentHomeBinding
import com.example.bitcoinnews.ui.detail.DetailActivity
import com.example.core.data.Resource
import com.example.core.ui.NewsAdapter
import com.example.core.ui.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private val progress by lazy { ProgressDialog(requireActivity()) }

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            newsAdapter = NewsAdapter()
            observer()
            setupView()
            setupClickListener()
        }
    }

    private fun observer() {
        homeViewModel.news.observe(viewLifecycleOwner) { news ->
            if (news != null) {
                when (news) {
                    is Resource.Loading -> {
                        showProgressDialog(true)
                        showShimmer(true)
                    }
                    is Resource.Success -> {
                        showProgressDialog(false)
                        showShimmer(false)
                        newsAdapter.submitList(news.data)
                    }

                    is Resource.Error -> {
                        showProgressDialog(false)
                        showShimmer(false)
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            news.message ?: getString(R.string.util_something_wrong)
                    }
                }
            }
        }
    }

    private fun setupView() {
        with(binding.rvNews) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    binding.fabScrollToTop.isVisible =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() >= 3
                }
            })
        }
    }

    private fun setupClickListener() {
        binding.fabScrollToTop.setOnClickListener {
            binding.rvNews.smoothScrollToPosition(0)
        }

        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
    }

    private fun showShimmer(isShimmering: Boolean) {
        binding.progressBar.visibility =
            if (isShimmering) View.VISIBLE else View.GONE
    }

    private fun showProgressDialog(isLoading: Boolean) {
        if (isLoading) progress.show() else progress.dismiss()
    }
}