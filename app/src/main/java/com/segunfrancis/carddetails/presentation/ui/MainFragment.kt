package com.segunfrancis.carddetails.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.segunfrancis.carddetails.databinding.FragmentMainBinding
import com.segunfrancis.carddetails.presentation.util.Result
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkCardButton.setOnClickListener {
            if (!binding.cardNumberEditText.text.isNullOrEmpty()) {
                viewModel.getBinResponse(binding.cardNumberEditText.text.toString())
            }
        }

        viewModel.binResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Timber.d("Loading")
                }
                is Result.Error -> {
                    Timber.d(result.error.localizedMessage)
                }
                is Result.Success -> {
                    Timber.d(result.data.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}