package ru.meseen.droidquest

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ru.meseen.droidquest.data.Quests
import ru.meseen.droidquest.fragment.CheatBottomSheet
import ru.meseen.droidquest.fragment.Peep
import ru.meseen.droidquest.viewmodel.MainViewModel
import ru.meseen.droidquest.viewmodel.MainViewModelFactory
import ru.meseen.droidquest.viewpager2.ViewPagerAdapter
import java.util.*

private lateinit var mTrueButton: MaterialButton
private lateinit var mFalseButton: MaterialButton

private lateinit var mNextButton: MaterialButton
private lateinit var mPreviousButton: MaterialButton

private lateinit var fab: FloatingActionButton

private lateinit var vpAdapter: ViewPagerAdapter
private lateinit var viewPager2: ViewPager2

class MainActivity : AppCompatActivity(), Peep {


    private lateinit var factory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { factory }

    private lateinit var quests: List<Quests>
    private lateinit var answers: Map<String, Boolean>

    private var curPosition: Int = 0
    private var curQuest = Quests()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        factory = MainViewModelFactory(application)

        initViews()
        vpAdapter = ViewPagerAdapter(this)
        viewPager2.adapter = vpAdapter
        viewModel.getQuestList().observe(this, {
            quests = it
            vpAdapter.setData(it)
        })
        viewModel.getAnswersMap().observe(this, {
            answers = it
        })

        mTrueButton.setOnClickListener(trueListener)
        mFalseButton.setOnClickListener(falseListener)
        mNextButton.setOnClickListener(nextListener)
        mPreviousButton.setOnClickListener(previousListener)

        fab.setOnClickListener(fabListener)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                curPosition = position
                curQuest = quests[position]
                fabCustomize()
            }
        })

    }

    /**
     *  Кастомизирует FAB под устовия ответа пользователя
     */
    private fun fabCustomize() {
        var src = R.drawable.ic_information_variant
        var color = R.color.colorPrimaryVariant
        if (isPeep()) {
            if (answers[curQuest.quest] == curQuest.answer) {
                src = R.drawable.ic_round_check_24
                color = R.color.green
            } else {
                src = R.drawable.ic_round_close_24
                color = R.color.red
            }
        }
        fab.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                this@MainActivity,
                color
            )
        )
        fab.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                src
            )
        )
    }


    private fun initViews() {
        mTrueButton = findViewById(R.id.trueButton)
        mFalseButton = findViewById(R.id.falseButton)

        viewPager2 = findViewById(R.id.viewPager2)

        mNextButton = findViewById(R.id.nextButton)
        mPreviousButton = findViewById(R.id.previousButton)

        fab = findViewById(R.id.fab)
    }


    private val trueListener = View.OnClickListener {
        answerChecker(it, true)
        fabCustomize()
    }

    private val falseListener = View.OnClickListener {
        answerChecker(it, false)
        fabCustomize()
    }

    /**
     * @param it вью для показа SnackBar
     * @param userAnswer ответ пользователя для проверки
     */
    private fun answerChecker(it: View, userAnswer: Boolean) {
        if (!isPeep()) {
            showSnack(curQuest.answer == userAnswer, it)
            viewModel.answerInsert(curQuest, userAnswer)
        } else {
            Snackbar.make(it, getString(R.string.already_answered), Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.colorSecondary)).show()
        }
    }

    /**
     * @param answer ответ пользователя
     * @param it View для показа SnackBar
     */
    private fun showSnack(answer: Boolean, it: View) {
        val snackBar = Snackbar.make(it, "text", Snackbar.LENGTH_SHORT)
        val answerText =
            if (answer) getString(R.string.answer_correct)
                .toUpperCase(Locale.ROOT)
            else getString(
                R.string.answer_incorrect
            ).toUpperCase(Locale.ROOT)
        val answerColor = if (answer) R.color.green else R.color.red
        snackBar.setBackgroundTint(ContextCompat.getColor(this, answerColor))
        snackBar.setText(answerText).show()
    }


    private val nextListener = View.OnClickListener {
        changePosition(Step.NEXT)
    }
    private val previousListener = View.OnClickListener {
        changePosition(Step.PREVIOUS)
    }

    private val fabListener = View.OnClickListener {
        val redactorBottomSheet = CheatBottomSheet.getInstance(curQuest)
        redactorBottomSheet.show(supportFragmentManager, "bottom_sheet")
    }

    /**
     * @param step принимает перечисление NEXT или PREVIOUS
     */
    private fun changePosition(step: Step) {
        val curItem = viewPager2.currentItem
        if (step == Step.NEXT) {
            viewPager2.currentItem = curItem + 1
        } else {
            viewPager2.currentItem = if ((curItem - 1) < 1) 0 else curItem - 1
        }
    }


    /**
     * @return Boolean есть ли ответ на текущий Quest ?
     */
    private fun isPeep(): Boolean {
        return answers.containsKey(curQuest.quest)
    }


    /**
     * @param  boolean если пользователь подглядывал ответ приходит true
     */
    override fun isPeeped(boolean: Boolean) {
        if (boolean) {
            if (!isPeep()) {
                viewModel.answerInsert(curQuest, !curQuest.answer)
                Log.d("TAG", "isPeeped: $boolean")
                fabCustomize()
            }
        }
    }

}

enum class Step {
    NEXT, PREVIOUS
}
