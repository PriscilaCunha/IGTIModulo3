package com.igti.igtimodulo3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.igti.igtimodulo3.adapters.RecyclerAdapter
import com.igti.igtimodulo3.models.Blog
import com.igti.igtimodulo3.viewModels.MainViewModel
import com.igti.igtimodulo3.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainRecycler: RecyclerView
    private lateinit var but: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecycler = findViewById(R.id.recycler)
        val application = requireNotNull(this).application
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        but = findViewById(R.id.button)
        but.setOnClickListener {
            addData()
        }

        initialiseAdapter()
    }

    private fun initialiseAdapter() {
        mainRecycler.layoutManager = viewManager
        observeData()
    }

    private fun observeData() {
        viewModel.lst.observe(this, Observer {
            Log.i("data", it.toString())
            mainRecycler.adapter = RecyclerAdapter(viewModel, it, this)
        })
    }

    private fun addData() {
        var txtPlce = findViewById<EditText>(R.id.titleTxt)
        var title = txtPlce.text.toString()


        if (title.isNullOrBlank()) {
            Toast.makeText(this, "Entre com o valor!", Toast.LENGTH_LONG).show()
        } else {
            var blog = Blog(title)
            viewModel.add(blog)
            txtPlce.text.clear()
            mainRecycler.adapter?.notifyDataSetChanged()
        }
    }
}
