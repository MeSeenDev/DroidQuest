package ru.meseen.droidquest.fragment

import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.meseen.droidquest.R
import ru.meseen.droidquest.data.Quests


class CheatBottomSheet : BottomSheetDialogFragment() {

    private lateinit var expandImage: ImageView
    private lateinit var answer: MaterialTextView

    private lateinit var listener: Peep


    companion object {
        private const val QUEST_KEY = "quest_get_key"

        fun getInstance(quests: Quests): CheatBottomSheet {
            val fragment = CheatBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable(QUEST_KEY, quests)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.mBottomSheetDialogTheme)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.cheat_dialog, container, false)

        val bottomNavigationView: CardView = v.findViewById(R.id.bottom_sheet)
        val bottomSheetBehavior = from(bottomNavigationView)
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallBacks)
        bottomSheetBehavior.state = STATE_HALF_EXPANDED
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView: MaterialCardView = view.findViewById(R.id.bottom_sheet)
        expandImage = view.findViewById(R.id.expandImage)
        answer = view.findViewById(R.id.descriptionBottomSheetText)
        bottomNavigationView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        val layoutParams = bottomNavigationView.layoutParams
        val windowHeight: Int =
            requireContext().resources.displayMetrics.heightPixels
        layoutParams.height = windowHeight
        bottomNavigationView.layoutParams = layoutParams
    }

    private val bottomSheetCallBacks = object : MyBottomSheetBehavior() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (resources.configuration.orientation == ORIENTATION_PORTRAIT) {
                if (newState == STATE_EXPANDED) {
                    showAnswer()
                }
            } else {
                if (newState == STATE_COLLAPSED || newState == STATE_EXPANDED) {
                    showAnswer()
                }
            }
            Log.d("isPeeped", "onStateChanged: $newState")
        }
    }

    private fun showAnswer() {
        answer.animate().alpha(1.0f).duration = 500
        answer.text = arguments?.getParcelable<Quests>(QUEST_KEY)?.comment
        listener.isPeeped(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Peep) {
            listener = context
        }
    }
}

interface Peep {
    fun isPeeped(boolean: Boolean = false)
}


