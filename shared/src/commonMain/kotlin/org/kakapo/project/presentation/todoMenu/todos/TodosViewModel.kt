package org.kakapo.project.presentation.todoMenu.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.model.TodoModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("TodosViewModelKt")
class TodosViewModel(
    private val todosRepository: TodosRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(TodosState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private var _uiEffect = MutableSharedFlow<TodosEffect>()

    fun initData() {
        loadTodos()
    }

    fun handleEvent(event: TodosEvent) {
        when(event) {
            TodosEvent.NavigateBack -> emit(TodosEffect.NavigateBack)
            TodosEvent.TapToAddTodo -> emit(TodosEffect.TapToAddTodo)
        }
    }

    private fun loadTodos() = viewModelScope.launch {
        val onSuccess: (List<TodoModel>) -> Unit = { todos ->
            _uiState.update { it.copy(todos = todos) }
        }
        todosRepository.loadTodos().asResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?){
        val message = throwable?.message ?: "An unknown error occurred"
        emit(TodosEffect.ShowError(message))
    }

    private fun emit(effect: TodosEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}