package com.example.android.mobiledeveloperinterntest

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mobiledeveloperinterntest.models.UsersResponse
import com.example.android.mobiledeveloperinterntest.retrofit.ApiService
import com.example.android.mobiledeveloperinterntest.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstViewModel: ViewModel() {

    private val _inputPalindromText: MutableLiveData<String> = MutableLiveData()
    private val _name: MutableLiveData<String> = MutableLiveData()
    private val _listUser: MutableLiveData<List<UserData>> = MutableLiveData()
    private val _selectedUser: MutableLiveData<UserData> = MutableLiveData()

    val inputPalindromText: LiveData<String> = _inputPalindromText
    val name: LiveData<String> = _name
    val listUser: LiveData<List<UserData>> = _listUser
    val selectedUser: LiveData<UserData> = _selectedUser

    fun checkPalindrome(text: Editable?): Boolean{
        _inputPalindromText.apply { value = text.toString() }
        if (text != null) {
            val len = text.length
            for (i in 0 until text.length / 2) {
                if (text[i] != text[len - i - 1]) {
                    return false
                }
            }
        }
        else {
            return false
        }
        return true
    }

    fun setName(text: Editable?){
        _name.apply { value = text.toString() }
    }

    fun selectUser(user: UserData){
        _selectedUser.apply { value = user }
    }

    fun loadUsers(page: Int) {
        ApiService.apiService.getUsers(page).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    if (_listUser.value == null) {
                        _listUser.apply { value = response.body()?.data as List<UserData> }
                        return
                    }
                    else {
                        val list = _listUser.value as MutableList<UserData>
                        list.addAll(response.body()?.data as List<UserData>)
                        _listUser.apply { value = list }
                    }
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("FirstViewModel", "Error: ${t.message}")
            }
        })
    }

    fun refreshUsers() {
        ApiService.apiService.getUsers(1).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    _listUser.apply { value = response.body()?.data as List<UserData>? }
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("FirstViewModel", "Error: ${t.message}")
            }
        })
    }
}