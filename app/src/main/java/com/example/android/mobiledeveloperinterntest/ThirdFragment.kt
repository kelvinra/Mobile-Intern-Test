package com.example.android.mobiledeveloperinterntest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mobiledeveloperinterntest.adapter.UserAdapter
import com.example.android.mobiledeveloperinterntest.databinding.FragmentThirdBinding
import com.example.android.mobiledeveloperinterntest.models.User
import com.example.android.mobiledeveloperinterntest.models.UserData

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val firstViewModel: FirstViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private var page: Int = 1
    private var limit: Int = 6



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        recyclerView = binding.userView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter(arrayListOf(), object : UserAdapter.OnAdapterListener{
            override fun onClick(user: UserData) {
                firstViewModel.selectUser(user)
                findNavController().navigate(R.id.SecondFragment)
            }
        })
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstViewModel.listUser.observe(viewLifecycleOwner, Observer { users ->
            userAdapter.setData(users)
        })
        recyclerView.adapter = userAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItems = layoutManager.itemCount
                val visibleItems = layoutManager.childCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItems >= totalItems / 2){
                    if (page < 3){
                        page++
                    }
                    firstViewModel.loadUsers(page)
                }
            }
        }
        )
        firstViewModel.loadUsers(page)

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            firstViewModel.refreshUsers()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}