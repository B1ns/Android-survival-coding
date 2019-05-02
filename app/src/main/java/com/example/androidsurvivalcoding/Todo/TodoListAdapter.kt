package com.example.androidsurvivalcoding.Todo

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidsurvivalcoding.R
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class TodoListAdapter(realmResults: OrderedRealmCollection<Todo>) : RealmBaseAdapter<Todo>(realmResults) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val view: View
        val vh: ViewHolder


        if (convertView == null){
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_todo, parent, false)

            vh = ViewHolder(view)
            view.tag = vh
        }else{
            view = convertView
            vh = view.tag as ViewHolder
        }

        if (adapterData != null){
            val item = adapterData!![position]
            vh.textTextView.text = item.title
            vh.dataTextView.text = DateFormat.format("yyyy/MM/dd", item.date)
        }
        return view
    }

    override fun getItemId(position: Int): Long {
        if (adapterData != null){
            return adapterData!![position].id
        }

        return super.getItemId(position)
    }
}

class ViewHolder(view: View){
    var dataTextView: TextView = view.findViewById(R.id.text1)
    var textTextView: TextView = view.findViewById(R.id.text2)
}