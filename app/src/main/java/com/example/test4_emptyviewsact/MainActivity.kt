package com.example.test4_emptyviewsact

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: ArrayAdapter<String>
    private lateinit var videoAdapter: ArrayAdapter<String>
    private val MUSIC_PERMISSION_CODE = 123
    private var mediaPlayer: MediaPlayer? = null
    private var currentFilePath: String? = null // Add this line
    private var controlf: String? = null
    private val musicFilePaths = mutableListOf<String>()
    //private val videoFilePaths = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val settingsListView: ListView = findViewById(R.id.settings_list_view)
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_audio_player -> {
                    val musicListView: ListView = findViewById(R.id.music_list_view)
                    musicListView.visibility = View.VISIBLE
                    val videoListView: ListView = findViewById(R.id.video_list_view)
                    videoListView.visibility = View.GONE

                    settingsListView.visibility = View.GONE
                    val aboutListView: ListView = findViewById(R.id.about_list_view)
                    aboutListView.visibility = View.GONE
                    val adminListView: ListView = findViewById(R.id.admin_list_view)
                    adminListView.visibility = View.GONE
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.settings -> {
                    val musicListView: ListView = findViewById(R.id.music_list_view)
                    musicListView.visibility = View.GONE
                    val videoListView: ListView = findViewById(R.id.video_list_view)
                    videoListView.visibility = View.GONE
                    val aboutListView: ListView = findViewById(R.id.about_list_view)
                    aboutListView.visibility = View.GONE
                    val adminListView: ListView = findViewById(R.id.admin_list_view)
                    adminListView.visibility = View.GONE
                    //val settingsListView: ListView = findViewById(R.id.settings_list_view)
                    settingsListView.visibility = View.VISIBLE
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_video_player -> {
                    val musicListView: ListView = findViewById(R.id.music_list_view)
                    musicListView.visibility = View.GONE
                    val videoListView: ListView = findViewById(R.id.video_list_view)
                    videoListView.visibility = View.VISIBLE
                    //val settingsListView: ListView = findViewById(R.id.settings_list_view)
                    settingsListView.visibility = View.GONE
                    val aboutListView: ListView = findViewById(R.id.about_list_view)
                    aboutListView.visibility = View.GONE
                    val adminListView: ListView = findViewById(R.id.admin_list_view)
                    adminListView.visibility = View.GONE
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.about -> {
                    val musicListView: ListView = findViewById(R.id.music_list_view)
                    musicListView.visibility = View.GONE
                    val videoListView: ListView = findViewById(R.id.video_list_view)
                    videoListView.visibility = View.GONE
                    //val settingsListView: ListView = findViewById(R.id.settings_list_view)
                    settingsListView.visibility = View.GONE
                    val adminListView: ListView = findViewById(R.id.admin_list_view)
                    adminListView.visibility = View.GONE
                    val aboutListView: ListView = findViewById(R.id.about_list_view)
                    aboutListView.visibility = View.VISIBLE
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.admin -> {
                    val musicListView: ListView = findViewById(R.id.music_list_view)
                    musicListView.visibility = View.GONE
                    val videoListView: ListView = findViewById(R.id.video_list_view)
                    videoListView.visibility = View.GONE
                    //val settingsListView: ListView = findViewById(R.id.settings_list_view)
                    settingsListView.visibility = View.GONE
                    val aboutListView: ListView = findViewById(R.id.about_list_view)
                    aboutListView.visibility = View.GONE
                    val adminListView: ListView = findViewById(R.id.admin_list_view)
                    adminListView.visibility = View.VISIBLE
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
        //val btnToggleVal = findViewById<ImageButton>(R.id.btn_toggle)
        /*btnToggleVal.setOnClickListener {
            // This code will be executed when the button is clicked
            searchAndDisplayMusicFiles()
            Toast.makeText(this, "5/8/2024Button clicked! $musicFilePaths", Toast.LENGTH_SHORT).show()
            drawerLayout.open()
            //drawerLayout.openDrawer(GravityCompat.END)
        }*/
        val settings = listOf("Use Loudspeaker")
        val settingAdapter = SettingAdapter(this, settings)
       // val listView = findViewById<ListView>(R.id.settings_list_view) // Adjust to your actual ListView ID
        settingsListView.adapter = settingAdapter
        val musicListView: ListView = findViewById(R.id.music_list_view)
        val videoListView: ListView = findViewById(R.id.video_list_view)
        musicAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        musicListView.adapter = musicAdapter
        videoAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        videoListView.adapter = videoAdapter
        val aboutListView: ListView = findViewById(R.id.about_list_view)
        val aboutAdapter = AboutPageAdapter(this, settings)
        aboutListView.adapter = aboutAdapter
        if (checkPermission()) {
            searchAndDisplayMusicFiles()
        } else {
            requestPermission()
        }
        musicListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedMusicPath = musicFilePaths[position]
            selectedMusicPath?.let { playMusic(it) }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
    //START INSERTION
    private fun updateAudioAttributes(isLoudspeaker: Boolean) {
        mediaPlayer?.setAudioAttributes(
            if (isLoudspeaker) {
                getMediaAudioAttributes()
            } else {
                getVoiceCommunicationAudioAttributes()
            }
        )
    }
    //Needs to be able to update playing music when speaker setting is changed
    private fun updatePlayback(isLoudspeaker: Boolean) {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                updateAudioAttributes(isLoudspeaker)
                player.start()
            }
        }
    }
    private fun getVoiceCommunicationAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
            .build()
    }
    private fun getMediaAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    }
    //END INSERTION
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            MUSIC_PERMISSION_CODE
        )
    }
    private fun searchAndDisplayMusicFiles() {
        val musicList = mutableListOf<String>()
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Audio.Media.TITLE + " ASC"
        )
        cursor?.use {
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            while (it.moveToNext()) {
                val filePath = it.getString(dataColumn)
                musicFilePaths.add(filePath)
                val title = it.getString(titleColumn)
                musicList.add(title)
            }
        }
        musicAdapter.addAll(musicList)
    }
    private fun searchAndDisplayVideoFiles() {
        // Similar logic as searchAndDisplayMusicFiles() to query and display video file
        /*val videoList = mutableListOf<String>()
        val cursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Video.Media.TITLE + " ASC"
        )
        cursor?.use {
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            while (it.moveToNext()) {
                val filePath = it.getString(dataColumn)
                videoFilePaths.add(filePath)
                val title = it.getString(titleColumn)
                videoList.add(title)
            }
        }
        videoAdapter.addAll(videoList)*/
    }
    private fun playMusic(filePath: String) {
        if (mediaPlayer != null) {
            if (currentFilePath == filePath && controlf == null) {
                // Same song is playing, pause the MediaPlayer
                pauseMusic()
                controlf = "paused"
            } else if (controlf == "paused" && currentFilePath == filePath) {
                // Resume paused music
                resumeMusic()
                controlf = null
            } else {
                // Different song is selected or resuming a different paused song
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                currentFilePath = filePath
                startMediaPlayer(currentFilePath!!)
                controlf = null
            }
        } else {
            // No song is currently playing, start playing the selected song
            currentFilePath = filePath
            startMediaPlayer(currentFilePath!!)
            controlf = null
        }
    }
    private fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }
    private fun resumeMusic() {
        if(GlobalVariables.useLS){
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .build()
            )
        }
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }
    private fun startMediaPlayer(filePath: String) {
        mediaPlayer = MediaPlayer()
        if(GlobalVariables.useLS){
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .build()
            )
        }
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(filePath)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }
    private fun playVideo(kotselectedVideo: String) {
        /*
            // Construct URI for the selected video file
            val file = File(selectedVideo)
            val uri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)

            // Create intent to play the video file
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "video")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        */
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MUSIC_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                searchAndDisplayMusicFiles()
                // Add method call to search and display video files
                // searchAndDisplayVideoFiles()
            } else {
                // Handle permission denied
            }
        }
    }
}