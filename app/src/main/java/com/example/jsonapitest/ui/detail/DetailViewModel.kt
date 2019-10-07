package com.example.jsonapitest.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.Post
import com.example.domain.User
import com.example.jsonapitest.ui.utils.ScopedViewModel
import com.example.usecase.CheckReadStatus
import com.example.usecase.FindPostById
import com.example.usecase.GetUser
import com.example.usecase.ToggleAnimeFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val postId: Int,
    private val findPostById: FindPostById,
    private val toggleAnimeFavorite: ToggleAnimeFavorite,
    private val checkReadStatus: CheckReadStatus,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    class UiModel(val post: Post)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findPost()
            return _model
        }

    private fun findPost() = launch {
        _model.value = UiModel(findPostById.invoke(postId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.post?.let {
            _model.value = UiModel(toggleAnimeFavorite.invoke(it))
        }
    }

    fun updateReadStatus() = launch {
        _model.value?.post?.let {
            _model.value = UiModel(checkReadStatus.invoke(it))
        }
    }
}