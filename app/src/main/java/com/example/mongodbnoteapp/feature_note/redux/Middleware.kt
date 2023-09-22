package com.example.mongodbnoteapp.feature_note.redux

interface Middleware<A: Action, S: State, SE: SideEffect> {
    suspend fun process(
        action: A,
        currentState: S,
        container: Container<A, S, SE>,
        )
}