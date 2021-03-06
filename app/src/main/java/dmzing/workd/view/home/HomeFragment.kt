package dmzing.workd.view.home

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmzing.workd.CommonData
import dmzing.workd.R
import dmzing.workd.model.home.HomeCourseData
import dmzing.workd.model.home.HomeFilterData
import dmzing.workd.model.home.PickCourse
import dmzing.workd.network.ApplicationController
import dmzing.workd.network.NetworkService
import dmzing.workd.util.SharedPreference
import dmzing.workd.view.adapter.HomeCourseAdapter
import dmzing.workd.view.adapter.HomeFilterAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import dmzing.workd.view.chatbot.ChatbotActivity
import dmzing.workd.view.MainActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by VictoryWoo
 */
class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            /*   v -> {
                   var index = courseList.getChildAdapterPosition(v!!)
                   toast("${index}")
               }*/
            v!!.filterDMZingBtn -> {
                if (!filterDMZingBtn.isSelected) {

                    filterDMZingBtn.background = ContextCompat.getDrawable(context!!, R.drawable.filter_background)

                    filterDateBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterHistoryBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterNaturalBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)

                    filterDMZingBtn.isSelected = true
                    filterDateBtn.isSelected = false
                    filterHistoryBtn.isSelected = false
                    filterNaturalBtn.isSelected = false

                    putCoursePick(view!!, 1)
                } else {
                    filterDMZingBtn.isSelected = false
                    filterDMZingBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                }

            }
            v!!.filterDateBtn -> {
                if (!filterDateBtn.isSelected) {
                    filterDMZingBtn.isSelected = false
                    filterDateBtn.isSelected = true
                    filterHistoryBtn.isSelected = false
                    filterNaturalBtn.isSelected = false

                    filterDateBtn.background = ContextCompat.getDrawable(context!!, R.drawable.filter_background)

                    filterDMZingBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterHistoryBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterNaturalBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)


                    putCoursePick(view!!, 2)
                } else {
                    filterDateBtn.isSelected = true
                    filterDateBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                }
            }
            v!!.filterHistoryBtn -> {
                if (!filterHistoryBtn.isSelected) {
                    filterHistoryBtn.isSelected = true
                    filterDateBtn.isSelected = false
                    filterDMZingBtn.isSelected = false
                    filterNaturalBtn.isSelected = false

                    filterHistoryBtn.background = ContextCompat.getDrawable(context!!, R.drawable.filter_background)

                    filterDateBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterDMZingBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterNaturalBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)





                    putCoursePick(view!!, 3)
                } else {
                    filterHistoryBtn.isSelected = true
                    filterHistoryBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                }
            }
            v!!.filterNaturalBtn -> {
                if (!filterNaturalBtn.isSelected) {
                    filterNaturalBtn.background = ContextCompat.getDrawable(context!!, R.drawable.filter_background)

                    filterHistoryBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterDateBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                    filterDMZingBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)

                    filterNaturalBtn.isSelected = true
                    filterHistoryBtn.isSelected = false
                    filterDateBtn.isSelected = false
                    filterDMZingBtn.isSelected = false


                    putCoursePick(view!!, 4)

                } else {
                    filterNaturalBtn.isSelected = true
                    filterNaturalBtn.background =
                            ContextCompat.getDrawable(context!!, R.drawable.filter_opacity_background)
                }
            }
            v!!.chatbotBtn -> {
                context!!.startActivity<ChatbotActivity>()
            }
        }
    }

    lateinit var homeCourseAdapter: HomeCourseAdapter
    lateinit var courseItems: PickCourse
    lateinit var filterItems: ArrayList<HomeFilterData>
    lateinit var mainActivity: MainActivity
    lateinit var hView: View
    private var ovelapNetworking: String = ""
    val MY_LOCATION_REQUEST_CODE = 100


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = this@HomeFragment.activity!! as MainActivity
    }

    override fun onResume() {
        super.onResume()
        getHomeMission(view!!, 2)

    }


    fun init(view: View) {
        view.filterDMZingBtn.setOnClickListener(this)
        view.filterDateBtn.setOnClickListener(this)
        view.filterHistoryBtn.setOnClickListener(this)
        view.filterNaturalBtn.setOnClickListener(this)
        view.chatbotBtn.setOnClickListener(this)

        view.filterDMZingBtn.isSelected = false
        view.filterDateBtn.isSelected = false
        view.filterHistoryBtn.isSelected = false
        view.filterNaturalBtn.isSelected = false

        filterItems = ArrayList()

        networkService = ApplicationController.instance.networkService
        SharedPreference.instance!!.load(context!!)
    }

    lateinit var networkService: NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        hView = view
        init(view)

        //getLocation()
        requestLocationPermission()


        /*FIXME
        * android recyclerview를 viewpager처럼 아이템 하나씩 스크롤 할 수 있도록 하기 위한 방법으로
        * snapHelper라는 것을 사용하면 되고 코드는 아래의 두줄로 충분하다.
        * */
        var snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.courseList)


        getHomeMission(view, 1)

        return view
    }

    fun requestLocationPermission() {
        if (context!!.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            context!!.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ), MY_LOCATION_REQUEST_CODE
                )
            else {
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ), MY_LOCATION_REQUEST_CODE
                )
            }
        } else {

            getLocation()
        }
    }


    fun getLocation() {
        val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Define a listener that responds to location updates
        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                //context!!.toast("testing2222")
                var lat: Double? = location.latitude
                var lng: Double? = location.longitude
                CommonData.commonLatitude = lat
                CommonData.commonLongitude = lng


                Log.v("859 lat", lat.toString())
                Log.v("859 lng", lng.toString())
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }

        // Register the listener with the Location Manager to receive location updates
        try {
            Log.v("859 lat", "ㅇㅇㅇㅇㅇ try")
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.v("859 lat", "ㅇㅇㅇㅇㅇ catch")
            context!!.toast("위치를 잡을 수 없습니다.")
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                for (i in 0 until grantResults.size - 1) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getLocation()
                        activity!!.finish()
                    }
                }
            }
        }
    }

    fun getHomeMission(view: View, type: Int) {
        var homeResponse = networkService
            .getHomeMissions(SharedPreference.instance!!.getPrefStringData(CommonData.JWT)!!)


        if (ovelapNetworking == "") {
            ovelapNetworking = "networking"

            homeResponse.enqueue(object : Callback<HomeCourseData> {
                override fun onFailure(call: Call<HomeCourseData>, t: Throwable) {
                    Log.v("Error Home WOO:", t.message)
                    ovelapNetworking = "networking"
                }

                override fun onResponse(call: Call<HomeCourseData>, response: Response<HomeCourseData>) {
                    Log.v("853 woo r:", response.code().toString())

                    response?.let {
                        when (it.code()) {
                            200 -> {
                                //settingFilterItems(view, response.body()!!.purchaseList)
                                filterItems = response.body()!!.purchaseList

                                for (i in 0..filterItems.size - 1) {
                                    when (filterItems[i].id) {
                                        1 -> {
                                            if (filterItems[i].isPicked)
                                                view.filterDMZingBtn.background =
                                                        ContextCompat.getDrawable(
                                                            context!!,
                                                            R.drawable.filter_background
                                                        )

                                            view.filterDMZingBtn.visibility = View.VISIBLE
                                        }
                                        2 -> {
                                            if (filterItems[i].isPicked)
                                                view.filterDateBtn.background =
                                                        ContextCompat.getDrawable(
                                                            context!!,
                                                            R.drawable.filter_background
                                                        )

                                            view.filterDateBtn.visibility = View.VISIBLE
                                        }
                                        3 -> {
                                            if (filterItems[i].isPicked)
                                                view.filterHistoryBtn.background =
                                                        ContextCompat.getDrawable(
                                                            context!!,
                                                            R.drawable.filter_background
                                                        )

                                            view.filterHistoryBtn.visibility = View.VISIBLE
                                        }
                                        4 -> {
                                            if (filterItems[i].isPicked)
                                                view.filterNaturalBtn.background =
                                                        ContextCompat.getDrawable(
                                                            context!!,
                                                            R.drawable.filter_background
                                                        )

                                            view.filterNaturalBtn.visibility = View.VISIBLE
                                        }

                                    }
                                }

                            }
                            else -> {

                            }
                        }?.also {
                            if (type == 1)
                                settingHomeItems(view, response.body()!!.pickCourse)
                            ovelapNetworking = ""
                        }
                    }
                }

            })

        }


    }

    // 필터 아이템 통신
