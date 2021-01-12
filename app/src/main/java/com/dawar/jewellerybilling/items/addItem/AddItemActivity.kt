package com.dawar.jewellerybilling.items.addItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.billing.BillItemsRecyclerViewAdapter
import com.dawar.jewellerybilling.customers.addActivity.AddCustomerViewModel
import com.dawar.jewellerybilling.databinding.ActivityAddItemBinding
import com.dawar.jewellerybilling.rx.RxBus
import com.dawar.jewellerybilling.rx.RxEvent
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private lateinit var rxItemAdded: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_item)
        binding.lifecycleOwner = this
        binding.viewModel = viewModels<AddItemViewModel>().value

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