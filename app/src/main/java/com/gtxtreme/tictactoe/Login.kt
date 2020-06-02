package com.gtxtreme.tictactoe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val RC_SIGN_IN = 223
    private var user:FirebaseUser?=null
    private var database=FirebaseDatabase.getInstance()
    private var myRef = database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        user = FirebaseAuth.getInstance().currentUser
//        if (user!=null){
//            //TODO Send user details to the MainActivity
//            startActivity(Intent(this,MainActivity::class.java))
//            finish()
//        }
//        else {
            setContentView(R.layout.activity_login)
            signInBtn.setOnClickListener {
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo)
                        .build(),
                    RC_SIGN_IN
                )
            }

            txtSkip.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
//        }
    }

    override fun onStart() {
        super.onStart()
        user = FirebaseAuth.getInstance().currentUser
        if (user!=null){
            //TODO Send user details to the MainActivity
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            } else {
                Log.d("TicTacToe","Sign In Failed")
            }
        }
    }


}
