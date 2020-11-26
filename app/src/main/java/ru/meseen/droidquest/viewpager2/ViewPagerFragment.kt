package ru.meseen.droidquest.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import ru.meseen.droidquest.DataCodes
import ru.meseen.droidquest.R
import ru.meseen.droidquest.data.Quests

class ViewPagerFragment : Fragment() {

    private lateinit var data: Bundle

    companion object {
        fun getInstance(quests: Quests): ViewPagerFragment {
            val bundle = Bundle()
            bundle.putParcelable(DataCodes.PARSABLE_KEY.toString(), quests)
            val fragment = ViewPagerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        data = arguments ?: Bundle.EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val qTextView = view.findViewById<MaterialTextView>(R.id.questTest)
        val qImageView = view.findViewById<ImageView>(R.id.imageView)

        if (!data.isEmpty) {
            val quest: Quests? = data.getParcelable(DataCodes.PARSABLE_KEY.toString())
            qTextView.text = quest?.quest
            quest?.drawable?.let { qImageView.setImageResource(it) }
        }
    }
}