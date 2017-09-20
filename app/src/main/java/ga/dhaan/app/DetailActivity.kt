package ga.dhaan.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ga.dhaan.app.DetailFragment.Companion.SHOW_DETAILS_KEY
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        (detailFragment as DetailFragment).show= intent[SHOW_DETAILS_KEY]
    }
}