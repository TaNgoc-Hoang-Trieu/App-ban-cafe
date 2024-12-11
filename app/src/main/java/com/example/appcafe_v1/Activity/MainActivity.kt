package com.example.appcafe_v1.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcafe_v1.Adapter.CategoryAdapter
import com.example.appcafe_v1.Adapter.OfferAdapter
import com.example.appcafe_v1.Adapter.PopularAdapter
import com.example.appcafe_v1.ProfileActivity
import com.example.appcafe_v1.R
import com.example.appcafe_v1.ViewModel.MainViewModel
import com.example.appcafe_v1.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategory()
        initPopular()
        initOffer()
        bottomMenu()
        binding.profile.setOnClickListener{
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }

    private fun bottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

    }

    private fun initOffer() {
        binding.progressBarOffer.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer {

            binding.recyclerViewOffer.layoutManager =LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false)
            binding.recyclerViewOffer.adapter= OfferAdapter(it)
            binding.progressBarOffer.visibility = View.GONE
        })
        viewModel.loadOffer()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility= View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.recyclerViewPopular.layoutManager =LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false)
            binding.recyclerViewPopular.adapter= PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility= View.VISIBLE
        viewModel.category.observe(this, Observer {
            binding.recyclerViewCategory.layoutManager =LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false)
            binding.recyclerViewCategory.adapter=CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }
}