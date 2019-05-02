package com.example.androidsurvivalcoding.Todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.androidsurvivalcoding.R
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class EditActivity : AppCompatActivity() {

    val calendar: Calendar = Calendar.getInstance()

    val realm = Realm.getDefaultInstance() // 날짜를 다룰 캘린더 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            insertTodo()
        } else {
            updateTodo(id)
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun insertTodo() {
        realm.beginTransaction() // 트랜잭션 시작

        val newItem = realm.createObject<Todo>(nextId()) // 새 객체 생성
        // 값 설정
        newItem.title = todoEditText.text.toString()
        newItem.date = calendar.timeInMillis

        realm.commitTransaction() // 트랜잭션 종료 반영

        // 다이얼로그 표시
        alert("내용이 추가되었습니다.") {
            yesButton { finish() }
        }.show()
    }

    //다음 id 를 반환
    private fun nextId(): Int {
        val maxId = realm.where<Todo>().max("id")
        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }

    private fun updateTodo(id: Long) {
        realm.beginTransaction() // 트랜젝션 시작

        val updateItem = realm.where<Todo>().equalTo("id", id).findFirst()!!
        //값 수정
        updateItem.title = todoEditText.text.toString()
        updateItem.date = calendar.timeInMillis

        realm.commitTransaction() // 트랜잭션 종료 반영

        // 다이얼로그 표시
        alert("내용이 변경되었습니다.") {
            yesButton { finish() }
        }.show()
    }

    private fun deleteTodo(id: Long) {
        realm.beginTransaction()

        val deleteItem = realm.where<Todo>().equalTo("id", id).findFirst()
        // 삭제할 객체
        deleteItem?.deleteFromRealm()
        realm.commitTransaction()

        alert("내용이 삭제되었습니다.") {
            yesButton { finish() }
        }.show()
    }
}
