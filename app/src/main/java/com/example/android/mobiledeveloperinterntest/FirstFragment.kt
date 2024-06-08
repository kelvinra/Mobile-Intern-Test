package com.example.android.mobiledeveloperinterntest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.mobiledeveloperinterntest.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val firstViewModel: FirstViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        firstViewModel.inputPalindromText.observe(viewLifecycleOwner, Observer { text ->
//            // Update the UI with the new text
//            binding.editTextPalindrome.setText(text)
//        })
        binding.editTextname.addTextChangedListener { text ->
            firstViewModel.setName(text)
        }
        val palindromeText = binding.editTextPalindrome.text
        binding.palindromeBtn.setOnClickListener {
            val isPalindrome = firstViewModel.checkPalindrome(palindromeText)
            setPalindrome(isPalindrome)
        }
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun setPalindrome(bool: Boolean) {
        if (bool){
            binding.palindromeInputLayout.error = null
            binding.palindromeInputLayout.helperText = "Palindrome"
        }
        else {
            binding.palindromeInputLayout.error = "Not a Palindrome"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}