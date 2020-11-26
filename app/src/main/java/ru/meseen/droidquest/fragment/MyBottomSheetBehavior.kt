package ru.meseen.droidquest.fragment

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior


/**
 * Сoздан чтобы не переопределять onSlide в CheatBottomSheet
 */
abstract class MyBottomSheetBehavior : BottomSheetBehavior.BottomSheetCallback(){
    override fun onStateChanged(bottomSheet: View, newState: Int) {

    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {

    }

}