//new changes that i am making
package com.example.ig_connect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class HomeFragment : Fragment(), NewsItemClicked {
    private lateinit var mAdapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Set up news RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = PostListAdapter(this)
        recyclerView.adapter = mAdapter
        fetchData()

        // Set up mentor RecyclerView
        val mentorList = listOf(
            Mentor("001", "Aarya", "4th Year", "CSE", R.drawable.ic_profile_placeholder),
            Mentor("002", "Jay", "3rd Year", "ECE", R.drawable.ic_profile_placeholder),
            Mentor("003", "Nisha", "2nd Year", "IT", R.drawable.ic_profile_placeholder),
            Mentor("004", "Ravi", "4th Year", "MECH", R.drawable.ic_profile_placeholder),
            Mentor("005", "Zoya", "3rd Year", "CIVIL", R.drawable.ic_profile_placeholder)
        )

        val mentorRecyclerView = view.findViewById<RecyclerView>(R.id.mentorRecyclerView)
        mentorRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mentorRecyclerView.adapter = MentorAdapter(mentorList)

        return view
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=12bae8420e6c4779a9f470d1d7dfd879"
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                Log.d("NewsAPI", "Articles fetched: ${newsArray.size}")
                mAdapter.updateNews(newsArray)
            },
            { error ->
                Log.e("NewsAPI", "Volley Error: ${error.message}")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), item.url.toUri())
    }
}






// old file
//package com.example.ig_connect
//
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.browser.customtabs.CustomTabsIntent
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.android.volley.Request
//import com.android.volley.toolbox.JsonObjectRequest
//import androidx.core.net.toUri
//
//
//class HomeFragment : Fragment(), NewsItemClicked {
//    private lateinit var mAdapter:PostListAdapter
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_home, container, false)
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
//        //val recyclerView = view.findViewById(/* id = */ R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//       // val adapter:PostListAdapter= PostListAdapter(this)
//        mAdapter= PostListAdapter(this)
//        recyclerView.adapter= mAdapter
//        fetchData()
//        return view
//    }
//    private fun fetchData() {
//        val url = "https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=12bae8420e6c4779a9f470d1d7dfd879"
//        val jsonObjectRequest = object : JsonObjectRequest(
//            Request.Method.GET, url, null,
//            { response ->
//                val newsJsonArray = response.getJSONArray("articles")
//                val newsArray = ArrayList<News>()
//                for (i in 0 until newsJsonArray.length()) {
//                    val newsJsonObject = newsJsonArray.getJSONObject(i)
//                    val news = News(
//                        newsJsonObject.getString("title"),
//                        newsJsonObject.getString("author"),
//                        newsJsonObject.getString("url"),
//                        newsJsonObject.getString("urlToImage") // 👈 yeh correct key hai
//                    )
//                    newsArray.add(news)
//                }
//                Log.d("NewsAPI", "Articles fetched: ${newsArray.size}")
//                mAdapter.updateNews(newsArray)
//            },
//            { error ->
//                Log.e("NewsAPI", "Volley Error: ${error.message}")
//            }
//        ) {
//            override fun getHeaders():MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["User-Agent"] = "Mozilla/5.0"
//                return headers
//            }
//        }
//        MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
//    }
//    override fun onItemClicked(item: News) {
//        val builder=CustomTabsIntent.Builder()
//        val customTabsIntent=builder.build()
//        customTabsIntent.launchUrl(requireContext(), item.url.toUri())
//    }
//    }
//





//    private fun loadPost(){
//        // Instantiate the RequestQueue.
//        //val queue = Volley.newRequestQueue(context)
//        val url = "https://random.dog/woof.json"
//        // Request a string response from the provided URL.
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response -> Log.d("Success Request",response.substring(0,500))
//            },
//            {
//                it.localizedMessage?.let { it1 -> Log.d("error", it1) }
//            })
//    // Add the request to the RequestQueue.
//        //queue.add(stringRequest)
        //MySingleton.getInstance(this).addToRequestQueue(req = jsonObjectRequest)
//    }
