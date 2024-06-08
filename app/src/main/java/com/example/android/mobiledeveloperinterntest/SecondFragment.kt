package com.example.android.mobiledeveloperinterntest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.mobiledeveloperinterntest.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val firstViewModel: FirstViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstViewModel.name.observe(viewLifecycleOwner, Observer { name ->
            binding.nameText.text = name
        })
        firstViewModel.selectedUser.observe(viewLifecycleOwner, Observer { selectedUser ->
            if (selectedUser?.firstName != ""){
                binding.selectedUserText.text = "${selectedUser.firstName} ${selectedUser.lastName}"
            }
            else {
                binding.selectedUserText.text = "No User Selected"
            }
        })
        binding.chooseBtn.setOnClickListener {
            findNavController().navigate(R.id.ThirdFragment)
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}