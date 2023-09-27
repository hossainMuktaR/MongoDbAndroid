package com.example.mongodbnoteapp.feature_note.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Container<A: Action, S: State, SE: SideEffect> {

    val state: StateFlow<S>
    val sideEffect: SharedFlow<SE>
    val scope: CoroutineScope

    suspend fun dispatch(action: A)

    suspend fun emitSideEffect(sideEffect: SE)
}