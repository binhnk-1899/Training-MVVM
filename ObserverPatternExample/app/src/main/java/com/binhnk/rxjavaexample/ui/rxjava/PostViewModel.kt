package com.binhnk.rxjavaexample.ui.rxjava

import androidx.lifecycle.MutableLiveData
import com.binhnk.rxjavaexample.base.BaseViewModel
import com.binhnk.rxjavaexample.data.repository.PostRepository
import com.binhnk.rxjavaexample.models.Post
import com.binhnk.rxjavaexample.rx.SchedulerProvider

class PostViewModel(
    private val postRepository: PostRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val data = MutableLiveData<List<Post>>()
    val loading = MutableLiveData<Boolean>().apply { postValue(false) }

    fun getAllPost() {
        loading.postValue(true)

        compositeDisposable.add(postRepository.getPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally { loading.postValue(false) }
            .subscribe({ items ->
                data.value = items
            }, {})
        )
    }

}