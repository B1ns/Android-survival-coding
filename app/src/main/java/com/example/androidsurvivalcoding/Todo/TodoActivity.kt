package com.example.androidsurvivalcoding.Todo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.androidsurvivalcoding.R
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_todo.*
import org.jetbrains.anko.startActivity

class TodoActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity<EditActivity>()
        }

        // 전체 할 일 정보를 가져와서 날짜순으로 내림차순 정렬
        val realmResult = realm.where<Todo>()
            .findAll()
            .sort("date", Sort.DESCENDING)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
