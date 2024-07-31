package com.qpeterp.wising.ui.bottom.qoutes

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.data.BookMarkData
import com.qpeterp.wising.data.Quote
import kotlin.random.Random

class BookmarkManager(userId: String) {
    private val db = FirebaseFirestore.getInstance()
    private val bookmarksRef = db.collection("users").document(userId).collection("bookmarks")
    private var bookmarks = mutableListOf<String>()

    fun addBookmark(quoteId: String) {
        val bookmark = hashMapOf(
            "quoteId" to quoteId,
        )
        bookmarksRef.document(quoteId).set(bookmark)
            .addOnSuccessListener {
                Log.d(Constant.TAG, "BookmarkManager Bookmark added successfully ${quoteId}")
            }
            .addOnFailureListener { e ->
                Log.w(Constant.TAG, "BookmarkManager Error adding bookmark", e)
            }
    }

    fun removeBookmark(quoteId: String) {
        bookmarksRef.document(quoteId).delete()
            .addOnSuccessListener {
                Log.d(Constant.TAG, "BookmarkManager Bookmark removed successfully")
            }
            .addOnFailureListener { e ->
                Log.w(Constant.TAG, "BookmarkManager Error removing bookmark", e)
            }
    }

    fun getBookmarks(callback: (List<String>) -> Unit) {
        bookmarksRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    bookmarks.add(document.id)
                }
                callback(bookmarks)
            }
            .addOnFailureListener { e ->
                Log.w(Constant.TAG, "BookmarkManager Error getting bookmarks", e)
                callback(emptyList())
            }
    }

    fun getQuote(quoteId: String, callback: (BookMarkData?) -> Unit) {
        // 'quotes'는 명언이 저장된 컬렉션의 이름입니다. 적절한 이름으로 변경하세요.
        val quoteRef = db.collection("wising").document(quoteId)

        quoteRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // 문서가 존재할 경우, 데이터 추출
                    val quote = document.toObject(BookMarkData::class.java)
                    Log.d(Constant.TAG, "BookMarkManager getQuote quote: $quote")
                    callback(quote)
                } else {
                    // 문서가 존재하지 않는 경우
                    Log.d(Constant.TAG, "BookMarkManager getQuote No such document")
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                // 쿼리 실패 시
                Log.w(Constant.TAG, "Error getting document", e)
                callback(null)
            }
    }

    fun getRandomQuote(callback: (Quote?) -> Unit) {
        val quoteRef = db.collection("wising")

        quoteRef.get()
            .addOnSuccessListener { result ->
                val documentList = result.documents
                if (documentList.isNotEmpty()) {
                    val randomIndex = Random.nextInt(documentList.size)
                    val randomDocument = documentList[randomIndex]
                    Log.d(Constant.TAG, randomDocument.id)
                    val bookMarkData = randomDocument.toObject(BookMarkData::class.java)
                    if (bookMarkData != null) {
                        val quote = Quote(
                            id = randomDocument.id,
                            name = bookMarkData.name,
                            quote = bookMarkData.quote
                        )
                        callback(quote)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                callback(null)
            }
    }

}
