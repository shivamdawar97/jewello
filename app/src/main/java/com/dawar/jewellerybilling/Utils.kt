package com.dawar.jewellerybilling

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.InverseMethod
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val DATE_FORMAT = SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.US)
    private val DATE_FORMAT_FOR_HEADING = SimpleDateFormat("dd MMM yyyy", Locale.US)
    val GOLD_RATE = preferencesKey<Int>("gold_rate")
    val SILVER_RATE = preferencesKey<Int>("silver_rate")

    var printerName = ""
    var bussinessName = ""

    data class RatePreferences(val goldRate: Int, val silverRate: Int)

    fun TextView.onTextChanged(listener: (CharSequence) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.invoke(s!!)
            }
        })
    }

    fun TabLayout.onTabSelected(listener: (TabLayout.Tab) -> Unit) {
        this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                listener.invoke(tab!!)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    fun EditText.getTextToFloat() = this.text.toString().let {
        if (it.isNotBlank()) it.toFloat() else 0f
    }

    fun EditText.getTextToInt() = this.text.toString().let {
        if (it.isNotBlank()) it.toInt() else 0
    }

    fun EditText.getTextToLong() = this.text.toString().let {
        if (it.isNotBlank()) it.toLong() else 0
    }

    fun <T> AppCompatActivity.startActivity(cls: Class<T>) =
        this.startActivity(Intent(this, cls))

    fun Date.getFormattedDate() = DATE_FORMAT.format(this)

    @JvmStatic
    fun getDate(date: Date): String = DATE_FORMAT_FOR_HEADING.format(date)

    @JvmStatic
    fun getDateStringFromLong(date: Long): String = Date(date).getFormattedDate()

    fun animationListener(listener: () -> Unit): Animation.AnimationListener {
        return object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                listener.invoke()
            }
        }
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun DataStore<Preferences>.getRateValuesFlow(
        key: Array<Preferences.Key<Int>>,
        defaultValue: Array<Int>
    ): Flow<RatePreferences> {
        return this.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val goldRate = preferences[key[0]] ?: defaultValue[0]
                val silverRate = preferences[key[1]] ?: defaultValue[1]
                RatePreferences(goldRate, silverRate)
            }
    }

    suspend fun DataStore<Preferences>.setRateValues(
        key: Array<Preferences.Key<Int>>,
        value: Array<Int>
    ) {
        this.edit { preferences ->
            preferences[key[0]] = value[0]
            preferences[key[1]] = value[1]
        }
    }

    @InverseMethod("stringToFloat")
    @JvmStatic
    fun floatToString(context: Context, value: Float) = value.toString()

    @JvmStatic
    fun stringToFloat(context: Context, value: String) = value.toFloat()

    @InverseMethod("stringToLong")
    @JvmStatic
    fun longToString(value: Long) =
        value.toString()

    @JvmStatic
    fun stringToLong(value: String) =
        if (value.isNotBlank()) value.toString().toLong() else 0L

    fun updatePrinterName(name: String, sharedPreferences: SharedPreferences) {
        printerName = name
        sharedPreferences.edit().putString("printer_name", name).apply()
    }

    class GeneralViewHolder<T>(
        private val mView: View,
        private val onBind: (T, View) -> Unit
    ) : RecyclerView.ViewHolder(mView) {
        fun populate(item: T) = onBind(item, mView)
    }

    fun <T> generatedAdapter(list: List<T>, resource: Int, onBind: (T, View) -> Unit) =
        object : RecyclerView.Adapter<GeneralViewHolder<T>>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                GeneralViewHolder(
                    LayoutInflater.from(parent.context).inflate(resource, parent, false), onBind
                )

            override fun onBindViewHolder(holder: GeneralViewHolder<T>, position: Int) =
                holder.populate(list[position])

            override fun getItemCount() = list.size
        }


}