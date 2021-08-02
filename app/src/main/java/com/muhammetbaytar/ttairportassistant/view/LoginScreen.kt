package com.muhammetbaytar.ttairportassistant.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login_screen.*

class LoginScreen : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        supportActionBar?.hide()

        startAnimate()
        clickControl()

        auth= FirebaseAuth.getInstance()

        remember()
        initSensor()
    }
    fun remember(){
        sharedPreferences=this.getSharedPreferences("com.muhammetbaytar.ttairportassistant",
                Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("remember",false)){
            if(auth.currentUser!=null){
                val intent=Intent(applicationContext, HomeScreen::class.java)
                startActivity(intent)
                finish()
            }
        }
    }



    fun click_login(v: View){
        if(txt_Email.text.toString().isEmpty()){
            textInputLayout2.helperText="Required Field*"

        }else if(txt_Pass.text?.isEmpty() == true){
            textInputLayout.helperText="Required Field*"
        }

        else{

        auth.signInWithEmailAndPassword(txt_Email.text.toString(),txt_Pass.text.toString())
            .addOnCompleteListener { task->
                if (task.isSuccessful){

                    // beni hatırla seçeneği için veri kaydeder
                    if(check_remember.isChecked){
                        sharedPreferences.edit().putBoolean("remember",true).apply()
                    }

                    val intent=Intent(applicationContext, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception->
                    
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
    }
    }



    fun startAnimate(){
        iw_icon.animate().apply {
            duration=1000

            rotationYBy(-360f)
        }
        txt_icon.animate().apply {
            duration=1000

            rotationXBy(-360f)
        }
    }

    fun clickControl(){
        txt_newUser.setOnClickListener(){
            val intent=Intent(this, NewUser::class.java)
            startActivity(intent)
        }

    }
}