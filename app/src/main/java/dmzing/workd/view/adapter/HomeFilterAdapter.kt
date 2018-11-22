package dmzing.workd.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import dmzing.workd.R
import dmzing.workd.model.home.HomeFilterData
import kotlinx.android.synthetic.main.home_course_filter_list.view.*

/**
 * Created by VictoryWoo
 */
class HomeFilterAdapter(var items: ArrayList<HomeFilterData>, var context: Context) :
    RecyclerView.Adapter<HomeFilterAdapter.HomeFilterViewHolder>() {

    lateinit var onItemClick :View.OnClickListener

    fun setItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFilterViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.home_course_filter_list, parent, false)
        view.setOnClickListener(onItemClick)
        return HomeFilterViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeFilterViewHolder, position: Int) {
        when(items[position].id){
            1->{
                if(items[position].isPicked)
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                holder.fiter_map.text = "데이트 맵"
            }
            2->{
                if(items[position].isPicked)
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                holder.fiter_map.text = "역사기행 맵"
            }
            3->{
                if(items[position].isPicked)
                    holder.filter_btn.background = ContextCompat.getDrawable(context, R.drawable.filter_background)
                holder.fiter_map.text = "자연탐방 맵"
            }
        }

    }

    inner class HomeFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fiter_map: TextView = itemView.findViewById(R.id.filterMapItem)
        var filter_btn : RelativeLayout = itemView.findViewById(R.id.filterBtn)
    }

}