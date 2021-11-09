package com.met.tech.ui.splash.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.met.tech.R
import com.met.tech.base.BaseFragment
import com.met.tech.databinding.FragmentCategoryBinding
import com.met.tech.injection.component.AppComponent
import com.met.tech.ui.splash.SplashViewModel
import com.met.tech.ui.splash.adapter.ProductAdapter


class CategoryFragment : BaseFragment<FragmentCategoryBinding, SplashViewModel>() {


    companion object {

        fun newInstance(position: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args
            fragment.retainInstance = true
            return fragment
        }
    }

    override fun getViewModelClass(): Class<SplashViewModel> =
        SplashViewModel::class.java

    override fun getLayout(): Int = R.layout.fragment_category

    override fun injectFragment(appComponent: AppComponent) {
        appComponent.homeComponent().create().inject(this)
    }

    override fun addObservers() {
        observeItemList()
    }


    var position = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val request = Volley.newRequestQueue(activity)
        viewModel.getCategory(request)
        position = arguments?.getInt("position")!!
        binding.rvProduct.layoutManager = LinearLayoutManager(activity)
        binding.rvProduct.adapter = ProductAdapter(requireContext())
    }

    private fun observeItemList() {
        viewModel.getCategory.observe(this, {
            if (it.get(position).category_item.size > 0) {
                binding.tvEmptyLay.visibility = View.GONE
                (binding.rvProduct.adapter as ProductAdapter).submitList(it.get(position).category_item)
            } else binding.tvEmptyLay.visibility = View.VISIBLE
        })
    }
}