package com.example.weatherappcompose.model

import com.google.gson.annotations.SerializedName


data class ParentCity (

  @SerializedName("Key"           ) var Key           : String? = null,
  @SerializedName("LocalizedName" ) var LocalizedName : String? = null,
  @SerializedName("EnglishName"   ) var EnglishName   : String? = null

)