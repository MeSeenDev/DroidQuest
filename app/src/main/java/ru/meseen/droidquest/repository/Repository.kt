@file:Suppress("JoinDeclarationAndAssignment")

package ru.meseen.droidquest.repository

import android.app.Application
import ru.meseen.droidquest.data.KnowledgeDataBase
import ru.meseen.droidquest.data.Quests

class Repository(application: Application) {

    private lateinit var questsData: List<Quests>
    private lateinit var questsAnswers: Map<String, Boolean>
    private var dataBase: KnowledgeDataBase

    init {
        dataBase = KnowledgeDataBase.getInstance(application)
        updateQuests()
    }

    private fun updateQuests() {
        questsData = dataBase.getQuests()
        questsAnswers = dataBase.getAnswers()
    }

    fun getQuests(): List<Quests> {
        updateQuests()
        return questsData
    }

    fun getAnswers() : Map<String,Boolean> = dataBase.getAnswers()

    fun insert(quests: Quests): Boolean = dataBase.insert(quests)

    fun answerInsert(quests: Quests, answer: Boolean):Boolean? = dataBase.answerInsert(quests,answer)


}
