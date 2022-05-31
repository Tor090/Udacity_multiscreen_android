package com.example.android.miwok.data.entity

data class Word(
    private val mDefaultTranslation: Int,
    private val mMivokTranslation: Int,
    private val mAudioResourceId: Int
) {

    private var mImageResourceId : Int = -1

    constructor(
        mDefaultTranslation: Int,
        mMivokTranslation: Int, _mImageResoursceID: Int, mAudioResourceId: Int) :
            this(mDefaultTranslation, mMivokTranslation, mAudioResourceId) {
        mImageResourceId = _mImageResoursceID;
    }

    fun getMiwokTranslation() : Int = mMivokTranslation;
    fun getDefaultTranslation() : Int = mDefaultTranslation;
    fun getImageResourceId() : Int = mImageResourceId;
    fun getAudioResourceId() : Int = mAudioResourceId;

    fun hasImage() : Boolean = mImageResourceId != -1;
}
