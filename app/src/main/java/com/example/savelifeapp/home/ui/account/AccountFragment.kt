package com.example.savelifeapp.home.ui.account

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class AccountFragment : Fragment() {

    //    this is fo firebase
    private lateinit var auth: FirebaseAuth
//    private val db = Firebase.firestore


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

        binding.logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

//        get data with id auth
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("UserApp")
        val idAuth = auth.currentUser?.uid.toString()
        userRef.document(idAuth).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val email = document.getString("email")
                    val nama = document.getString("nama")
                    binding.name.text = nama
                    Log.d("tes","$nama")
                } else {
                    Log.d(TAG, "The document doesnt exits")
                }
            } else {
                task.exception?.message?.let {
                    Log.d(TAG, it)
                }
            }
        }


//        getNameWithAuth()
        return root
    }


    private fun getNameWithAuth() {
        if (auth.currentUser != null) {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("UserApp")
            val user = auth.currentUser!!.uid.toString()
            userRef.document(user).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val name = document.getString("nama")
                    binding.name.text = name.toString()
                }
            }


//           val ref=  db.collection("AppUser")


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}