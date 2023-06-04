package com.example.engcontext.fragments

import android.app.Activity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.engcontext.R
import com.example.engcontext.databinding.SigninFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {

    private var _binding: SigninFragmentBinding? = null
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SigninFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.signUpTextView.setOnClickListener { onRegisterClick() }
        binding.googleSignIn.setOnClickListener { onGoogleSignInClick() }
        binding.signInButton.setOnClickListener { onSignInClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onRegisterClick() {
        findNavController().navigate(R.id.action_SignInFragment_to_RegisterFragment)
    }

    private fun onGoogleSignInClick() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun onSignInClick() {
        val email = binding.emailTextInput.text.toString()
        val password = binding.passwordTextInput.text.toString()
        firebaseAuth.signInWithEmailAndPassword(
            email,
            password,
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_SignInFragment_to_SearchBarFragment)
            } else {
                showSnackBar("Ooops... ${it.exception?.message}")
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                findNavController().navigate(R.id.action_SignInFragment_to_SearchBarFragment)
            }
        } else {
            showSnackBar(task.exception.toString())
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.coordinatorLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}