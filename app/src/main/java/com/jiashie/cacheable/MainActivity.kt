package com.jiashie.cacheable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jiashie.cacheable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val cacheable: Cacheable<String> by lazy {
        object : Cacheable<String>() {
            override fun fetchData(): String {
                Thread.sleep(5000)
                return System.currentTimeMillis().toString()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch1.setOnClickListener { fetch("fetch1") }
        binding.btnFetch2.setOnClickListener { fetch("fetch2")}
        binding.btnFetch3.setOnClickListener { fetch("fetch3")}
        binding.btnGet.setOnClickListener { get()}

        binding.btnInvalidate.setOnClickListener { cacheable.invalidate() }
    }

    private fun fetch(caller: String) {
        cacheable.fetch(object : Cacheable.MainDataCallback<String>() {
            override fun onNext(e: String?) {
                println("$caller onNext $e")
            }

            override fun onError(t: Throwable?) {
                println("$caller onError ${t?.message}")
            }
        })
    }

    private fun get(){
        println("get ${cacheable.get()}")
    }

}