/*    fun settingFilterItems(view: View, items: ArrayList<HomeFilterData>) {
        filterItems = items
        view.homeFilterRv.setHasFixedSize(true)
        view.homeFilterRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFilterAdapter = HomeFilterAdapter(filterItems, context!!)
        homeFilterAdapter.setItemClickListener(this)
        homeFilterAdapter.setOnFilterSelectListener(object : HomeFilterAdapter.setFilterSelect {
            override fun onFilterSelect(holder: HomeFilterAdapter.HomeFilterViewHolder, position: Int) {
                if (holder.isCheck) {
                    holder.isChecked = false
                } else {
                    // recyclerview의 아이템 들 중에 체크가 되어 있는지 for문을 통해서 검사하는 과정
                    Log.v("142 woo size", view.homeFilterRv.childCount.toString())
                    Log.v("142 woo item size", items.size.toString())
                    Log.v("142 woo item size2", view.homeFilterRv.adapter!!.itemCount.toString())
                    Log.v("142 woo item id?", homeFilterAdapter.getItemId(1).toString())

                    for (i in 0 until view.homeFilterRv.adapter!!.itemCount - 1) {
                        Log.v("142 woo i", i.toString())


                        var viewHolder = view.homeFilterRv.findViewHolderForLayoutPosition(i)!!
                                as HomeFilterAdapter.HomeFilterViewHolder
                        Log.v("142 woo holder position", viewHolder.adapterPosition.toString())
                        Log.v("142 woo viewholder", viewHolder.itemId.toString())
                        if (viewHolder.isCheck) {
                            viewHolder.isChecked = false
                        }
                    }
                    holder.isChecked = true
                    //toast("${holder.fiter_map.text}")
                    putCoursePick(view, items[position].id)
                    toast("${items[position].id}+${holder.fiter_map.text}")

                }
            }

        })
        view.homeFilterRv.adapter = homeFilterAdapter

    }*/

    // 코스 픽하기
    fun putCoursePick(view: View, cid: Int) {
        Log.v("woo 731 put:", "또잉")
        var coursePickResponse = networkService.putCoursePick(
            SharedPreference.instance!!.getPrefStringData("jwt")!!, cid)


        if(ovelapNetworking == ""){
            ovelapNetworking = "networking"

            coursePickResponse.enqueue(object : Callback<PickCourse> {
                override fun onFailure(call: Call<PickCourse>, t: Throwable) {
                    Log.v("woo 731 f:", t.message)
                    ovelapNetworking = ""
                }

                override fun onResponse(call: Call<PickCourse>, response: Response<PickCourse>) {
                    Log.v("woo 731 r:", response.message())
                    Log.v("woo 731 r:", response.code().toString())

                    response?.let {
                        when (it.code()) {
                            200 -> {
                                if (response.body()!!.places.size == 4) {
                                    CommonData.complete_flag = 4
                                }
                                settingHomeItems(view, response.body()!!)
                            }
                            else->{

                            }
                        }
                    }?.also {
                        ovelapNetworking = ""
                    }

                }

            })

        }

    }

    // 리사이클러뷰 아이템 세팅
    fun settingHomeItems(view: View, items: PickCourse) {
        courseItems = items
        if (courseItems.places.size == 4) {
            Log.v("0627 size :", courseItems.places.size.toString())
            CommonData.complete_flag = 4
        }
        view.courseList.setHasFixedSize(true)
        view.courseList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeCourseAdapter = HomeCourseAdapter(courseItems, context!!)
        homeCourseAdapter.setOnItemClick(this)
        view.courseList.adapter = homeCourseAdapter
        homeCourseAdapter.notifyDataSetChanged()
    }


}