package com.example.quizapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val base_url = "https://sheets.googleapis.com/v4/spreadsheets/"
const val spread_name = "Quiz"
const val spread_id = "1ZETSrcwwOCdP_wP_s9xd9NtRNKh2M4DgsIUenJFTDAM"
const val api_key = "AIzaSyAmRdJDJCqMKnV8F82XGcziNFCVYcsGtco"

interface SpreadSheets {
    @GET("{spread_id}/values/{spread_name}")

    fun getQuiz(
        @Path("spread_id") spreadId: String = spread_id,
        @Path("spread_name") spreadName: String = spread_name,
        @Query("key") query: String = api_key
    ): Call<SpreadSheetsData>
}