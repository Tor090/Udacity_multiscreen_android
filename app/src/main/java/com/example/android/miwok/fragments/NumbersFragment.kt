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


class NumbersFragment : Fragment() {
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
    ): View {
        val rootView: View = inflater.inflate(R.layout.activity_numbers, container, false)

        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager;

        val words : ArrayList<Word> = arrayListOf(
            Word(R.string.number_one, R.string.miwok_number_one,
                R.drawable.number_one, R.raw.number_one),
            Word(R.string.number_two, R.string.miwok_number_two,
                R.drawable.number_two, R.raw.number_two),
            Word(R.string.number_three, R.string.miwok_number_three,
                R.drawable.number_three, R.raw.number_three),
            Word(R.string.number_four, R.string.miwok_number_four,
                R.drawable.number_four, R.raw.number_four),
            Word(R.string.number_five, R.string.miwok_number_five,
                R.drawable.number_five, R.raw.number_five),
            Word(R.string.number_six, R.string.miwok_number_six,
                R.drawable.number_six, R.raw.number_six),
            Word(R.string.number_seven, R.string.miwok_number_seven,
                R.drawable.number_seven, R.raw.number_seven),
            Word(R.string.number_eight, R.string.miwok_number_eight,
                R.drawable.number_eight, R.raw.number_eight),
            Word(R.string.number_nine, R.string.miwok_number_nine,
                R.drawable.number_nine, R.raw.number_nine),
            Word(R.string.number_ten, R.string.miwok_number_ten,
                R.drawable.number_ten, R.raw.number_ten)
        );

        val itemsAdapter = WordAdapter(requireActivity(), words, R.color.category_numbers);

        val listView = rootView.findViewById<ListView>(R.id.list);

        listView.adapter = itemsAdapter;

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            releaseMediaPlayer();
            val word: Word = words[i];
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