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


class PhrasesFragment : Fragment() {
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
            Word(R.string.phrase_where_are_you_going,
                R.string.miwok_phrase_where_are_you_going, R.raw.phrase_where_are_you_going),
            Word(R.string.phrase_what_is_your_name,
                R.string.miwok_phrase_what_is_your_name, R.raw.phrase_what_is_your_name),
            Word(R.string.phrase_my_name_is,
                R.string.miwok_phrase_my_name_is, R.raw.phrase_my_name_is),
            Word(R.string.phrase_how_are_you_feeling,
                R.string.miwok_phrase_how_are_you_feeling, R.raw.phrase_how_are_you_feeling),
            Word(R.string.phrase_im_feeling_good,
                R.string.miwok_phrase_im_feeling_good, R.raw.phrase_im_feeling_good),
            Word(R.string.phrase_are_you_coming,
                R.string.miwok_phrase_are_you_coming, R.raw.phrase_are_you_coming),
            Word(R.string.phrase_yes_im_coming,
                R.string.miwok_phrase_yes_im_coming, R.raw.phrase_yes_im_coming),
            Word(R.string.phrase_im_coming,
                R.string.miwok_phrase_im_coming, R.raw.phrase_im_coming),
            Word(R.string.phrase_lets_go,
                R.string.miwok_phrase_lets_go, R.raw.phrase_lets_go),
            Word(R.string.phrase_come_here,
                R.string.miwok_phrase_come_here, R.raw.phrase_come_here)
        );

        val itemsAdapter = WordAdapter(requireActivity(), words, R.color.category_phrases);

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