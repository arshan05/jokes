package com.example.jokes

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jokes.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        fetchRandomJoke()

        binding.fetchAnotherJoke.setOnClickListener{
            fetchRandomJoke()
        }

        binding.topAppBar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.favorite -> {
                    true
                }
                R.id.search -> {
                    var layoutTransition = binding.searchBar.layoutTransition
                    layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
//                    layoutTransition.enableTransitionType(LayoutTransition.DISAPPEARING)
                    if (binding.searchBar.visibility == View.VISIBLE){
                        binding.searchBar.visibility = View.INVISIBLE
                    }
                    else{
                        binding.searchBar.visibility = View.VISIBLE
                    }
                    true
                }
                else ->
                    false
            }
        }

    }

    private fun fetchRandomJoke() {
        val destinationService = ServiceBuilder.buildService(RetrofitAPI::class.java)
        val requestCall = destinationService.getJoke()
        requestCall.enqueue(object :Callback<Joke>{
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val dataResponse = response.body()!!
                    binding.joke = dataResponse.joke
                }
                else{
                    binding.joke = "not successful"
                }
            }

            override fun onFailure(call: Call<Joke>, t: Throwable) {
                Log.d("Response", "onResponse: failed")
            }

        })
    }
}