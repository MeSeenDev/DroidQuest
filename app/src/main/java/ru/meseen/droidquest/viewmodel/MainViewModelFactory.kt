package ru.meseen.droidquest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.meseen.droidquest.data.Quests
import ru.meseen.droidquest.repository.Repository

class MainViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java)
            .newInstance(application)
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository = Repository(application)
    private var questData: MutableLiveData<List<Quests>>
    private var questAnswersData: MutableLiveData<Map<String, Boolean>>

    init {
        questData = MutableLiveData(repository.getQuests())
        questAnswersData = MutableLiveData(repository.getAnswers())
    }

    fun getQuestList(): MutableLiveData<List<Quests>> = questData

    fun getAnswersMap(): MutableLiveData<Map<String, Boolean>> = questAnswersData


    /**
     * @param quests квест для динамического добавления квестов
     * тк работа не из BD то обновляю в ручную
     */
    fun insert(quests: Quests): Boolean {
        val boolean = repository.insert(quests)
        questData.value = repository.getQuests()
        return boolean
    }

    /**
     * @param quests квест на который отвечали (как ключ запишется quests.quest)
     * @param answer ответ пользователя или чит системы если подглядели
     */
    fun answerInsert(quests: Quests, answer: Boolean): Boolean? {
        val boolean = repository.answerInsert(quests, answer)
        questAnswersData.value = repository.getAnswers()
        return boolean
    }
}