package com.example.savelifeapp.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.savelifeapp.databinding.FragmentRequestBinding
import com.example.savelifeapp.ui.request.viewpager.SectionPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestFragment : Fragment() {
    private var _binding: FragmentRequestBinding? = null
     private lateinit var viewpagerAdapter: SectionPagerAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(RequestViewModel::class.java)

        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpagerAdapter = SectionPagerAdapter(this)
        with(binding) {
            viepagerReceived.adapter = viewpagerAdapter
            TabLayoutMediator(tabLayout2, viepagerReceived) { tab, position ->
                when (position) {
                    0 -> tab.text = "Received Request"
                    1 -> tab.text = "My Request"
                }
            }.attach()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}