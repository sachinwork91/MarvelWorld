package com.example.marvelworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_view.view.characterImage
import kotlinx.android.synthetic.main.list_item_view.view.name

class RecycleAdapter constructor(val data: List<Results>, val clickListener: (String, String)-> Unit): RecyclerView.Adapter<RecycleAdapter.CharacterHolder>() {

    data class CharacterHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(result: Results, clickListener: (String, String) -> Unit ) {
            view.name.setText(result.name)
            // we need th replace the marvel API http urls with https else we  get a failure on new version of android
            val url= result.thumbnail.path.replace("http", "https")+"/"+"portrait_incredible."+result.thumbnail.extension
            Picasso.with(view.context).load(url).into(view.characterImage)
            view.characterImage.setOnClickListener {
                clickListener(url, result.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return CharacterHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
       val marvelCharacter = data[position]
        holder.bind(marvelCharacter, clickListener)
    }
}
