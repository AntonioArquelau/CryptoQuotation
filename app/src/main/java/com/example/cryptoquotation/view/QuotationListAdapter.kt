package com.example.cryptoquotation.view

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoquotation.databinding.QuotationItemBinding
import com.example.cryptoquotation.repository.data.Bitcoin
import com.example.cryptoquotation.repository.data.DataStatus
import com.example.cryptoquotation.viewmodel.MainViewModel
import java.lang.Appendable
import kotlin.coroutines.coroutineContext

class QuotationListAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<QuotationListAdapter.QuotationViewHolder>() {

    private val  itemList = mutableListOf<QuotationItem>()


    fun setData(item:  QuotationItem){
        itemList.add(item)
        Log.d("###", "#### setData")
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
        Log.d("###", "#### onBindViewHolder")
        val item = itemList[position]
        holder.quotation.text = "${item.mainQuotation}/${item.targetQuotation}"
        holder.rate.text = item.rate
        val data = MutableLiveData<DataStatus<Bitcoin>>()
        viewModel.getExchangeRate(item.mainQuotation.toString(), item.targetQuotation.toString(), data)

        data.observeForever {
            when (it) {
                is DataStatus.Success -> {
                    holder.rate.text = it.data?.rate
                    viewModel.getExchangeRate(item.mainQuotation.toString(), item.targetQuotation.toString(), data)
                }
                is DataStatus.Error -> {
                    Log.e("###", "###" + it.toString())
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
    }

}