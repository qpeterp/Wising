package com.qpeterp.wising.ui.bottom.qoutes

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.data.Quote
import com.qpeterp.wising.databinding.FragmentBooksBinding
import com.qpeterp.wising.ui.page.ViewPagerAdapter

class BooksFragment : Fragment() {
    private val binding by lazy { FragmentBooksBinding.inflate(layoutInflater) }
    private var quoteList = mutableListOf<Quote>()
    private val db = Firebase.firestore
    private var viewPager2: ViewPager2? = null
    private var lastVisibleDocument: DocumentSnapshot? = null
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 데이터 추가 함수 호출

//        val quotes = listOf(
//            Quote("가장 큰 영광은 한 번도 실패하지 않음이 아니라, 실패할 때마다 다시 일어서는데 있다.", "공자"),
//            Quote("나는 내가 더 노력할 수록 운이 더 좋아진다는 것을 발견했다.", "토마스 제퍼슨"),
//            Quote("희망이 있는 곳에 반드시 시련이 있는 법이다.", "무라카미 하루키"),
//            Quote("지옥을 겪고 있다면 계속 겪어 나가라.", "윈스턴 처칠"),
//            Quote("겨울이 당신에게 여름 내내 무얼 했느냐 묻는 날이 꼭 올 것이다.", "헨리 클레이"),
//            Quote("미래를 예측하는 최선의 방법은 미래를 창조하는 것이다.", "앨런 케이"),
//            Quote("나는 폭풍이 두렵지 않다. 나는 배로 항해 하는 법을 배우고 있으니까.", "헬렌 켈러"),
//            Quote("우리의 모든 꿈은 이루어질 것이다. 그들을 믿고 나갈 용기만 있다면", "월트 디즈니"),
//            Quote("해야할 일은 과감히 하라. 결심한 일은 반드시 실행하라.", "벤자민 프랭클린"),
//            Quote("꿈을 날짜와 함께 적어 놓으면 목표가 되고, 목표를 잘게 나누면 계획이 되며, 계획을 실행에 옮기면 꿈은 실현되는 것이다.", "그렉 S 리드"),
//            Quote("절대로, 절대로, 절대로, 절대로 포기하지 마라.", "윈스턴 처칠"),
//            Quote("강한 자가 이기는 것이 아니라, 이긴 자가 강한 것이다.", "프란츠 베켄바우어"),
//            Quote("늦었다고 생각할 때가 진짜 너무 늦었다. 그러니 지금 당장 시작해라.", "박명수"),
//            Quote("모든 단점은, 장점이 될 수 있다.", "리오넬 메시"),
//            Quote("목표는 당연히 우승이죠. 저는 준우승을 모릅니다.", "문호준"),
//            Quote("항구에 정박해 있는 배는 안전하다. 그러나 그것이 배의 존재 이유는 아니다.", "존 A. 쉐드"),
//            Quote("안 해 봤으면 말을 하지 마세요.", "김병만"),
//            Quote("말로 갈 수도, 차로 갈 수도, 둘이서 갈 수도, 셋이서 갈 수도 있다. 하지만 맨 마지막 한 걸음은 자기 혼자서 걷지 않으면 안된다.", "헤르만 헤세"),
//            Quote("모두가 세상을 변화시키려 하지만, 정작 스스로 변하겠다고 생각하는 사람은 없다.", "레프 톨스토이"),
//            Quote("용기란 공포를 1분 더 참는 것이다.", "조지 패튼"),
//            Quote("일이 불가능하다고 믿는 것은 일을 불가능하게 하는 일이다.", "풀러"),
//            Quote("삶이 있는 한 희망은 있다.", "키케로"),
//            Quote("산다는것 그것은 치열한 전투이다.", "로망로랑"),
//            Quote("하루에 3시간을 걸으면 7년 후에 지구를 한바퀴 돌 수 있다.", "사무엘 존슨"),
//            Quote("언제나 현재에 집중할 수 있다면 행복할 것이다.", "파울로 코엘료"),
//            Quote("진정으로 웃으려면 고통을 참아야하며, 나아가 고통을 즐길 줄 알아야 해.", "찰리 채플린"),
//            Quote("직업에서 행복을 찾아라. 아니면 행복이 무엇인지 절대 모를 것이다.", "엘버트 허버드"),
//            Quote("신은 용기 있는 자를 결코 버리지 않는다.", "켄러"),
//            Quote("행복의 문이 하나 닫히면 다른 문이 열린다. 그러나 우리는 종종 닫힌 문을 멍하니 바라보다가 우리를 향해 열린 문을 보지 못하게 된다.", "헬렌 켈러"),
//            Quote("피할 수 없으면 즐겨라.", "로버트 엘리엇"),
//            Quote("단순하게 살아라. 현대인은 쓸데없는 절차와 일 때문에 얼마나 복잡한 삶을 살아가는가?", "이드리스 샤흐"),
//            Quote("먼저 자신을 비웃어라. 다른 사람이 당신을 비웃기 전에.", "엘사 맥스웰"),
//            Quote("먼저 핀 꽃은 먼저 진다. 남보다 먼저 공을 세우려고 조급히 서둘 것이 아니다.", "채근담"),
//            Quote("행복한 삶을 살기 위해 필요한 것은 거의 없다.", "마르쿠스 아우렐리우스 안토니우스"),
//            Quote("절대 어제를 후회하지 마라. 인생은 오늘의 나 안에 있고 내일은 스스로 만드는 것이다.", "L.론허바드"),
//            Quote("어리석은 자는 멀리서 행복을 찾고, 현명한 자는 자신의 발치에서 행복을 키워간다.", "제임스 오펜하임"),
//            Quote("너무 소심하고 까다롭게 자신의 행동을 고민하지 말라. 모든 인생은 실험이다. 더 많이 실험할수록 더 나아진다.", "랄프 왈도 에머슨"),
//            Quote("한번의 실패와 영원한 실패를 혼동하지 마라.", "F.스콧 핏제랄드"),
//            Quote("내일은 내일의 태양이 뜬다.", "익명"),
//            Quote("피할 수 없으면 즐겨라.", "로버트 엘리엇"),
//            Quote("절대 어제를 후회하지 마라. 인생은 오늘의 내 안에 있고 내일은 스스로 만드는 것이다.", "L.론허바드"),
//            Quote("계단을 밟아야 계단 위에 올라설 수 있다.", "터키 속담"),
//            Quote("오랫동안 꿈을 그리는 사람은 마침내 그 꿈을 닮아 간다.", "앙드레 말로"),
//            Quote("좋은 성과를 얻으려면 한 걸음 한 걸음이 힘차고 충실하지 않으면 안 된다.", "단테"),
//            Quote("행복은 습관이다, 그것을 몸에 지니라.", "허버드"),
//            Quote("성공의 비결은 단 한 가지, 잘할 수 있는 일에 광적으로 집중하는 것이다.", "톰 모나건"),
//            Quote("자신감 있는 표정을 지으면 자신감이 생긴다.", "찰스 다윈"),
//            Quote("평생 살 것처럼 꿈을 꾸어라. 그리고 내일 죽을 것처럼 오늘을 살아라.", "제임스 딘"),
//            Quote("네 믿음은 네 생각이 된다. 네 생각은 네 말이 된다. 네 말은 네 행동이 된다. 네 행동은 네 습관이 된다. 네 습관은 네 가치가 된다. 네 가치는 네 운명이 된다.", "간디"),
//            Quote("일하는 시간과 노는 시간을 뚜렷이 구분하라. 시간의 중요성을 이해하고 매순간을 즐겁게 보내고 유용하게 활용하라. 그러면 젊은 날은 유쾌함으로 가득찰 것이고 늙어서도 후회할 일이 적어질 것이며 비록 가난할 때라도 인생을 아름답게 살아갈 수 있다.", "루이사 메이올콧"),
//            Quote("절대 포기하지 말라. 당신이 되고 싶은 무언가가 있다면, 그에 대해 자부심을 가져라. 당신 자신에게 기회를 주어라. 스스로가 형편없다고 생각하지 말라. 그래봐야 아무 것도 얻을 것이 없다. 목표를 높이 세워라. 인생은 그렇게 살아야 한다.", "마이크 맥라렌"),
//            Quote("1퍼센트의 가능성, 그것이 나의 길이다.", "나폴레옹"),
//            Quote("그대 자신의 영혼을 탐구하라. 다른 누구에게도 의지하지 말고 오직 그대 혼자의 힘으로 하라. 그대의 여정에 다른 이들이 끼어들지 못하게 하라. 이 길은 그대만의 길이요, 그대 혼자 가야할 길임을 명심하라. 비록 다른 이들과 함께 걸을 수는 있으나 다른 그 어느 누구도 그대가 선택한 길을 대신 가줄 수 없음을 알라.", "인디언 속담"),
//            Quote("고통이 남기고 간 뒤를 보라! 고난이 지나면 반드시 기쁨이 스며든다.", "괴테"),
//            Quote("삶은 소유물이 아니라 순간 순간의 있음이다. 영원한 것이 어디 있는가. 모두가 한때일 뿐. 그러나 그 한때를 최선을 다해 최대한으로 살 수 있어야 한다. 삶은 놀라운 신비요 아름다움이다.", "법정 스님"),
//            Quote("꿈을 계속 간직하고 있으면 반드시 실현할 때가 온다.", "괴테"),
//            Quote("화려한 일을 추구하지 말라. 중요한 것은 스스로의 재능이며, 자신의 행동에 쏟아 붓는 사랑의 정도이다.", "마더 테레사"),
//            Quote("마음만을 가지고 있어서는 안 된다. 반드시 실천하여야 한다.", "이소룡"),
//            Quote("흔히 사람들은 기회를 기다리고 있지만 기회는 기다리는 사람에게 잡히지 않는 법이다. 우리는 기회를 기다리는 사람이 되기 전에 기회를 얻을 수 있는 실력을 갖춰야 한다. 일에 더 열중하는 사람이 되어야 한다.", "안창호"),
//            Quote("나이가 60이다 70이다 하는 것으로 그 사람이 늙었다 젊었다 할 수 없다. 늙고 젊은 것은 그 사람의 신념이 늙었느냐 젊었느냐 하는데 있다.", "맥아더"),
//            Quote("만약 우리가 할 수 있는 일을 모두 한다면 우리들은 우리 자신에 깜짝 놀랄 것이다.", "에디슨"),
//            Quote("나는 누구인가 스스로 물으라. 자신의 속 얼굴이 드러나 보일 때까지 묻고 묻고 물어야 한다. 건성으로 묻지 말고 목소리 속의 목소리로 귀 속의 귀에 대고 간절하게 물어야 한다. 해답은 그 물음 속에 있다.", "법정 스님"),
//            Quote("행복은 결코 많고 큰 데만 있는 것이 아니다. 작은 것을 가지고도 고마워 하고 만족할 줄 안다면 그는 행복한 사람이다. 여백과 공간의 아름다움은 단순함과 간소함에 있다.", "법정 스님"),
//            Quote("물러나서 조용하게 구하면 배울 수 있는 스승은 많다. 사람은 가는 곳마다 보는 것마다 모두 스승으로서 배울 것이 많은 법이다.", "맹자"),
//            Quote("눈물과 더불어 빵을 먹어 보지 않은 자는 인생의 참다운 맛을 모른다.", "괴테"),
//            Quote("진짜 문제는 사람들의 마음이다. 그것은 절대로 물리학이나 윤리학의 문제가 아니다.", "아인슈타인"),
//            Quote("해야 할 것을 하라. 모든 것은 타인의 행복을 위해서, 동시에 특히 나의 행복을 위해서이다.", "톨스토이"),
//            Quote("사람이 여행을 하는 것은 도착하기 위해서가 아니라 여행하기 위해서이다.", "괴테"),
//            Quote("화가 날 때는 100까지 세라. 최악일 때는 욕설을 퍼부어라.", "마크 트웨인"),
//            Quote("재산을 잃은 사람은 많이 잃은 것이고, 친구를 잃은 사람은 더 많이 잃은 것이며, 용기를 잃은 사람은 모든 것을 잃은 것이다.", "세르반테스"),
//            Quote("돈이란 바닷물과도 같다. 그것은 마시면 마실수록 목이 말라진다.", "쇼펜하우어"),
//            Quote("용기 있는 자로 살아라. 운이 따라주지 않는다면 용기 있는 가슴으로 불행을 이겨내라.", "발타사르 그라시안"),
//            Quote("사람은 가는 곳마다 보는 것마다 배울 것이 많은 법이다.", "벤자민 프랭클린"),
//            Quote("준비하지 않은 자는 기회가 와도 소용없다.", "알렉시스 드 토크빌"),
//            Quote("노력에 집착하라. 숙명적인 노력을.", "레오나르도 다 빈치"),
//            Quote("내일이란 오늘의 다른 이름일 뿐.", "윌리엄 포크너"),
//            Quote("강인한 의지 없이는 뛰어난 재능도 없다.", "오노레 드 발자크"),
//            Quote("나는 날마다 모든 면에서 점점 좋아지고 있다.", "에밀쿠에"),
//            Quote("불가능한 일을 해보는 것은 신나는 일이다.", "월트 디즈니"),
//            Quote("할 수 있다고 믿는 사람은 결국 그렇게 된다.", "샤론 드골"),
//            Quote("당신이 포기할 때, 나는 시작한다.", "엘론 머스크"),
//            Quote("나는 이룰 때까지 노력할 것이다.", "브라이언 트레이시"),
//            Quote("한번 포기하면 습관이 된다. 절대 포기하지 말아라.", "마이클 조던")
//        )
//
//        quotes.forEach { quote ->
//            db.collection("wising")
//                .add(quote)
//                .addOnSuccessListener { documentReference ->
//                    Log.d("TAG", "DocumentSnapshot written with ID: ${documentReference}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w("TAG", "Error adding document", e)
//                }
//        }

