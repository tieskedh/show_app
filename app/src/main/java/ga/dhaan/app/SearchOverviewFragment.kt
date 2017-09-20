package ga.dhaan.app

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewSwitcher
import kotlinx.android.synthetic.main.fragment_search_overview.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.ctx
import kotlin.properties.Delegates.observable

class SearchOverviewFragment : Fragment() {

    //beschikbaar vanaf onViewCreated
    lateinit var adapter: ShowAdapter
    var searchValue: String by observable("") { _, _, new ->
        adapter.loadFromUri(BASE_QUERY.addQuery("q", new))
    }

    private var mListener: OnShowSelectListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?)
            = inflater.inflate(R.layout.fragment_search_overview, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = ShowAdapter(view as ViewSwitcher)
        (savedInstanceState ?: arguments)?.getString(SEARCH_VALUE)?.also { searchValue = it }
        recSearch.adapter = adapter
        recSearch.layoutManager = LinearLayoutManager(ctx)

        adapter.onShowSelected { mListener?.onShowSelected(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState[SEARCH_VALUE] = searchValue
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = requireNotNull(context as? OnShowSelectListener) {
            "$context must implement OnShowSelectListener"
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnShowSelectListener {
        fun onShowSelected(show: ShowWithScore)
    }

    companion object {
        private val SEARCH_VALUE = "search_value"

        operator fun invoke(searchValue: String) = SearchOverviewFragment().apply {
            arguments = bundleOf(SEARCH_VALUE to searchValue)
        }

        private val BASE_QUERY = Uri.Builder()
                .scheme("http")
                .authority("api.tvmaze.com")
                .appendPath("search")
                .appendPath("shows")
                .build()
    }
}