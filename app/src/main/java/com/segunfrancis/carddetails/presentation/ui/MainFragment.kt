package com.segunfrancis.carddetails.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.segunfrancis.carddetails.databinding.FragmentMainBinding
import com.segunfrancis.carddetails.domain.BinListResponse
import com.segunfrancis.carddetails.presentation.util.AppConstants.DEFAULT_TEXT
import com.segunfrancis.carddetails.presentation.util.Result
import com.segunfrancis.carddetails.presentation.util.enableState
import com.segunfrancis.carddetails.presentation.util.showMessage
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearData()
        binding.checkCardButton.setOnClickListener {
            viewModel.getBinResponse(binding.cardNumberEditText.text.toString())
        }

        viewModel.binResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Error -> {
                    Timber.d(result.error.localizedMessage)
                    binding.root.showMessage(resources.getString(result.formattedErrorMessage))
                    clearData()
                    binding.progressBar.isVisible = false
                }
                is Result.Success -> {
                    populateData(result.data)
                    binding.progressBar.isVisible = false
                }
            }
        }

        viewModel.cardNumber.observe(viewLifecycleOwner) {
            binding.checkCardButton.enableState(it.length > 6)
        }

        binding.cardNumberEditText.addTextChangedListener {
            viewModel.setCardNumber(it.toString())
        }
    }

    private fun populateData(response: BinListResponse?) {
        binding.textBank.text = response?.bank?.name ?: DEFAULT_TEXT
        binding.textType.text = response?.type ?: DEFAULT_TEXT
        binding.textScheme.text = response?.scheme ?: DEFAULT_TEXT
        binding.textPrepaid.text = response?.prepaid?.toString() ?: DEFAULT_TEXT
        binding.textCountry.text = response?.country?.name ?: DEFAULT_TEXT
        binding.textCardNumberLength.text = response?.number?.length?.toString() ?: DEFAULT_TEXT
    }

    private fun clearData() {
        binding.textBank.text = DEFAULT_TEXT
        binding.textType.text = DEFAULT_TEXT
        binding.textScheme.text = DEFAULT_TEXT
        binding.textPrepaid.text = DEFAULT_TEXT
        binding.textCountry.text = DEFAULT_TEXT
        binding.textCardNumberLength.text = DEFAULT_TEXT
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}