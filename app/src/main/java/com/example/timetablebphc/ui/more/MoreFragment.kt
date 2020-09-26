package com.example.timetablebphc.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timetablebphc.GoogleSignInActivity
import com.example.timetablebphc.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : Fragment() {

    private lateinit var moreViewModel : MoreViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: remove redundancy of signout code, gso created twice
    }

    private fun signOut() {
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.g_api_key))
            .requestEmail()
            .build()

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
        // Firebase sign out
        auth.signOut()
        googleSignInClient.signOut()
        // Google sign out
        val intent = Intent(context, GoogleSignInActivity::class.java)
        intent.putExtra("key", "Kotlin")
        startActivity(intent)

    }



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        moreViewModel =
                ViewModelProviders.of(this).get(MoreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_more, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sing_out_button.setOnClickListener { signOut() }
    }
}