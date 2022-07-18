package com.example.savelifeapp.home.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.FragmentAccountBinding
import com.example.savelifeapp.databinding.FragmentHomeBinding
import com.example.savelifeapp.home.ui.home.HomeViewModel
import com.example.savelifeapp.home.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountFragment : Fragment() {

    //    this is fo firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //        inisialisasi firebase
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()

        binding.logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireActivity(),LoginActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}