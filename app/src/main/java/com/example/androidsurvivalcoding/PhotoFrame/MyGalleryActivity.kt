package com.example.androidsurvivalcoding.PhotoFrame

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.androidsurvivalcoding.PhotoFrame.Adapter.MyPagerAdapter
import com.example.androidsurvivalcoding.PhotoFrame.Fragment.PhotoFragment
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_my_gallery.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.concurrent.timer

class MyGalleryActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_gallery)

        //권한이 부여되었는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //권환이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //이전에 이미 권환이 거부되었을 때 설명
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필요함"){
                    yesButton {
                        //권한 요청
                        ActivityCompat.requestPermissions(this@MyGalleryActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_READ_EXTERNAL_STORAGE)
                }
                    noButton { toast("권환 요청실패") }
                }.show()
            }else{
                //권한 요청
                ActivityCompat.requestPermissions(this@MyGalleryActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_READ_EXTERNAL_STORAGE)
            }
        }else{
            //권환이 허용되있을경우
            getAllPhotos()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getAllPhotos()
                }else{
                    toast("권한 거부됨.")
                }
                return
            }
        }
    }

    private fun getAllPhotos() {
        // 모든 사진 정보 가져오기
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC" // 찍은 날짜 내림차순
        )

        val fragments = ArrayList<Fragment>()


        if (cursor != null) {
            while (cursor.moveToNext()) {
                //사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MyGalleryActivity", uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }
        //어뎁터
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter


        //3초마다 슬라이드
        timer(period = 3000){
            runOnUiThread {
                if (viewPager.currentItem<adapter.count - 1){
                    viewPager.currentItem = viewPager.currentItem + 1
                }else{
                    viewPager.currentItem = 0
                }
            }
        }
    }

    

}