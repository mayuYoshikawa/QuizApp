package com.example.quizapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.SpreadSheets
import com.example.quizapp.SpreadSheetsData
import com.example.quizapp.databinding.FragmentShowAllQuizBinding
import com.example.quizapp.base_url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ShowAllQuizFragment : Fragment() {

    private var _binding : FragmentShowAllQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowAllQuizBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()

        binding.showAllCreateQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_showAllQuizFragment_to_createQuizFragment)
        }
    }
    private fun getData(){
        // Retrofit のインスタンスを作成
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SpreadSheets = retrofit.create(SpreadSheets::class.java)
        val call: Call<SpreadSheetsData> = service.getQuiz()

        // Callクラスのenqueueメソッドを使って非同期でのリクエスト
        call.enqueue(object : Callback<SpreadSheetsData> {

            override fun onFailure(call: Call<SpreadSheetsData>, t: Throwable) {
                Log.e("error onFailure", t.message.toString())
            }

            override fun onResponse(
                call: Call<SpreadSheetsData>,
                response: Response<SpreadSheetsData>
            ) {
                if (response.isSuccessful) {
                    val quizList: SpreadSheetsData = response.body()!!

                    display(quizList)

                    Log.i("Response", "$quizList")
                } else {
                    Log.e("error onResponse", "${response.code()}")
                }
            }
        })
    }

    // quizを全て表示させる
    private fun display(showAllQuizList: SpreadSheetsData) {
        val allQuiz = mutableListOf<String>()
        for (i in showAllQuizList.values.indices){
            if(i > 0){
                allQuiz += listOf(showAllQuizList.values[i][0])
            }
        }
        binding.showAllQuizList.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            allQuiz
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}