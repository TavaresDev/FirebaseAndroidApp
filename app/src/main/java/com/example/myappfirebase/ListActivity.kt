package com.example.myappfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_restaurant.view.*

class ListActivity : AppCompatActivity() {

    //Connect to Firestore
    val db = FirebaseFirestore.getInstance()
    private var adapter:RestaurantAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)
        //Set our recyclerView to use linear layout
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(this)
        //query the db for all restaurants
        val query = db.collection("restaurants").orderBy("name", Query.Direction.ASCENDING)
        //pass query results to de RecyclerAdapter for the display in recyclerView
        val options = FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(query, Restaurant::class.java).build()
        adapter = RestaurantAdapter(options)
        restaurantsRecyclerView.adapter = adapter

        addFab.setOnClickListener{
            //navigate to main activity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)

        }




    }

    //tell adapter to start watching data for changes
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }
    override fun onStop() {
        super.onStop()
        if(adapter != null) {
            adapter!!.stopListening()
        }

    }

    //create inner classes needed to bind the data to the recyclerview
    private inner class RestaurantViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {}

    //Adaptor class
    private inner class RestaurantAdapter internal constructor(options: FirestoreRecyclerOptions<Restaurant>) :
        FirestoreRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
            //inflate the item_restaurant.xml layout tamplate to populate the recyclerview
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
            return RestaurantViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: RestaurantViewHolder,
            position: Int,
            model: Restaurant
        ) {
            //populate the restaurant name and rating into matching TextView and ratingbar for each item in the list
            holder.itemView.nameTextView.text = model.name
            holder.itemView.ratingBar.rating = model.rating!!.toFloat() //convert to float to mach the rating type

            //Restaurant selection when ReciclerView item touched
            holder.itemView.setOnClickListener {
                val intent = Intent(applicationContext, RestaurantActivity::class.java)
                intent.putExtra("restaurantId", model.id)
                intent.putExtra("name", model.name)
                startActivity(intent)
            }

        }

    }


}