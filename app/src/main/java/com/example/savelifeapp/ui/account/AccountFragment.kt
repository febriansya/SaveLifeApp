package com.example.savelifeapp.ui.account

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.savelifeapp.data.model.Profile
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.databinding.FragmentAccountBinding
import com.example.savelifeapp.ui.login.LoginActivity
import com.example.savelifeapp.ui.login.LoginViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class AccountFragment : Fragment() {
    lateinit var filePath: Uri
    private var objUser: UsersApp? = null

    companion object {
        const val REQUEST_CAMERA = 100
    }

    private val PICK_IMAGE_REQUEST = 100
    private lateinit var user: UsersApp
    private lateinit var imageUri: Uri
    private lateinit var auth: FirebaseAuth
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var storageRef: FirebaseStorage? = null
    val viewmodel: AccountViewModel by viewModels()
    val loginView: LoginViewModel by viewModels()

    lateinit var objProfile: Profile
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance()
        observer()
        binding.logout.setOnClickListener {
            viewmodel.logout()
        }

        val currentUser = auth.currentUser?.uid.toString()
        val userAppColection = Firebase.firestore.collection("UserApp").document(currentUser)
        userAppColection.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.name.text = task.result.getString("nama")
                binding.textView16.text = task.result.getString("golDarah")
                Picasso.get().load(task.result.getString("image")).into(binding.imgProfile)
            }
        }

        binding.apply {
            edSetings.setOnClickListener {
                val intent = Intent(requireContext(), UpdateAccountActivity::class.java)
                startActivity(intent)
            }

            tvChangedpw.setOnClickListener {
                val intent = Intent(requireContext(),ChangePwdActivity::class.java)
                startActivity(intent)
            }

            imgProfile.setOnClickListener {
                OpenGallery()
            }
        }

        binding.btnRiawayat.setOnClickListener {
            val intent = Intent(requireActivity(), HistoryDonorActivity::class.java)
            startActivity(intent)
        }

        val userCurrent = auth.currentUser?.uid.toString()


//        hitung berapa kali donor berdasarkan history donor
        mFirebaseDatabaseInstance!!.collection("HistoryDonor").document(userCurrent)
            .collection("Riwayat")
//            .whereEqualTo("idPendonor", userCurrent)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    var count = 0
                    for (document in task.result) {
                        count++
                    }
                    binding.textView17.setText(count.toString())
                } else {
                    Log.d("errror", "Error getting documents: ", task.exception)
                }
            })
    }

    private fun observer() {
        viewmodel.logout.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    toast(it.data)
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                }
            }
        }
    }

    fun OpenGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = storageRef?.reference?.child("img")?.child("${UUID.randomUUID()}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val image = baos.toByteArray()
        ref?.putBytes(image)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result.let {
                            filePath = it
                           binding.imgProfile.setImageBitmap(imgBitmap)
//
//                            binding.imageChange.setText(filePath.toString())
                        }
//                        ganti image disini boss
                        mFirebaseDatabaseInstance!!.collection("UserApp").document(auth.currentUser!!.uid)
                            .update("image",filePath)
                    }
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)
                uploadImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }







}