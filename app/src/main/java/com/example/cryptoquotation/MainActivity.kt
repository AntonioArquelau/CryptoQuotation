package com.example.cryptoquotation

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cryptoquotation.databinding.ActivityMainBinding
import com.example.cryptoquotation.databinding.DialogBinding
import com.example.cryptoquotation.view.QuotationItem
import com.example.cryptoquotation.view.QuotationListAdapter
import com.example.cryptoquotation.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dialogBinding: DialogBinding by lazy {
        DialogBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        MainViewModel()
    }

    private val dialog by lazy {
        Dialog(this, R.style.AlertDialogCustom)
    }
    private val mainListAdapter by lazy {
        QuotationListAdapter(viewModel, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupSpinnerAdapter()
        setupDialog()
        binding.floatingButton.setOnClickListener {
            showDialog("Select a currency")
        }
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = mainListAdapter
    }

    private fun setupSpinnerAdapter(){

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            R.layout.spinner_item
        ).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dialogBinding.spinnerMain.adapter = adapter
            dialogBinding.spinnerTarget.adapter = adapter
        }
    }

    private fun setupDialog(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
    }

    private fun showDialog(title: String) {
        dialogBinding.tvTitle.text = title

        dialogBinding.btnAdd.setOnClickListener {
            dialog.dismiss()
            val item = QuotationItem(
                dialogBinding.spinnerMain.selectedItem.toString(),
                dialogBinding.spinnerTarget.selectedItem.toString(),
                ""
            )
            mainListAdapter.setData(item)
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}