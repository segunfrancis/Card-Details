package com.segunfrancis.carddetails.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.segunfrancis.carddetails.databinding.FragmentMainBinding
import com.segunfrancis.carddetails.domain.BinListResponse
import com.segunfrancis.carddetails.presentation.util.Result
import com.segunfrancis.carddetails.presentation.util.enableState
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

        binding.checkCardButton.setOnClickListener {
            viewModel.getBinResponse(binding.cardNumberEditText.text.toString())
        }

        viewModel.binResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Snackbar.make(requireView(), "Loading", Snackbar.LENGTH_LONG).show()
                }
                is Result.Error -> {
                    Timber.d(result.error.localizedMessage)
                    Snackbar.make(
                        requireView(),
                        result.formattedErrorMessage,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is Result.Success -> {
                    populateData(result.data)
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
        binding.textBank.text = response?.bank?.name ?: ""
        binding.textType.text = response?.type ?: ""
        binding.textScheme.text = response?.scheme ?: ""
        binding.textPrepaid.text = response?.prepaid?.toString() ?: ""
        binding.textCountry.text = response?.country?.name ?: ""
        binding.textCardNumberLength.text = response?.number?.length?.toString() ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}