package com.dawar.jewellerybilling.billing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BillingViewModel(isEditEnabled:Boolean): ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>()
    private val _goldRate = MutableLiveData<Float>()
    private val _silverRate = MutableLiveData<Float>()

    val editRateEnabled : LiveData<Boolean>
        get() = _editRateEnabled

    val goldRate : LiveData<Float>
        get() = _goldRate

    val silverRate : LiveData<Float>
        get() = _silverRate

    init {
        _editRateEnabled.value = isEditEnabled
        _goldRate.value = 0f
        _silverRate.value = 0f

    }

    fun editRateEnabled() {
        _editRateEnabled.value = true
    }

    fun applyRates(goldRate:String?,silverRate:String?){

        _goldRate.value = (goldRate?:"0").toFloat()
        _silverRate.value = (silverRate?:"0").toFloat()
        _editRateEnabled.value = false
    }

}