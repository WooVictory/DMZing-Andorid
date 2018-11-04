package dmzing.workd.view.mypage

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_mypage_review.*

class MypageReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_review)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        collapsToolbar.title = "나의 리뷰"


        collapsToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.toolbarColor))

        collapsToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.toolbarColor))


    }
}
