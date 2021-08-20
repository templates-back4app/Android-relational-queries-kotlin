package com.emre.android_relational_queries_kotlin

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.*

class MainActivity : AppCompatActivity() {

    var queryA: Button? = null
    var queryB: Button? = null
    var queryC: Button? = null
    var clearResults: Button? = null
    private var adapter: ResultAdapter? = null
    private var progressDialog: ProgressDialog? = null
    private var resultList: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        queryA = findViewById(R.id.queryA)
        queryB = findViewById(R.id.queryB)
        queryC = findViewById(R.id.queryC)
        clearResults = findViewById(R.id.clearResults)
        resultList = findViewById(R.id.resultList)
        progressDialog = ProgressDialog(this)

        queryA?.setOnClickListener {
            progressDialog?.show()
            val publisherQuery = ParseQuery<ParseObject>("Publisher")
            publisherQuery.whereEqualTo("name", "Acacia Publishings")
            try {
                val publisher = publisherQuery.first
                val bookQuery = ParseQuery<ParseObject>("Book")
                bookQuery.whereEqualTo("publisher", publisher)
                bookQuery.findInBackground { objects: List<ParseObject>?, e: ParseException? ->
                    progressDialog?.hide()
                    if (e == null) {
                        initData(objects!!)
                    } else {
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ParseException) {
                progressDialog?.hide()
                e.printStackTrace()
            }
        }
        queryB?.setOnClickListener {
            progressDialog?.show()
            val bookQuery = ParseQuery<ParseObject>("Book")
            val calendar = Calendar.getInstance()
            calendar[2010, 1, 1, 59, 59] = 59
            val date = calendar.time
            bookQuery.whereGreaterThan("publishingDate", date)
            val bookStoreQuery = ParseQuery<ParseObject>("BookStore")
            bookStoreQuery.whereMatchesQuery("books", bookQuery)
            bookStoreQuery.findInBackground { objects: List<ParseObject>?, e: ParseException? ->
                progressDialog?.hide()
                if (e == null) {
                    initData(objects!!)
                } else {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        queryC?.setOnClickListener {
            progressDialog?.show()
            val authorQuery = ParseQuery<ParseObject>("Author")
            authorQuery.whereEqualTo("name", "Aaron Writer")
            try {
                val authorA = authorQuery.first
                val bookQuery = ParseQuery<ParseObject>("Book")
                bookQuery.whereEqualTo("authors", authorA)
                val bookStoreQuery = ParseQuery<ParseObject>("BookStore")
                bookStoreQuery.whereMatchesQuery("books", bookQuery)
                bookStoreQuery.findInBackground { objects: List<ParseObject>?, e: ParseException? ->
                    progressDialog?.hide()
                    if (e == null) {
                        initData(objects!!)
                    } else {
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ParseException) {
                progressDialog?.hide()
                e.printStackTrace()
            }
        }

        clearResults?.setOnClickListener { adapter!!.clearList() }
    }


    private fun initData(objects: List<ParseObject>) {
        adapter = ResultAdapter(this, objects)
        resultList!!.layoutManager = LinearLayoutManager(this)
        resultList!!.adapter = adapter
    }
}