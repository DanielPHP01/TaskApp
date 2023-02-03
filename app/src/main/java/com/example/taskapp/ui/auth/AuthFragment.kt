package com.example.taskapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class AuthFragment : Fragment() {
    lateinit var binding: FragmentAuthBinding
    var auth = FirebaseAuth.getInstance()
    var corectCoade: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        initViews()
        initListeners()
        return binding.root

    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{
            if (binding.etPhone.text!!.isNotEmpty()) {
                sendPhone()
                Toast.makeText(requireContext(), "отправка...", Toast.LENGTH_SHORT).show()
            } else {
                binding.etPhone.error = "Введите номер телефона"
            }
        }
        binding.btnConfirn.setOnClickListener{
            sendCode()
        }
    }

    private fun sendCode() {
        val credential =
            PhoneAuthProvider.getCredential(
                corectCoade.toString(), binding.etPhoneFrt.toString()
            )
        signInWithPhoneAuthCredential(credential)
    }

    private fun initViews() {

    }

    private fun sendPhone() {
        auth.setLanguageCode("ru")
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.etPhone.text.toString())       // Phone number to verify binding.etPhone.text.stribg
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                     Toast.makeText(requireContext(), p0.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(requireContext(), p0.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    corectCoade = p0
                    binding.etPhone.isVisible = false
                    binding.btnSave.isVisible = false

                    binding.etPhoneLayoutFrt.isVisible = true
                    binding.btnConfirn.isVisible = true
                }

            })     // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                   findNavController().navigate(R.id.navigation_home)

                    val user = task.result?.user
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }



}