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
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.media.AudioAttributesCompat
import com.example.android.miwok.R
import com.example.android.miwok.Word
import com.example.android.miwok.WordAdapter


class FamilyFrament : Fragment() {
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

        val words : ArrayList<Word> = arrayListOf(
            Word(R.string.family_father, R.string.miwok_family_father,
                R.drawable.family_father, R.raw.family_father),
            Word(R.string.family_mother, R.string.miwok_family_mother,
                R.drawable.family_mother, R.raw.family_mother),
            Word(R.string.family_son, R.string.miwok_family_son,
                R.drawable.family_son, R.raw.family_son),
            Word(R.string.family_daughter, R.string.miwok_family_daughter,
                R.drawable.family_daughter, R.raw.family_daughter),
            Word(R.string.family_older_brother, R.string.miwok_family_older_brother,
                R.drawable.family_older_brother, R.raw.family_older_brother),
            Word(R.string.family_younger_brother, R.string.miwok_family_younger_brother,
                R.drawable.family_younger_brother, R.raw.family_younger_brother),
            Word(R.string.family_older_sister, R.string.miwok_family_older_sister,
                R.drawable.family_older_sister, R.raw.family_older_sister),
            Word(R.string.family_younger_sister, R.string.miwok_family_younger_sister,
                R.drawable.family_younger_sister, R.raw.family_younger_sister),
            Word(R.string.family_grandmother, R.string.miwok_family_grandmother,
                R.drawable.family_grandmother, R.raw.family_grandmother),
            Word(R.string.family_grandfather, R.string.miwok_family_grandfather,
                R.drawable.family_grandfather, R.raw.family_grandfather)
        );

        val itemsAdapter = WordAdapter(requireActivity(), words, R.color.category_family);

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