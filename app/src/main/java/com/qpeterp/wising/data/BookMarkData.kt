package com.qpeterp.wising.data

data class BookMarkData(
    val name: String = "개발자",
    val quote: String = "실패해도 괜찮아요.. \n저도 지금 실패해서 화면에 나온 글인데요 뭐"
)
// 기본 생성자를 적어두지 않아서 자꾸 십 아잇 RunTimeError가 쳐났음
//
