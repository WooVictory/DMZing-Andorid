package dmzing.workd.view.mypage.setting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import dmzing.workd.R
import kotlinx.android.synthetic.main.activity_service_detail.*
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ServiceDetailActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            serviceDetailBackBtn -> finish()
            serviceDetailXBtn -> {
                var intent = Intent(this, SettingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        init()
        getData()
    }

    private fun getData() {
        var data = intent.getStringExtra(ServiceActivity.DETAIL_TITLE)
        Log.v("title 421",data)
        serviceDetailTitle.text = data.toString()
        settingText(data)
    }

    fun settingText(text: String) {
        when(text){
            "DMZing 안내"->{
                serviceText.setText(readText(resources.openRawResource(R.raw.introduce)))
            }
            "이용 약관"->{
                serviceText.setText(readText(resources.openRawResource(R.raw.terms)))
            }
            "개인정보 보호 정책"->{
                serviceText.setText(readText(resources.openRawResource(R.raw.privacy)))
            }

        }

    }

    fun readText(file : InputStream): String? {
        var data: String? = null

        val inputStream = file
        val byteArrayOutputStream = ByteArrayOutputStream()

        var i: Int
        try {
            i = inputStream.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = inputStream.read()
            }

            data = String(byteArrayOutputStream.toByteArray())
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return data
    }

    private fun init() {
        serviceDetailBackBtn.setOnClickListener(this)
        serviceDetailXBtn.setOnClickListener(this)
    }
}
