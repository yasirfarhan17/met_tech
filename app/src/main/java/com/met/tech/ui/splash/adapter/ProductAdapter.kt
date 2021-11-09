package com.met.tech.ui.splash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.met.tech.R
import com.met.tech.databinding.RvProductBinding
import com.met.tech.ui.splash.ItemsModel

class ProductAdapter(private var context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val items = ArrayList<ItemsModel>()
    fun submitList(item: List<ItemsModel>) {
        if(!item.isEmpty()){
            notifyItemRangeRemoved(0,itemCount)
        }
        items.clear()
        items.addAll(item)
        notifyItemRangeInserted(0, items.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProductViewHolder {
        val binding = RvProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }


    override fun getItemCount() = items.size

    var count = 0
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ProductViewHolder(private val binding: RvProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemsModel) {
            with(binding) {
                var click = true
                tvProductName.text = item.product_name
                tvProductName2.text = item.category_name
                tvPrice.text = item.price
                imgProduct.load(item.product_image) {
                    crossfade(true)
                    placeholder(R.drawable.ic_gallery__1_)
                }
                imgHeart.setOnClickListener {
                    if (!click) {
                        imgHeart.background=(context.getResources().getDrawable(R.drawable.ic_heart))
                        click = true
                    } else {
                        imgHeart.background=(context.getResources().getDrawable(R.drawable.ic_heart_red))
                        click = false
                    }
                }
                btAdd.setOnClickListener {
                    count++
                    tvCount.text = count.toString()
                }
                btEnd.setOnClickListener {
                    if(count==0){
                        return@setOnClickListener
                    }
                    count--
                    tvCount.text = count.toString()
                }
            }

        }
    }
}