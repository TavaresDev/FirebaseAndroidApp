package com.example.myappfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance().collection("comments")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        //populate Heading textview w/ restaurant name
        restaurantNameTextView.text = intent.getStringExtra("name")?.toString()


        saveCommentButton.setOnClickListener{
            //create new coment and save to firebase
        }
    }
}