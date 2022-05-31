package com.example.android.miwok.adapters


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android.miwok.R
import com.example.android.miwok.data.entity.Word


class WordAdapter(val context: Activity, val words: ArrayList<Word>, val colorResourceId : Int) : BaseAdapter() {


    override fun getCount(): Int {
        return words.size
    }
    override fun getItem(position: Int): Word {
        return words[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val listItemView = convertView ?:
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_custom, parent, false)

        val currentWord: Word = getItem(position)

        val nameTextView = listItemView.findViewById<TextView>(R.id.miwok_text_view)

        nameTextView.setText(currentWord.getMiwokTranslation())

        val numberTextView = listItemView.findViewById<TextView>(R.id.default_text_view)

        numberTextView.setText(currentWord.getDefaultTranslation())

        val imageView = listItemView.findViewById<ImageView>(R.id.imageView)

        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getImageResourceId())

            imageView.visibility = View.VISIBLE
        } else {
            imageView.visibility = View.GONE
        }

        val textContainer = listItemView.findViewById<View>(R.id.text_container)

        val color = ContextCompat.getColor(context, colorResourceId)

        textContainer.setBackgroundColor(color)

        return listItemView
    }

}