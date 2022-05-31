package com.example.android.miwok.fragments

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.media.AudioAttributesCompat
import com.example.android.miwok.R
import com.example.android.miwok.data.entity.Word
import com.example.android.miwok.adapters.WordAdapter


class ColorsFragment : Fragment() {
    private var mMediaPlayer: MediaPlayer? = null
    private lateinit var mAudioManager: AudioManager

    private var focusRequest: AudioFocusRequest? = null
    private var playbackAttributes: AudioAttributesCompat? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private val mOnAudioFocusChangeListener: AudioManager.OnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange: Int ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
            ) {
                mMediaPlayer?.pause();
                mMediaPlayer?.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer?.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    private val mCompletionListener = MediaPlayer.OnCompletionListener {
        releaseMediaPlayer()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.activity_numbers, container, false)

        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager;

        val words: ArrayList<Word> = arrayListOf(
            Word(
                R.string.color_red, R.string.miwok_color_red,
                R.drawable.color_red, R.raw.color_red
            ),
            Word(
                R.string.color_mustard_yellow, R.string.miwok_color_mustard_yellow,
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow
            ),
            Word(
                R.string.color_dusty_yellow, R.string.miwok_color_dusty_yellow,
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow
            ),
            Word(
                R.string.color_green, R.string.miwok_color_green,
                R.drawable.color_green, R.raw.color_green
            ),
            Word(
                R.string.color_brown, R.string.miwok_color_brown,
                R.drawable.color_brown, R.raw.color_brown
            ),
            Word(
                R.string.color_gray, R.string.miwok_color_gray,
                R.drawable.color_gray, R.raw.color_gray
            ),
            Word(
                R.string.color_black, R.string.miwok_color_black,
                R.drawable.color_black, R.raw.color_black
            ),
            Word(
                R.string.color_white, R.string.miwok_color_white,
                R.drawable.color_white, R.raw.color_white
            )
        );

        val itemsAdapter = WordAdapter(requireActivity(), words, R.color.category_colors);

        val listView = rootView.findViewById<ListView>(R.id.list);

        listView.adapter = itemsAdapter;

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            releaseMediaPlayer();
            val word: Word = words.get(i);
            playbackAttributes = AudioAttributesCompat.Builder()
                .setUsage(AudioAttributesCompat.USAGE_MEDIA)
                .setContentType(AudioAttributesCompat.CONTENT_TYPE_SPEECH)
                .build()

            focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setWillPauseWhenDucked(false)
                .setOnAudioFocusChangeListener(mOnAudioFocusChangeListener)
                .build()

            val result = mAudioManager.requestAudioFocus(focusRequest!!)
//
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = MediaPlayer.create(activity, word.getAudioResourceId());

                // Start the audio file
                mMediaPlayer!!.start();


                mMediaPlayer!!.setOnCompletionListener(mCompletionListener);
            }
        }

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        releaseMediaPlayer();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {

            mMediaPlayer!!.release()

            mMediaPlayer = null

            mAudioManager.abandonAudioFocusRequest(focusRequest!!)
        }
    }
}