package com.example.marvelworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelworld.popoutimage.ImagePopOutActivity
import kotlinx.android.synthetic.main.activity_main.recyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecycleAdapter
    private var photosList: ArrayList<Results> = ArrayList()

    companion object {
        val BASE_URL = "https://gateway.marvel.com/v1/public/"
        val API_KEY = "" // Add your key
        val HASH = ""   //  read here about generrating hash https://developer.marvel.com/documentation/authorization
         // the way to generate md5(ts+privateKey+publicKey) , use https://www.md5hashgenerator.com/
        val TIME_STAMP = "1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecycleAdapter(photosList,
            { url: String, name:String ->
            imageClickListener(url, name)
        }
        )
        recyclerView.adapter = adapter
        linearLayoutManager = LinearLayoutManager(this)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager // TODO: try changing the layout manager to linearLayout

        makeNetworkCall()

    }
    fun dataRecieved(result: List<Results>) {
      // Some of the results don't have images, so best is to filter them
        val filteredResult = result.filter {
            !it.thumbnail.path.contains("image_not_available")
        }
        photosList.addAll(filteredResult)
        adapter.notifyDataSetChanged()
    }
    private fun imageClickListener(url: String, name: String) {
        Toast.makeText(this, resources.getString(R.string.implement_image_onclick_message), Toast.LENGTH_SHORT).show()
        // TODO: 1 Just uncomment the below code to launch the ImagePopOutActivity
        // val intent = Intent(this, ImagePopOutActivity::class.java)
        // intent.putExtra(ImagePopOutActivity.URL, url)
        // intent.putExtra(ImagePopOutActivity.NAME, name)
        // startActivity(intent)
    }

    private fun makeNetworkCall() {
        val builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())


        val retrofit = builder.build()
        val client = retrofit.create(MarvelEndPoint::class.java)
        val call = client.findCharacters(
            apiKey = API_KEY,
            ts = TIME_STAMP, // this is can anytime stamp , keep it 1 for ease of use
            hash = HASH,
            offset = "0",
            limit = "20"
        )

        call.enqueue(object : Callback<MyResponse> {
            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MyResponse>,
                response: Response<MyResponse>
            ) {
                response?.let {
                    it.body()
                }

                runOnUiThread {
                    response.body()?.data?.results?.let {
                        dataRecieved(it)
                    }
                }
            }
        })
    }
}
