package com.pishi.ziki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pishi.ziki.Music
import com.pishi.ziki.R
import com.pishi.ziki.itemClicked

class AllMusicAdapter(private var musicList: MutableList <Music>, private var itemClicked: itemClicked):
        RecyclerView.Adapter<AllMusicAdapter.AllMusicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMusicViewHolder {

        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.card_view_music_item, parent, false)

        return AllMusicViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: AllMusicViewHolder, position: Int) {

        val item = musicList[position]
        holder.bindMusic(item)

    }

    override fun getItemCount(): Int {

        return musicList.size
    }

    inner class AllMusicViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private var view : View = itemView
        private lateinit var music : Music
        private var  songName : TextView = itemView.findViewById(R.id.song_name)
        private var artistName : TextView = itemView.findViewById(R.id.artist_name)

        init {
            view.setOnClickListener(this)
        }

        fun bindMusic(music : Music){

            this.music = music
            songName.text = music.musicName
            artistName.text = music.artistName
        }

        override fun onClick(v: View?) {

            itemClicked.itemClicked(adapterPosition)
        }

    }

}