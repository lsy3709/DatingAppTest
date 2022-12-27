package com.example.date_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bokchi.sogating_final.slider.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter
    //뷰를 어떻게 보일거냐? 카드 스택뷰에 정의된 부분에서 가져올 예정.
    lateinit var manager : CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object  : CardStackListener {

            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })

        val testList = mutableListOf<String>()
        testList.add("a")
        testList.add("b")
        testList.add("c")
        testList.add("d")

        cardStackAdapter = CardStackAdapter(baseContext, testList)

        // 카드 스택 레이아웃에 매니저 연결
       cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter
    }
}