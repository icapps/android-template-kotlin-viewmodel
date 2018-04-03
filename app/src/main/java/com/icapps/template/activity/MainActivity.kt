package com.icapps.template.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.icapps.template.R
import com.icapps.template.databinding.ActivityMainBinding
import com.icapps.template.model.Example
import com.icapps.template.model.arch.Resource
import com.icapps.template.model.arch.Status
import com.icapps.template.util.ext.bindContentView
import com.icapps.template.viewmodel.ExampleViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: ExampleViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = R.layout.activity_main.bindContentView(this)

        viewModel = getOrCreateViewModel(savedInstanceState)
        viewModel.init()
        viewModel.examples.observe(this, Observer<Resource<List<Example>>> {
            if (it == null) return@Observer

            when (it.status) {
                Status.ERROR -> {
                    binding.loadingIndicator.visibility = View.GONE
                    Snackbar.make(findViewById(android.R.id.content), it.message ?: "Something went wrong", Snackbar.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.loadingIndicator.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingIndicator.visibility = View.GONE
                    Snackbar.make(findViewById(android.R.id.content), "Loaded examples: ${it.data}", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

}
