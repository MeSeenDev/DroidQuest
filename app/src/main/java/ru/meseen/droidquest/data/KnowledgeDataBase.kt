package ru.meseen.droidquest.data

import android.app.Application
import ru.meseen.droidquest.R

/**
 * Симуляция некого хранилища даных , все можно заменить на Room
 * и LiveData.
 */

class KnowledgeDataBase private constructor(application: Application) {

    private var questsData: MutableList<Quests> = mutableListOf()
    private var answersData: MutableMap<String, Boolean> = mutableMapOf()

    init {
        questsData.addAll(
            listOf(
                Quests(
                    quest = application.resources.getString(R.string.question_android),
                    answer = true,
                    comment = application.resources.getString(R.string.comment_android),
                    drawable = R.drawable.ic_linux
                ),
                Quests(
                    quest = application.resources.getString(R.string.question_manifest),
                    answer = true,
                    comment = application.resources.getString(R.string.comment_manifest),
                    drawable = R.drawable.ic_clipboard_text_outline
                ),
                Quests(
                    quest = application.resources.getString(R.string.question_res),
                    answer = true,
                    comment = application.resources.getString(R.string.comment_res),
                    drawable = R.drawable.ic_folder_zip_outline

                ),
                Quests(
                    quest = application.resources.getString(R.string.question_service),
                    answer = false,
                    comment = application.resources.getString(R.string.comment_service),
                    drawable = R.drawable.ic_outline_cast_24
                ),
                Quests(
                    quest = application.resources.getString(R.string.question_linear),
                    answer = false,
                    comment = application.resources.getString(R.string.comment_linear),
                    drawable = R.drawable.ic_blur_linear
                )
            )
        )
    }

    /**
     * Для получения синглтона KnowledgeDataBase
     */
    companion object {
        private var instance: KnowledgeDataBase? = null
        fun getInstance(application: Application): KnowledgeDataBase {
            val temp = if (instance != null) {
                instance
            } else {
                instance = KnowledgeDataBase(application)
                instance
            }
            return temp!!
        }
    }

    fun getQuests(): List<Quests>  = questsData.toList()

    fun getAnswers(): Map<String, Boolean> = answersData.toMap()

    fun insert(quests: Quests): Boolean = questsData.add(quests)

    fun answerInsert(quests: Quests, answer: Boolean): Boolean? =
        answersData.put(quests.quest, answer)

}


