package com.ermilova.android.diary.utils

sealed class Result {
    object Loading : Result()
    object Success : Result()
    object Error : Result()
}