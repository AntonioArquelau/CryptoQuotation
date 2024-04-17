package com.example.cryptoquotation.view

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoquotation.R
import com.example.cryptoquotation.databinding.QuotationItemBinding
import com.example.cryptoquotation.repository.data.Bitcoin
import com.example.cryptoquotation.repository.data.DataStatus
import com.example.cryptoquotation.viewmodel.MainViewModel
import java.lang.Appendable
import kotlin.coroutines.coroutineContext

class QuotationListAdapter(
    private val viewModel: MainViewModel,
    private val context: Context,
) : RecyclerView.Adapter<QuotationListAdapter.QuotationViewHolder>() {

    private val  itemList = mutableListOf<QuotationItem>()


    fun setData(item:  QuotationItem){
        itemList.add(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotationListAdapter.QuotationViewHolder {
        val itemView =  QuotationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuotationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuotationListAdapter.QuotationViewHolder, position: Int) {
        val item = itemList[position]
        holder.quotation.text = "${item.mainQuotation}/${item.targetQuotation}"
        holder.rate.text = item.rate
        val data = MutableLiveData<DataStatus<Bitcoin>>()
        viewModel.getExchangeRate(item.mainQuotation.toString(), item.targetQuotation.toString(), data)

        data.observeForever {
            when (it) {
                is DataStatus.Success -> {
                    if (holder.rate.text.toString().isNotEmpty() && it.data?.rate?.isNotEmpty()!!) {
                        if ((holder.rate.text.toString().toFloat()) > (it.data.rate.toFloat()!!)) {
                            holder.imageView.setColorFilter(
                                ContextCompat.getColor(
                                    context,
                                    R.color.red
                                )
                            )
                        } else {
                            holder.imageView.setColorFilter(
                                ContextCompat.getColor(
                                    context,
                                    R.color.green
                                )
                            )
                        }
                    }
                    holder.rate.text = it.data?.rate
                    viewModel.getExchangeRate(item.mainQuotation.toString(), item.targetQuotation.toString(), data)
                }
                is DataStatus.Error -> {
                    viewModel.getExchangeRate(item.mainQuotation.toString(), item.targetQuotation.toString(), data)
                }
                else -> {
                    Log.d("###", "### Loading")
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class QuotationViewHolder (itemView: QuotationItemBinding): RecyclerView.ViewHolder(itemView.root){
        val view = itemView.root
        val quotation = itemView.coin
        val rate = itemView.rate
        val imageView = itemView.resultImageView
    }

}