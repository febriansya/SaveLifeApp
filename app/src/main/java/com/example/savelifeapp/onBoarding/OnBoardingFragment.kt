package com.example.savelifeapp.onBoarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = OnBoardingFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)
        val onBoardingTitles = requireContext().resources.getStringArray(R.array.onboarding_titles)
        val onBoardingText = requireContext().resources.getStringArray(R.array.onboarding_texxt)
        val imageOnBoarding = requireContext().resources.obtainTypedArray(R.array.imageOnBoarding)
        with(binding) {
            tvTitle.text = onBoardingTitles[position]
            tvText.text = onBoardingText[position]
            val image = imageOnBoarding.getResourceId(position, -1)
            imageView.setImageResource(image)
            Log.e("image", "error")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
