package ga.dhaan.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.*
import org.jetbrains.anko.bundleOf
import kotlin.properties.Delegates


class DetailFragment : Fragment() {

    var show by Delegates.observable<ShowWithScore?>(null) { _, old, new ->
        if (old != new) requireNotNull(new).apply {
            detail_text.text = toDetails()
            detail_text.textSize = 25f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_detail, container)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        show = arguments?.getParcelable(SHOW_DETAILS_KEY)
    }

    private fun ShowWithScore.toDetails() = with(show) {
        getString(R.string.details_string,
                name,
                language,
                genres.toString(),
                status
        )
    }

    companion object {
        const val SHOW_DETAILS_KEY = "eu.dhaan.app.showDetails"
        operator fun invoke(show: ShowWithScore) = DetailFragment().apply {
            arguments = bundleOf(SHOW_DETAILS_KEY to show)
        }
    }
}