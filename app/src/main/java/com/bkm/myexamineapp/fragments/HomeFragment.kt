package com.bkm.myexamineapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bkm.myexamineapp.R
import com.bkm.myexamineapp.databinding.FragmentHomeBinding
import com.bkm.myexamineapp.models.MySharedPreference
import com.bkm.myexamineapp.models.User
import com.bkm.myexamineapp.network.ApiClient
import com.bkm.myexamineapp.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var apiService: ApiService
    private lateinit var sharedPreferences: SharedPreferences
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        sharedPreferences = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        apiService = ApiClient.getRetrofitService()

        postData()

//addCatchToken()
        return binding.root
    }

    private fun postData(){
        binding.btnNext.setOnClickListener {
            val user = User(
                binding.edtUsername.text.toString().trim(),
                binding.edtPassword.text.toString().trim()
            )

            // Show the progress bar while loading
            binding.myProgressBar.visibility = View.VISIBLE

/*
            GlobalScope.launch(Dispatchers.IO) {
                ApiClient.getRetrofitService(requireContext()).postUser(user).enqueue(object :Callback<MyToken>{
                    override fun onResponse(call: Call<MyToken>, response: Response<MyToken>) {
                        if (response.isSuccessful){
                            Toast.makeText(requireContext(), "Succesful", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.secondFragment)
                        }
                    }

                    override fun onFailure(call: Call<MyToken>, t: Throwable) {
                        binding.myProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${t.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
*/


            if (binding.edtUsername.text.isNotEmpty() && binding.edtPassword.text.isNotEmpty()){
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val response = apiService.getToken(user).execute()

                        if (response.isSuccessful) {
                            val token =response.body()?.access
                            sharedPreferences.edit().putString("token", token).apply()
                            withContext(Dispatchers.Main) {

                                Log.d(TAG, "postData: ${response.body().toString()}")
//                                findNavController().popBackStack()
                                findNavController().navigate(R.id.secondFragment)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Unsuccessful ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Error ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "postData: ${e.message}")
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            binding.myProgressBar.visibility = View.GONE
                        }
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Ma'lumotlarni to'ldiring", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

//    fun addCatchToken(){
//        MySharedPreference.init(requireContext())
//        MySharedPreference.token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjkxOTM1NDYxLCJpYXQiOjE2OTE1MDM0NjEsImp0aSI6Ijk0ODlmYzJmNWJiYzRmM2Y5OWE2YjFkZDMwMjk2OGQ1IiwidXNlcl9pZCI6MTU4fQ.0t354rN_eDNtUqF9RduwM73sl3OF5LE-Rm5P2tlq1AQ"
//    }


}