package com.androidnews.views

interface AsyncState

class SuccessState : AsyncState
class ErrorState(val throwable: Throwable) : AsyncState
class LoadingState : AsyncState