        viewPager2 = binding.viewPager
        initView()

        return binding.root
    }

    private fun initView() {
        quoteList.add(Quote(id = "개발자전용", quote = "화면을 위로 쓸어\n삶에 한줄을 추가해봐요!", name = "개발자"))
        loadQuotes()

        viewPager2?.adapter = ViewPagerAdapter(quoteList, requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE).getString("androidId", "").toString())
        viewPager2?.orientation = ViewPager2.ORIENTATION_VERTICAL

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == quoteList.size - 1 - 1 && !isLoading) { // 9번째 항목에 도달
                    loadQuotes()
                }
            }
        })
    }

    private fun loadQuotes() {
        if (isLoading) return
        isLoading = true

        var query = db.collection("wising").limit(10)
        lastVisibleDocument?.let {
            query = query.startAfter(it)
        }

        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) return@addOnSuccessListener

                for (document in result) {
                    quoteList.add(Quote(id = document.id, quote = document.data["quote"].toString(), name = document.data["name"].toString()))
                }
                lastVisibleDocument = result.documents[result.size() - 1]
                viewPager2?.adapter?.notifyDataSetChanged()
                isLoading = false
            }
            .addOnFailureListener { exception ->
                Log.w(Constant.TAG, "Error getting documents.", exception)
                isLoading = false
            }
    }
}
