package com.dawar.jewellerybilling.customers.addActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.databinding.ActivityAddCustomerBinding
import com.dawar.jewellerybilling.rx.RxBus
import com.dawar.jewellerybilling.rx.RxEvent
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding
    private lateinit var rxItemAdded: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer)
        binding.lifecycleOwner = this
        binding.viewModel = viewModels<AddCustomerViewModel>().value

        rxItemAdded = RxBus.listen(RxEvent.ItemAdded::class.java).subscribe {
            finish()
        }
    }

    fun goBack(v: View) = finish()

    override fun onDestroy() {
        super.onDestroy()
        rxItemAdded.dispose()
    }
}