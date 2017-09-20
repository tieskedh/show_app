package ga.dhaan.app

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ViewSwitcher
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.IOException
import kotlin.properties.Delegates

typealias ShowSelectListener = (ShowWithScore) -> Unit

class ShowAdapter(val switcher: ViewSwitcher, shows: List<ShowWithScore> = emptyList()) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {
    private var listener: ShowSelectListener? = null

    var shows by Delegates.observable(shows) { _, old, new ->
        val newIsEmpty = new.isEmpty()
        if (old.isEmpty() != newIsEmpty) {
            switcher.displayedChild = if (newIsEmpty) 0 else 1
        }
        notifyDataSetChanged()
    }


    fun loadFromUri(uri: Uri) = async(UI) {
        shows = bg<List<ShowWithScore>> {
            try {
                Gson().fromJson(uri.toUrl().readText())
            } catch (e: IOException) {
                e.printStackTrace()
                emptyList()
            }
        }.await()
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) = holder.bind(shows[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TextView(parent.context).apply {
        textSize = 16f
    }.wrap()

    override fun getItemCount() = shows.size

    fun onShowSelected(listener: ShowSelectListener?) = listener.also { this.listener = it }

    fun TextView.wrap() = ShowViewHolder(this)
    inner class ShowViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {

        fun bind(show: ShowWithScore) {
            view.text = show.show.name
            view.onClick {
                listener?.invoke(show)
            }
        }
    }
}