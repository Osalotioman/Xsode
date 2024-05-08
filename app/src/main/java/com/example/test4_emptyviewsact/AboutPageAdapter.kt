package com.example.test4_emptyviewsact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class AboutPageAdapter(context: Context, settings: List<String>) : ArrayAdapter<String>(context, R.layout.about_page, settings) {

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val listItemView = convertView ?: inflater.inflate(R.layout.about_page, parent, false)
		// Get references to views in list_item_setting.xml
		//val textViewSetting: TextView = listItemView.findViewById(R.id.textViewSetting)
		//val switchSetting: Switch = listItemView.findViewById(R.id.switchSetting)
		// Set data for each item in the ListView
		//textViewSetting.text = getItem(position)
		return listItemView
	}
}