package com.bkm.myexamineapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bkm.myexamineapp.ProductsAdapter
import com.bkm.myexamineapp.databinding.FragmentSecondBinding
import com.bkm.myexamineapp.models.GetProductsResponse
import com.bkm.myexamineapp.models.MySharedPreference
import com.bkm.myexamineapp.network.ApiClient
import com.bkm.myexamineapp.network.ApiService
import com.bkm.myexamineapp.repository.MyRepository
import com.bkm.myexamineapp.utils.Status
import com.bkm.myexamineapp.viewModel.MyViewModelFactory
import com.bkm.myexamineapp.viewModel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "SecondFragment"
class SecondFragment : Fragment() {
    private lateinit var apiService: ApiService
    private lateinit var sharedPreferences: SharedPreferences
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var productViewModel: ProductViewModel
    private lateinit var myRepository: MyRepository
    private val binding by lazy { FragmentSecondBinding.inflate(layoutInflater) }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        myRepository = MyRepository(ApiClient.getRetrofitService())
        productViewModel =ViewModelProvider(requireActivity(),MyViewModelFactory(myRepository))[ProductViewModel::class.java]
        productsAdapter = ProductsAdapter()
        sharedPreferences = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val savedToken = sharedPreferences.getString("token", "")
        apiService = ApiClient.getRetrofitService()
        binding.myRv.adapter = productsAdapter
//        binding.tv.text = savedToken.toString()

        productViewModel.getProduct(savedToken.toString())
            .observe(requireActivity()){
                when(it.status){
                    Status.LOADING->{
                        Log.d(TAG, "onCreateView: Loading...")
                        binding.myProgressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR->{
                        Log.d(TAG, "onCreateView: Error ${it.message}")
                        binding.myProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.SUCCESS->{
                        Log.d(TAG, "Success onCreateView: ${it.data}")
                        binding.tv.text = it.data!!.products[1].name

                        productsAdapter.list.addAll(it.data.products)
                        productsAdapter.notifyDataSetChanged()
                        binding.myProgressBar.visibility = View.GONE
                    }
                }
            }


   /*
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    ApiClient.getRetrofitService().getUserProducts(savedToken!!).enqueue(object :Callback<GetProductsResponse>{
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onResponse(
                            call: Call<GetProductsResponse>,
                            response: Response<GetProductsResponse>,
                        ) {
                            if (response.isSuccessful){
                                productsAdapter.list.addAll(response.body()!!.products)
                                productsAdapter.notifyDataSetChanged()
                                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                            Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "onFailure: ${t.message}")
                            Log.d(TAG, "onFailure: ${t.localizedMessage}")
                        }
                    })

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error ${e.message} catchdan", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onCreateView: ${e.message}")
                        Log.d(TAG, "onCreateView: ${e.localizedMessage}")
                        Log.d(TAG, "onCreateView: ${e.cause}")
                    }
                }
            }
       */

        return binding.root
    }

}