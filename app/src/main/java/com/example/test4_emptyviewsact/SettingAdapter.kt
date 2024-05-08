package com.example.test4_emptyviewsact
// package com.xsode
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class SettingAdapter(context: Context, settings: List<String>) : ArrayAdapter<String>(context, R.layout.list_item_setting, settings) {

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
	val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
	val listItemView = convertView ?: inflater.inflate(R.layout.list_item_setting, parent, false)

	// Get references to views in list_item_setting.xml
	val textViewSetting: TextView = listItemView.findViewById(R.id.textViewSetting)
	val switchSetting: Switch = listItemView.findViewById(R.id.switchSetting)

	// Set data for each item in the ListView
	textViewSetting.text = getItem(position)

	// Set an OnClickListener for the Switch
	switchSetting.setOnClickListener {
		// Handle the click event here
		val isChecked = switchSetting.isChecked
		if(isChecked){
			GlobalVariables.useLS = true
		}else{
			GlobalVariables.useLS = false
		}
		val message = if (isChecked) {
			"Switch is checked"
		} else {
			"Switch is unchecked"
		}
			// Show a Toast message
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
		}
		return listItemView
	}
}