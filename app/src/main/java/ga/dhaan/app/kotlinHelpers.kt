package ga.dhaan.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import com.google.gson.Gson
import java.net.URL

/**
 * Created by thijs on 20-9-2017.
 */

operator fun <S : Parcelable> Bundle.set(key: String, value: S){
    putParcelable(key, value)
}
operator fun Bundle.set(key: String, value: String){
    putString(key,value)
}

operator inline fun <reified T: Parcelable> Bundle.get(key: String) = getParcelable<T>(key) as T
operator inline fun <reified T: Parcelable> Intent.get(key: String) = getParcelableExtra<T>(key) as T


fun Uri.toUrl() = URL(this.toString())
inline fun <reified T> String.toList() = Gson().fromJson<Array<T>>(this, Array<T>::class.java).toList()

fun Uri.addQuery(key: String, value: String) = buildUpon().appendQueryParameter(key, value).build()
