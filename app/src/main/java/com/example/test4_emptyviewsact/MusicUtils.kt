package com.example.test4_emptyviewsact.utils
//package com.xsode.utils

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.widget.ArrayAdapter

object MusicUtils {

fun searchMusicFiles(context: Context): MutableList<String> {
val musicList = mutableListOf<String>()

val cursor: Cursor? = context.contentResolver.query(
MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
null,
null,
null,
MediaStore.Audio.Media.TITLE + " ASC"
)

cursor?.use {
val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
while (it.moveToNext()) {
val title = it.getString(titleColumn)
musicList.add(title)
}
}
return musicList
}

fun displayMusicFiles(context: Context, musicList: MutableList<String>, adapter: ArrayAdapter<String>) {
adapter.clear()
adapter.addAll(searchMusicFiles(context))
adapter.notifyDataSetChanged()
}
}