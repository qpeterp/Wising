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
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 데이터 추가 함수 호출

//        val quotes = listOf(
//            BookMarkData("스티븐 스필버그", "위대한 예술가는 감정을 담아낼 수 있는 사람이다."),
//            BookMarkData("마이클 조던", "나는 실패를 두려워하지 않는다. 실패가 나를 더 강하게 만든다."),
//            BookMarkData("일론 머스크", "우리는 우주를 탐험해야 한다. 인류의 미래는 우주에 있다."),
//            BookMarkData("스티브 잡스", "혁신은 리더와 추종자를 구분짓는다."),
//            BookMarkData("조지 루카스", "별을 넘어서는 꿈을 꾸어라."),
//            BookMarkData("오프라 윈프리", "자신을 믿는 것이 모든 것을 시작하는 첫걸음이다."),
//            BookMarkData("엘론 머스크", "인류는 다가오는 위협에 대비해야 한다. 지속 가능한 에너지를 개발하자."),
//            BookMarkData("테슬라", "미래를 만드는 것은 상상력과 기술의 융합이다."),
//            BookMarkData("마틴 루터 킹 주니어", "나에게는 꿈이 있습니다. 평등과 정의를 위한 꿈입니다."),
//            BookMarkData("알베르트 아인슈타인", "상상력은 지식보다 중요하다."),
//            BookMarkData("넬슨 만델라", "세상은 나의 마음속에 있는 것이 아니라, 나의 마음속에 있다."),
//            BookMarkData("레오나르도 다 빈치", "무엇이든지 다르게 보일 수 있다. 그것이 창의성이다."),
//            BookMarkData("빈센트 반 고흐", "행복은 작업 속에 있다. 작업에 집중하면 모든 것이 잘 될 것이다."),
//            BookMarkData("빌 게이츠", "성공은 나쁜 교사다. 성공은 똑똑한 사람들이 자신이 실패하지 않을 것이라고 생각하게 만든다."),
//            BookMarkData("마크 주커버그", "가장 중요한 것은 당신이 올바른 것을 만들고 있다는 확신을 갖는 것이다."),
//            BookMarkData("헨리 포드", "성공은 목표를 세우고 이를 달성하기 위해 행동하는 것이다."),
//            BookMarkData("톰 행크스", "우리는 모두 서로의 이야기를 이해할 수 있는 능력을 갖추고 있다."),
//            BookMarkData("조지 오웰", "진실을 말하는 것은 힘든 일이다. 그러나 진실은 항상 존중받아야 한다."),
//            BookMarkData("자크 쿠스토", "바다는 우리가 아직 모르는 많은 것을 가지고 있다."),
//            BookMarkData("세스 고딘", "리더는 사람들을 변화를 위해 동기 부여하고 영감을 주는 사람이다."),
//            BookMarkData("아이작 뉴턴", "나는 넓은 바다에서 항해하는 작은 배에 지나지 않는다."),
//            BookMarkData("프리드리히 니체", "무엇이 나를 죽게 만들지 않는다면, 그것이 나를 더 강하게 만든다."),
//            BookMarkData("윌리엄 셰익스피어", "자신의 진정한 모습을 찾아야 한다."),
//            BookMarkData("리처드 브랜슨", "세상에서 가장 중요한 것은 사람들이 당신을 어떻게 느끼게 하는가이다."),
//            BookMarkData("피카소", "창의성은 모든 것을 재창조하는 것이다."),
//            BookMarkData("로버트 다우니 주니어", "과거는 지나갔고, 미래는 오늘 내가 어떻게 행동하느냐에 달려 있다."),
//            BookMarkData("하워드 슐츠", "성공적인 사업은 사람들의 삶을 변화시키는 것이다."),
//            BookMarkData("조지 부시", "국가의 성공은 국민의 노력과 헌신에 달려 있다."),
//            BookMarkData("헬렌 켈러", "인생에서 가장 큰 어려움은 보이지 않는 것이 아니라 느끼지 않는 것이다."),
//            BookMarkData("안젤리나 졸리", "모든 사람에게 그들만의 이야기가 있다. 그것을 존중해야 한다."),
//            BookMarkData("마르틴 루터 킹 주니어", "자유가 없으면 인권도 없다."),
//            BookMarkData("장 폴 사르트르", "우리는 우리 자신을 만든다."),
//            BookMarkData("파블로 네루다", "사랑은 인생에서 가장 중요한 것이다."),
//            BookMarkData("찰스 다윈", "생존에 필요한 것은 가장 강한 것이 아니라 적응할 수 있는 것이다."),
//            BookMarkData("셰릴 샌드버그", "여성의 리더십은 조직을 더 강하게 만든다."),
//            BookMarkData("조지 마틴", "꿈은 실현될 수 있는 것이다."),
//            BookMarkData("앤 마리 슬로터", "리더는 사람들이 함께 일하도록 동기를 부여하는 사람이다."),
//            BookMarkData("말콤 X", "자신을 믿고 싸우는 것이 중요하다."),
//            BookMarkData("마야 안젤루", "사람들은 당신이 한 일은 잊을 수 있지만, 당신이 어떻게 느끼게 했는지는 잊지 않을 것이다."),
//            BookMarkData("미셸 오바마", "자신의 이야기를 통해 다른 사람들에게 영감을 주는 것이 중요하다."),
//            BookMarkData("오스카 와일드", "우리는 우리가 가장 좋아하는 사람과 가장 비슷해진다."),
//            BookMarkData("앨버트 아인슈타인", "성공은 능력이 아니라 열정에서 나온다."),
//            BookMarkData("프랭클린 D. 루즈벨트", "우리가 두려워해야 하는 것은 두려움 그 자체이다."),
//            BookMarkData("테디 루즈벨트", "행동이 없는 말은 소용이 없다."),
//            BookMarkData("헤르만 헤세", "자신을 이해하는 것이 가장 중요한 일이다."),
//            BookMarkData("르네 데카르트", "나는 생각한다. 고로 나는 존재한다."),
//            BookMarkData("피에르 드 쿠베르탱", "올림픽은 모든 국가의 평화를 위한 것이다."),
//            BookMarkData("루치아노 파바로티", "음악은 세상의 모든 언어를 초월하는 힘이 있다."),
//            BookMarkData("레이 크록", "성공은 품질과 헌신에서 온다."),
//            BookMarkData("지미 카터", "우리의 업적은 우리의 의도와 헌신에 달려 있다."),
//            BookMarkData("자크 프레베르", "시가는 삶의 본질을 포착하는 것이다."),
//            BookMarkData("폴 알렉산더", "기술의 발전은 인류의 미래를 밝히는 열쇠이다."),
//            BookMarkData("마리 퀴리", "과학은 진리와 희망을 제공한다."),
//            BookMarkData("찰리 채플린", "웃음은 인간의 가장 큰 보상이다."),
//            BookMarkData("존 F. 케네디", "변화를 두려워하지 말고, 변화를 만들어라."),
//            BookMarkData("세르게이 브린", "문제를 해결하는 것이 중요하다."),
//            BookMarkData("바락 오바마", "희망을 가지고 행동하는 것이 중요하다."),
//            BookMarkData("위트니 휴스턴", "자신을 믿고 도전하는 것이 가장 중요하다."),
//            BookMarkData("앤디 워홀", "예술은 누구나 할 수 있는 것이다."),
//            BookMarkData("미하일 고르바초프", "변화는 혁신에서 시작된다."),
//            BookMarkData("스티븐 호킹", "우리는 우주를 이해하기 위해 계속 도전해야 한다."),
//            BookMarkData("리차드 도킨스", "생명은 유전자의 수호자이다."),
//            BookMarkData("스티븐 제이 굴드", "과학은 지식의 탐구이다."),
//            BookMarkData("로저 페더러", "열정과 헌신이 성공의 열쇠이다."),
//            BookMarkData("마이클 잭슨", "음악은 사람들의 마음을 움직인다."),
//            BookMarkData("조지 마르셀", "글로벌화는 새로운 기회를 제공한다."),
//            BookMarkData("제임스 카메론", "영화는 상상력의 결과물이다."),
//            BookMarkData("조지 시걸", "인간의 삶은 상상력과 창의성으로 가득 차 있다."),
//            BookMarkData("톰 크루즈", "성공은 노력을 통해 얻어진다."),
//            BookMarkData("아놀드 슈왈제네거", "성공적인 삶은 긍정적인 태도에서 시작된다."),
//            BookMarkData("조지 클루니", "영화는 사람들의 감정을 자극한다."),
//            BookMarkData("라이언 레이놀즈", "진정성은 모든 것을 가능하게 한다."),
//            BookMarkData("신디 크로포드", "자신을 믿는 것이 가장 중요하다."),
//            BookMarkData("클린턴", "정직과 신뢰가 가장 중요하다."),
//            BookMarkData("마르셀 프루스트", "추억은 가장 소중한 것이다."),
//            BookMarkData("폴 매카트니", "음악은 인생의 동반자이다."),
//            BookMarkData("엘튼 존", "자신의 꿈을 추구하는 것이 중요하다."),
//            BookMarkData("셰릴 크로우", "자신의 길을 찾는 것이 가장 중요하다."),
//            BookMarkData("바이런", "자유는 인류의 가장 큰 보물이다."),
//            BookMarkData("게르트 루다", "창의성은 인류의 진보를 이끈다."),
//            BookMarkData("펭귄", "책은 세상을 바꿀 수 있는 힘이 있다."),
//            BookMarkData("제임스 패터슨", "작가는 독자의 상상력을 자극하는 사람이다."),
//            BookMarkData("메리 울스턴크래프트", "평등은 인류의 가장 큰 목표이다."),
//            BookMarkData("올리버 트위스트", "희망은 삶의 원동력이다."),
//            BookMarkData("로렌스 오리언", "모험은 삶을 풍요롭게 만든다."),
//            BookMarkData("그레이스 켈리", "자신의 길을 찾는 것이 가장 중요하다."),
//            BookMarkData("제리 라이언", "성공은 꾸준한 노력의 결과이다."),
//            BookMarkData("잭 웰치", "리더십은 사람들을 성공으로 이끄는 것이다."),
//            BookMarkData("클라우디아 시프", "문학은 인간의 본성을 이해하는 열쇠이다."),
//            BookMarkData("제임스 딘", "자신을 믿고 도전하는 것이 중요하다."),
//            BookMarkData("지미 헨드릭스", "음악은 영혼의 표현이다."),
//            BookMarkData("데이비드 보위", "자신의 길을 가는 것이 중요하다."),
//            BookMarkData("제리 스필버그", "꿈을 이루기 위해서는 끊임없이 노력해야 한다."),
//            BookMarkData("에디 머피", "웃음은 인생의 최고의 약이다."),
//            BookMarkData("사라 제시카 파커", "자신의 꿈을 따르는 것이 중요하다."),
//            BookMarkData("드류 베리모어", "행복은 순간순간의 작은 것들에 있다."),
//            BookMarkData("로버트 드 니로", "연기는 삶의 가장 진솔한 표현이다."),
//            BookMarkData("윌 스미스", "긍정적인 태도가 성공의 열쇠이다."),
//            BookMarkData("앨버트 아인슈타인", "상상력은 지식보다 중요하다."),
//            BookMarkData("스티븐 스필버그", "위대한 예술가는 감정을 담아낼 수 있는 사람이다."),
//            BookMarkData("엘론 머스크", "인류는 다가오는 위협에 대비해야 한다. 지속 가능한 에너지를 개발하자."),
//            BookMarkData("조지 루카스", "별을 넘어서는 꿈을 꾸어라."),
//            BookMarkData("오프라 윈프리", "자신을 믿는 것이 모든 것을 시작하는 첫걸음이다."),
//            BookMarkData("레오나르도 다 빈치", "무엇이든지 다르게 보일 수 있다. 그것이 창의성이다."),
//            BookMarkData("빈센트 반 고흐", "행복은 작업 속에 있다. 작업에 집중하면 모든 것이 잘 될 것이다."),
//            BookMarkData("빌 게이츠", "성공은 나쁜 교사다. 성공은 똑똑한 사람들이 자신이 실패하지 않을 것이라고 생각하게 만든다."),
//            BookMarkData("마크 주커버그", "가장 중요한 것은 당신이 올바른 것을 만들고 있다는 확신을 갖는 것이다."),
//            BookMarkData("헨리 포드", "성공은 목표를 세우고 이를 달성하기 위해 행동하는 것이다."),
//            BookMarkData("톰 행크스", "우리는 모두 서로의 이야기를 이해할 수 있는 능력을 갖추고 있다."),
//            BookMarkData("조지 오웰", "진실을 말하는 것은 힘든 일이다. 그러나 진실은 항상 존중받아야 한다."),
//            BookMarkData("자크 쿠스토", "바다는 우리가 아직 모르는 많은 것을 가지고 있다."),
//            BookMarkData("세스 고딘", "리더는 사람들을 변화를 위해 동기 부여하고 영감을 주는 사람이다."),
//            BookMarkData("아이작 뉴턴", "나는 넓은 바다에서 항해하는 작은 배에 지나지 않는다."),
//            BookMarkData("프리드리히 니체", "무엇이 나를 죽게 만들지 않는다면, 그것이 나를 더 강하게 만든다."),
//            BookMarkData("윌리엄 셰익스피어", "자신의 진정한 모습을 찾아야 한다."),
//            BookMarkData("리처드 브랜슨", "세상에서 가장 중요한 것은 사람들이 당신을 어떻게 느끼게 하는가이다."),
//            BookMarkData("피카소", "창의성은 모든 것을 재창조하는 것이다."),
//            BookMarkData("로버트 다우니 주니어", "과거는 지나갔고, 미래는 오늘 내가 어떻게 행동하느냐에 달려 있다."),
//            BookMarkData("하워드 슐츠", "성공적인 사업은 사람들의 삶을 변화시키는 것이다."),
//            BookMarkData("조지 부시", "국가의 성공은 국민의 노력과 헌신에 달려 있다."),
//            BookMarkData("헬렌 켈러", "인생에서 가장 큰 어려움은 보이지 않는 것이 아니라 느끼지 않는 것이다."),
//            BookMarkData("안젤리나 졸리", "모든 사람에게 그들만의 이야기가 있다. 그것을 존중해야 한다."),
//            BookMarkData("마르틴 루터 킹 주니어", "자유가 없으면 인권도 없다."),
//            BookMarkData("장 폴 사르트르", "우리는 우리 자신을 만든다."),
//            BookMarkData("파블로 네루다", "사랑은 인생에서 가장 중요한 것이다."),
//            BookMarkData("찰스 다윈", "생존에 필요한 것은 가장 강한 것이 아니라 적응할 수 있는 것이다."),
//            BookMarkData("셰릴 샌드버그", "여성의 리더십은 조직을 더 강하게 만든다."),
//            BookMarkData("조지 마르셀", "꿈은 실현될 수 있는 것이다."),
//            BookMarkData("앤 마리 슬로터", "리더는 사람들이 함께 일하도록 동기를 부여하는 사람이다."),
//            BookMarkData("말콤 X", "자신을 믿고 싸우는 것이 중요하다."),
//            BookMarkData("마야 안젤루", "사람들은 당신이 한 일은 잊을 수 있지만, 당신이 어떻게 느끼게 했는지는 잊지 않을 것이다."),
//            BookMarkData("미셸 오바마", "자신의 이야기를 통해 다른 사람들에게 영감을 주는 것이 중요하다."),
//            BookMarkData("오스카 와일드", "우리는 우리가 가장 좋아하는 사람과 가장 비슷해진다."),
//            BookMarkData("앨버트 아인슈타인", "성공은 능력이 아니라 열정에서 나온다."),
//            BookMarkData("프랭클린 D. 루즈벨트", "우리가 두려워해야 하는 것은 두려움 그 자체이다."),
//            BookMarkData("테디 루즈벨트", "행동이 없는 말은 소용이 없다."),
//            BookMarkData("헤르만 헤세", "자신을 이해하는 것이 가장 중요한 일이다."),
//            BookMarkData("르네 데카르트", "나는 생각한다. 고로 나는 존재한다."),
//            BookMarkData("피에르 드 쿠베르탱", "올림픽은 모든 국가의 평화를 위한 것이다."),
//            BookMarkData("루치아노 파바로티", "음악은 세상의 모든 언어를 초월하는 힘이 있다."),
//            BookMarkData("레이 크록", "성공은 품질과 헌신에서 온다."),
//            BookMarkData("지미 카터", "우리의 업적은 우리의 의도와 헌신에 달려 있다."),
//            BookMarkData("자크 프레베르", "시가는 삶의 본질을 포착하는 것이다."),
//            BookMarkData("폴 알렉산더", "기술의 발전은 인류의 미래를 밝히는 열쇠이다."),
//            BookMarkData("마리 퀴리", "과학은 진리와 희망을 제공한다."),
//            BookMarkData("찰리 채플린", "웃음은 인간의 가장 큰 보상이다."),
//            BookMarkData("존 F. 케네디", "변화를 두려워하지 말고, 변화를 만들어라."),
//            BookMarkData("세르게이 브린", "문제를 해결하는 것이 중요하다."),
//            BookMarkData("바락 오바마", "희망을 가지고 행동하는 것이 중요하다."),
//            BookMarkData("위트니 휴스턴", "자신을 믿고 도전하는 것이 가장 중요하다."),
//            BookMarkData("앤디 워홀", "예술은 누구나 할 수 있는 것이다."),
//            BookMarkData("미하일 고르바초프", "변화는 혁신에서 시작된다."),
//            BookMarkData("스티븐 호킹", "우리는 우주를 이해하기 위해 계속 도전해야 한다."),
//            BookMarkData("리차드 도킨스", "생명은 유전자의 수호자이다."),
//            BookMarkData("스티븐 제이 굴드", "과학은 지식의 탐구이다."),
//            BookMarkData("로저 페더러", "열정과 헌신이 성공의 열쇠이다."),
//            BookMarkData("마이클 잭슨", "음악은 사람들의 마음을 움직인다."),
//            BookMarkData("조지 마르셀", "글로벌화는 새로운 기회를 제공한다."),
//            BookMarkData("제임스 카메론", "영화는 상상력의 결과물이다."),
//            BookMarkData("조지 시걸", "인간의 삶은 상상력과 창의성으로 가득 차 있다."),
//            BookMarkData("톰 크루즈", "성공은 노력을 통해 얻어진다."),
//            BookMarkData("아놀드 슈왈제네거", "성공적인 삶은 긍정적인 태도에서 시작된다."),
//            BookMarkData("조지 클루니", "영화는 사람들의 감정을 자극한다."),
//            BookMarkData("라이언 레이놀즈", "진정성은 모든 것을 가능하게 한다."),
//            BookMarkData("신디 크로포드", "자신을 믿는 것이 가장 중요하다."),
//            BookMarkData("클린턴", "정직과 신뢰가 가장 중요하다."),
//            BookMarkData("마르셀 프루스트", "추억은 가장 소중한 것이다."),
//            BookMarkData("폴 매카트니", "음악은 인생의 동반자이다."),
//            BookMarkData("엘튼 존", "자신의 꿈을 추구하는 것이 중요하다."),
//            BookMarkData("셰릴 크로우", "자신의 길을 찾는 것이 가장 중요하다."),
//            BookMarkData("바이런", "자유는 인류의 가장 큰 보물이다."),
//            BookMarkData("게르트 루다", "창의성은 인류의 진보를 이끈다."),
//            BookMarkData("펭귄", "책은 세상을 바꿀 수 있는 힘이 있다."),
//            BookMarkData("제임스 패터슨", "작가는 독자의 상상력을 자극하는 사람이다."),
//            BookMarkData("메리 울스턴크래프트", "평등은 인류의 가장 큰 목표이다."),
//            BookMarkData("올리버 트위스트", "희망은 삶의 원동력이다."),
//            BookMarkData("로렌스 오리언", "모험은 삶을 풍요롭게 만든다."),
//            BookMarkData("그레이스 켈리", "자신의 길을 찾는 것이 가장 중요하다."),
//            BookMarkData("제리 라이언", "성공은 꾸준한 노력의 결과이다."),
//            BookMarkData("잭 웰치", "리더십은 사람들을 성공으로 이끄는 것이다."),
//            BookMarkData("클라우디아 시프", "문학은 인간의 본성을 이해하는 열쇠이다."),
//            BookMarkData("제임스 딘", "자신을 믿고 도전하는 것이 중요하다."),
//            BookMarkData("지미 헨드릭스", "음악은 영혼의 표현이다."),
//            BookMarkData("데이비드 보위", "자신의 길을 가는 것이 중요하다."),
//            BookMarkData("제리 스필버그", "꿈을 이루기 위해서는 끊임없이 노력해야 한다."),
//            BookMarkData("에디 머피", "웃음은 인생의 최고의 약이다."),
//            BookMarkData("사라 제시카 파커", "자신의 꿈을 따르는 것이 중요하다."),
//            BookMarkData("드류 베리모어", "행복은 순간순간의 작은 것들에 있다."),
//            BookMarkData("로버트 드 니로", "연기는 삶의 가장 진솔한 표현이다."),
//            BookMarkData("윌 스미스", "긍정적인 태도가 성공의 열쇠이다."),
//            BookMarkData("헨리 데이비드 소로", "진정한 성공은 자신의 삶에 만족하는 것이다."),
//            BookMarkData("코코 샤넬", "우아함은 진정성에서 비롯된다."),
//            BookMarkData("알베르 카뮈", "인생은 부조리하지만, 우리는 그 안에서 의미를 찾아야 한다."),
//            BookMarkData("마하트마 간디", "변화가 되기를 원하는 것은 자신이 먼저 변화해야 한다."),
//            BookMarkData("파울로 코엘료", "모든 꿈은 실현될 수 있다. 다만 시간이 필요할 뿐이다."),
//            BookMarkData("아나이스 닌", "우리는 모두 우리 내면의 세계에서 시작된다."),
//            BookMarkData("찰리 채플린", "인생에서 가장 중요한 것은 웃음과 사랑이다."),
//            BookMarkData("존 레논", "상상력은 모든 것을 가능하게 만든다."),
//            BookMarkData("스티브 잡스", "창의성은 연결을 만드는 것이다."),
//            BookMarkData("마르셀 프루스트", "우리는 과거의 모든 것을 기억할 수 없다. 그러나 그것이 우리의 일부이다."),
//            BookMarkData("빅터 프랭클", "인생의 의미를 찾는 것은 개인의 책임이다."),
//            BookMarkData("제인 오스틴", "자신을 사랑하는 것이 다른 사람을 사랑하는 첫걸음이다."),
//            BookMarkData("윌리엄 포크너", "사람은 자신의 선택으로 자신을 정의한다."),
//            BookMarkData("호머", "인내는 모든 것의 어머니이다."),
//            BookMarkData("피터 드러커", "가장 중요한 것은 무엇을 하고 있는가가 아니라 어떻게 하는가이다."),
//            BookMarkData("버락 오바마", "변화는 우리가 만드는 것이다."),
//            BookMarkData("셰릴 샌드버그", "리더십은 타인을 이끌어가는 것이 아니라, 타인과 함께 나아가는 것이다."),
//            BookMarkData("조지 에일리엇", "자신의 길을 찾는 것은 인생의 가장 큰 모험이다."),
//            BookMarkData("아이작 아시모프", "지식은 미래를 만드는 열쇠이다."),
//            BookMarkData("오드리 햅번", "우리는 사랑으로만 세상을 바꿀 수 있다."),
//            BookMarkData("알버트 아인슈타인", "상상력은 지식보다 중요하다."),
//            BookMarkData("마틴 루터 킹 Jr.", "당신이 보지 못하는 미래를 꿈꾸는 것이 중요하다."),
//            BookMarkData("에이브러햄 링컨", "모든 사람은 성공할 기회를 가져야 한다."),
//            BookMarkData("넬슨 만델라", "자유는 우리의 가장 큰 보물이다."),
//            BookMarkData("에픽테토스", "우리의 행동은 우리의 삶을 정의한다."),
//            BookMarkData("프리드리히 니체", "자신을 극복하는 것이 가장 중요한 도전이다."),
//            BookMarkData("존 스튜어트 밀", "자유는 자신이 원하는 대로 살 수 있는 권리이다."),
//            BookMarkData("마르틴 하이데거", "존재는 시간을 통한 경험이다."),
//            BookMarkData("에리히 프롬", "진정한 사랑은 상대방의 자유를 존중하는 것이다."),
//            BookMarkData("메리 안드로스", "행동하지 않는 것은 실패를 선택하는 것이다."),
//            BookMarkData("헨리 포드", "성공은 실패를 반복하는 것이 아니라, 포기하지 않는 것이다."),
//            BookMarkData("루이스 캐럴", "꿈꾸지 않으면 아무것도 이룰 수 없다."),
//            BookMarkData("조지 워싱턴", "진정한 지도력은 남을 돕는 것이다."),
//            BookMarkData("윌리엄 셰익스피어", "자신을 알면 인생의 절반은 이룬 것이다."),
//            BookMarkData("로버트 프로스트", "길이 두 개 있을 때 선택이 중요하다."),
//            BookMarkData("버나드 쇼", "인생은 당신이 만들고자 하는 대로 만든다."),
//            BookMarkData("빌 게이츠", "성공은 열정과 준비가 만나서 이루어진다."),
//            BookMarkData("마하트마 간디", "당신이 보고 싶은 변화가 되십시오."),
//            BookMarkData("칼 융", "자신의 그림자를 직면하는 것이 성장의 시작이다."),
//            BookMarkData("제임스 딘", "인생은 단 한 번만 주어진다. 그러니 최선을 다하라."),
//            BookMarkData("호프 스프링", "우리는 자신의 길을 만들어간다."),
//            BookMarkData("찰리 채플린", "인생에서 가장 큰 모험은 자신의 길을 가는 것이다."),
//            BookMarkData("존 우든", "성공은 준비된 마음이 기회를 만났을 때 온다."),
//            BookMarkData("오프라 윈프리", "자신의 꿈을 믿는 것이 첫걸음이다."),
//            BookMarkData("테레사 수녀", "사랑은 우리가 할 수 있는 모든 것이다."),
//            BookMarkData("프랭클린 D. 루스벨트", "두려움은 우리의 적이다."),
//            BookMarkData("피터 드러커", "미래는 우리가 어떻게 준비하느냐에 달려 있다."),
//            BookMarkData("로버트 그린", "자기 자신을 아는 것이 가장 중요한 지혜이다."),
//            BookMarkData("헨리 데이비드 소로", "자연과의 접촉은 영혼을 깨우는 것이다."),
//            BookMarkData("알렉산더 그레이엄 벨", "성공은 끊임없는 도전에서 온다."),
//            BookMarkData("버트런드 러셀", "지식은 힘이다."),
//            BookMarkData("리차드 브랜슨", "모험을 두려워하지 말라."),
//            BookMarkData("밀턴 에릭슨", "상상력은 인생을 변화시킨다."),
//            BookMarkData("피카소", "자유는 창조성을 낳는다."),
//            BookMarkData("루이스 브라일", "절망 속에서도 희망을 찾는다."),
//            BookMarkData("마거릿 미드", "작은 그룹이 세상을 변화시킬 수 있다."),
//            BookMarkData("로저 밴스턴", "삶에서 중요한 것은 당신이 살아가는 방식이다."),
//            BookMarkData("아리스토텔레스", "행복은 올바른 행동에서 온다."),
//            BookMarkData("앤드류 카네기", "행복은 나누는 것이다."),
//            BookMarkData("아브라함 링컨", "자유는 모든 이의 권리이다."),
//            BookMarkData("메리 커리", "진리는 당신의 가장 큰 친구이다."),
//            BookMarkData("조지 오웰", "자유는 무지에서 오는 것이다."),
//            BookMarkData("제프 베조스", "가장 중요한 것은 고객을 이해하는 것이다."),
//            BookMarkData("스티브 잡스", "단순함이 가장 고급스러운 것이다."),
//            BookMarkData("어니스트 헤밍웨이", "인생은 스스로 결정하는 것이다."),
//            BookMarkData("프랭클린 D. 루스벨트", "우리는 두려움 그 자체만을 두려워해야 한다."),
//            )

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

        adapter = ViewPagerAdapter(quoteList, requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE).getString("androidId", "").toString())
        viewPager2?.adapter = adapter
        viewPager2?.orientation = ViewPager2.ORIENTATION_VERTICAL

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == quoteList.size - 2 && !isLoading) { // 9번째 항목에 도달
                    loadQuotes()
                }
            }
        })
    }

    private fun loadQuotes() {
        if (isLoading) return
        isLoading = true

        var query = db.collection("wising").limit(30)
        lastVisibleDocument?.let {
            query = query.startAfter(it)
        }

        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) return@addOnSuccessListener

                // 새로운 데이터만 섞기
                val newQuotes = result.map { document ->
                    Quote(id = document.id, quote = document.data["quote"].toString(), name = document.data["name"].toString())
                }.shuffled()

                quoteList.addAll(newQuotes)

                Log.d(Constant.TAG, "BooksFragment loadQuotes quoteList is $quoteList")

                lastVisibleDocument = result.documents[result.size() - 1]
                adapter.notifyDataSetChanged()
                isLoading = false
            }
            .addOnFailureListener { exception ->
                Log.w(Constant.TAG, "Error getting documents.", exception)
                isLoading = false
            }
    }
}