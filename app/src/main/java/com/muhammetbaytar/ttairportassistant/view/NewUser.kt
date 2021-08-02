package com.muhammetbaytar.ttairportassistant.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Instrumentation
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login_screen.iw_icon
import kotlinx.android.synthetic.main.activity_login_screen.txt_icon
import kotlinx.android.synthetic.main.activity_new_user.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

@Suppress("DEPRECATION")
class NewUser : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        supportActionBar?.hide()

        startAnimate()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        clickControl()
        initSensor()
        checkControl()
    }

    fun clickControl() {
        newTextInputLayout3.setStartIconOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val mouth = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view: DatePicker,
                                                                                  mYear: Int, mMonth: Int, mDay: Int ->
                txt_newBday.setText("" + mDay + "/" + mMonth + "/" + mYear)
            }, year, mouth, day)

            dpd.show()
        }
    }

    fun checkControl() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (resources.getDrawable(R.drawable.p_man).constantState == pNewImageView.drawable.constantState ||
                resources.getDrawable(R.drawable.p_woman).constantState == pNewImageView.drawable.constantState
            )
                if (radioGroup.checkedRadioButtonId == pNewRadioMan.id) {
                    pNewImageView.setImageResource(R.drawable.p_man)
                } else {
                    pNewImageView.setImageResource(R.drawable.p_woman)
                }
        }
    }

    fun btn_createAcc(v: View) {

        if (txt_newName.text?.isEmpty() == true) {
            newTextInputLayout1.helperText = "Required Field*"
        } else if (txt_newTelNo.text?.isEmpty() == true) {
            newTextInputLayout2.helperText = "Required Field*"
        } else if (txt_newBday.text?.isEmpty() == true) {
            newTextInputLayout3.helperText = "Required Field*"
        } else if (txt_newEmail.text?.isEmpty() == true) {
            newTextInputLayout5.helperText = "Required Field*"
        } else if (txt_newPass.text?.isEmpty() == true) {
            newTextInputLayout4.helperText = "Required Field*"
        } else {

            auth.createUserWithEmailAndPassword(
                txt_newEmail.text.toString(),
                txt_newPass.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fireStoreSaver()
                        imageSaveStorage()
                    }
                }.addOnFailureListener { exception ->
                    if (exception != null) {
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun fireStoreSaver() {

        val userMap = hashMapOf<String, Any>()

        if (radioGroup.checkedRadioButtonId == pNewRadioMan.id) {
            userMap.put("userGender", 0)
        } else {
            userMap.put("userGender", 1)
        }
        auth.currentUser?.let { userMap.put("userId", it.uid) }
        userMap.put("userTelNo", txt_newTelNo.text.toString())
        userMap.put("userBday", txt_newBday.text.toString())
        userMap.put("userName", txt_newName.text.toString())


        db.collection("Users").add(userMap).addOnCompleteListener { task ->
        }.addOnFailureListener { exception ->
            Toast.makeText(
                applicationContext,
                exception.localizedMessage.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun imageSaveStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED

            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                )
            } else {
                saveImageToStorage()
            }
        } else {
            saveImageToStorage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageToStorage()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }


     fun saveImageToStorage() {
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, "profile_image.jpg")
            try {
                val stream: OutputStream = FileOutputStream(file)

                pNewImageView.drawable.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()

                Toast.makeText(
                    applicationContext,
                    "Registration Successful",
                    Toast.LENGTH_LONG
                ).show()
                println(Uri.parse(file.absolutePath))
                finish()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Unable to acces the storage", Toast.LENGTH_SHORT).show()
        }
    }

    fun startAnimate() {
        iw_icon.animate().apply {
            duration = 1000

            rotationYBy(-360f)
        }
        txt_icon.animate().apply {
            duration = 1000

            rotationXBy(-360f)
        }
    }

    fun editNewImage(view: View) {
        ImagePicker.with(this)
            .cropSquare() //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            pNewImageView.setImageURI(data?.data)
        }
    }

}