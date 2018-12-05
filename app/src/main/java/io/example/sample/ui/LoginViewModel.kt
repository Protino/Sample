package io.example.sample.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val emailPhoneText: LiveData<String> = MutableLiveData()
    val passwordText: LiveData<String> = MutableLiveData()
}