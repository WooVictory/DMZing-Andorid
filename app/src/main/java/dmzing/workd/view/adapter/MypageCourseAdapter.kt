package dmzing.workd.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.mypage.CourseDatas
import dmzing.workd.model.mypage.MypageCourseData
import dmzing.workd.view.mypage.MyLetterActivity
import dmzing.workd.view.mypage.MypageReviewActivity
import kotlinx.android.synthetic.main.my_course_item.view.*
import org.jetbrains.anko.startActivity

class MypageCourseAdapter(var itemList: List<CourseDatas>, var context: Context) :
    RecyclerView.Adapter<MypageCourseAdapter.MypageCourseViewHolder>() {


    lateinit var onItemClick : View.OnClickListener

    fun setOnClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MypageCourseViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.my_course_item, p0, false)
        view.setOnClickListener(onItemClick)
        return MypageCourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MypageCourseViewHolder, position: Int) {
        holder.courseTitle.text = itemList.get(position).title
        holder.courseDescription.text = itemList.get(position).mainDescription
        holder.courseImageButton.setOnClickListener {
            //context.startActivity()
        }
        holder.courseLayout.setOnClickListener {
            var cid : Int = itemList[position].id
            context.startActivity<MyLetterActivity>("cid" to cid)
            // 앞에가 key 값이고 뒤에가 value 값.
        }
    }


    inner class MypageCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var courseTitle: TextView = itemView.findViewById(R.id.myCourse_title_item)
        var courseDescription: TextView = itemView.findViewById(R.id.myCourse_description_item)
        var courseImageButton: ImageView = itemView.findViewById(R.id.mycourse_item_button)
        var courseLayout : RelativeLayout = itemView.myCourseLayout
    }
}