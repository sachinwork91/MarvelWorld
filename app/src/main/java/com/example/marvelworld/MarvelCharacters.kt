package com.example.marvelworld

data class MyResponse(val data: Data)
data class Data(val results: List<Results>)
data class Results(val id: String, val name: String, val thumbnail: Thumbnail, val comics: Comics)
data class Thumbnail(val path: String, val extension: String)
data class Comics(val items: List<Items>)
data class Items(val resourceURI: String, val name: String)
