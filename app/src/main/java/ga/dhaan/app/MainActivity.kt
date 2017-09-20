package ga.dhaan.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import ga.dhaan.app.DetailFragment.Companion.SHOW_DETAILS_KEY
import ga.dhaan.app.SearchOverviewFragment.OnShowSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), OnShowSelectListener {
    @PublishedApi
    internal val isOneScreen by lazy {
        findOptional<View>(R.id.detailFragment) != null
    }

    inline fun onTablet(exec: () -> Unit) = controlFlow(true, exec)
    inline fun onMobile(exec: () -> Unit) = controlFlow(false, exec)

    inline fun controlFlow(execIfTablet: Boolean, lambda: () -> Unit): Else {
        val exec = execIfTablet == isOneScreen
        if (exec) lambda()
        return Else(!exec)
    }

    class Else(private val executeElse: Boolean) {
        infix fun Else(exec: () -> Unit) {
            if (executeElse) exec()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSearchButtonClick(v: View) {
        val showName = show_search_input.text.toString()
        (overviewFragment as SearchOverviewFragment).searchValue = showName
    }

    override fun onShowSelected(show: ShowWithScore) = onMobile {
        startActivity<DetailActivity>(SHOW_DETAILS_KEY to show)
    } Else {
        (detailFragment as DetailFragment).show = show
    }
}