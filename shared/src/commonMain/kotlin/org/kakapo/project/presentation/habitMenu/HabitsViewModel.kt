package org.kakapo.project.presentation.habitMenu

import androidx.lifecycle.ViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.native.ObjCName

@ObjCName("HabitsViewModelKt")
class HabitsViewModel: ViewModel() {

    @NativeCoroutines
    val uiEffect: SharedFlow<HabitsEffect> get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<HabitsEffect>()

}