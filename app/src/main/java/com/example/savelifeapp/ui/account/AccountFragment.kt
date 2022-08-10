package com.example.savelifeapp.ui.account

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.savelifeapp.R
import com.example.savelifeapp.databinding.FragmentAccountBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.login.LoginActivity
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.hide
import com.example.savelifeapp.utils.isValidEmail
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val userAppColection = Firebase.firestore.collection("UserApp")

    companion object {
        const val REQUEST_CAMERA = 100
    }

    private lateinit var imageUri: Uri

    //    this is fo firebase
    private lateinit var auth: FirebaseAuth
//    private val db = Firebase.firestore

    val viewmodel: AccountViewModel by viewModels()
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
            ViewModelProvider(this).get(com.example.savelifeapp.ui.account.AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //        inisialisasi firebase
//        getNameWithAuth()
        return root
    }

//    private fun intentCamera() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
//            activity?.packageManager?.let {
//                intent.resolveActivity(it).also {
//                    startActivityForResult(intent,
//                        com.example.savelifeapp.ui.account.AccountFragment.Companion.REQUEST_CAMERA
//                    )
//                }
//            }
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == com.example.savelifeapp.ui.account.AccountFragment.Companion.REQUEST_CAMERA && resultCode == RESULT_OK) {
//            val imgBitmap = data?.extras?.get("data") as Bitmap
//            uploadImage(imgBitmap)
//        }
//    }

//    private fun uploadImage(imgBitmap: Bitmap) {
//        val baos = ByteArrayOutputStream()
//        val ref = FirebaseStorage.getInstance().reference.child("img/${auth.currentUser?.uid}")
//        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val image = baos.toByteArray()
//        ref.putBytes(image)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    ref.downloadUrl.addOnCompleteListener {
//                        it.result?.let {
//                            imageUri = it
//                            binding.imgProfile.setImageBitmap(imgBitmap)
//                        }
//                    }
//                }
//            }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        viewmodel.getProfile()
        viewmodel.profile.observe(viewLifecycleOwner) {
            it.forEach {
                binding.textView16.text = it.name.toString()
                binding.textView17.text = it.golDarah.toString()
                Log.e("mami", it.toString())
            }
        }

//        tes disini
//        val user = auth.currentUser


        observer()
        binding.logout.setOnClickListener {
//            auth.signOut()
            viewmodel.logout()

//            val intent = Intent(requireActivity(), LoginActivity::class.java)
//            startActivity(intent)
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
                    val poto = document.getString("image")
                    Picasso.get().load(poto).into(binding.imgProfile)
                    binding.name.text = nama
                    Log.d("tes", "$nama")
                } else {
                    Log.d(TAG, "The document doesnt exits")
                }
            } else {
                task.exception?.message?.let {
                    Log.d(TAG, it)
                }
            }
        }
    }

    fun observer(){
        viewmodel.logout.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    toast(it.data)
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}