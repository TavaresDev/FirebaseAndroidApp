package com.example.myappfirebase

class Restaurant {

    //class proprietes
    var id : String? = null
    var name: String? = null
    var rating: Int? = null

    //empty contructor
    constructor(){}


    constructor(id: String, name: String, rating: Int) {
        this.id = id
        this.name = name
        this.rating = rating
    }

}

