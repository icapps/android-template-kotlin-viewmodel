package com.icapps.template.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.icapps.architecture.annotation.AndroidInjected
import com.icapps.architecture.utils.ext.bindContentView
import com.icapps.architecture.utils.ext.observe
import com.icapps.template.R
import com.icapps.template.databinding.ActivityMainBinding
import com.icapps.template.viewmodel.ExampleViewModel

@AndroidInjected
class MainActivity : BaseActivity() {

    private lateinit var viewModel: ExampleViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getOrCreateViewModel(savedInstanceState)

        binding = R.layout.activity_main.bindContentView(this)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel.init(lifecycle, resourcesWrapper)
        viewModel.examples.observe(lifecycle) {
            if (it == null) return@observe
            Snackbar.make(binding.root, "Loaded examples: $it", Snackbar.LENGTH_LONG).show()
        }
    }

}
