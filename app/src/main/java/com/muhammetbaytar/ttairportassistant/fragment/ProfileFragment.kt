package com.muhammetbaytar.ttairportassistant.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.muhammetbaytar.ttairportassistant.view.LoginScreen
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.view.widget.CustomLoadDilaog
import kotlinx.android.synthetic.main.activity_new_user.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.pRadioMan
import kotlinx.android.synthetic.main.signout_dialog.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*


class ProfileFragment : Fragment() {

    val dialog = CustomLoadDilaog()

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var dId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

        this.activity?.let { dialog.createLoadDialog(it) }


        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseAdmin(view)
        clickControl(view)

        ///storage/emulated/0/profile_image.jpg

        view.pImageView.setOnClickListener {
            editNewImage()
        }
        return view
    }

    fun loadImageFromStorage(path: String) {
        try {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, "profile_image.jpg")

            var bitmap: Bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            view?.pImageView?.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun firebaseAdmin(view: View) {

        // logout click event
        view.profileBtnLogout.setOnClickListener {
            dialogCreater()

        }
        //-----------------------------------------

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        db.collection("Users").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(context, "Something went wrong !", Toast.LENGTH_LONG).show()
            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {
                        for (document in snapshot.documents) {
                            var userId = document.get("userId") as String
                            if (userId.equals(auth.currentUser?.uid)) {
                                dId = document.id
                                view.profileTxtName.setText(document.get("userName").toString())
                                view.profileTxtBday.setText(document.get("userBday").toString())
                                view.profileTxtTelNo.setText(document.get("userTelNo").toString())
                                view.profileTxtMail.setText(auth.currentUser?.email.toString())

                                if (loadImageFromStorage("storage/emulated/0/profile_image.jpg") != null) {
                                    loadImageFromStorage("storage/emulated/0/profile_image.jpg")
                                }
                                if (document.get("userGender").toString() == "0") {
                                    view.pRadioMan.isChecked = true
                                }
                                break
                            }
                        }
                        dialog.cancelLoadDialog()
                    }
                }
            }
        }
    }

    fun editNewImage() {
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
            pImageView.setImageURI(data?.data)
        }
    }

    fun saveImageToStorage() {
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, "profile_image.jpg")
            try {
                val stream: OutputStream = FileOutputStream(file)

                pImageView.drawable.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()

                Toast.makeText(
                    view?.context,
                    "Registration Successful",
                    Toast.LENGTH_LONG
                ).show()
                println(Uri.parse(file.absolutePath))

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(
                view?.context,
                "Unable to acces the storage",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun imageSaveStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view?.let {
                    ContextCompat.checkSelfPermission(
                        it.context,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                } != PackageManager.PERMISSION_GRANTED

            ) {
                ActivityCompat.requestPermissions(
                    Activity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                )
            } else {
                saveImageToStorage()
            }
        } else {
            saveImageToStorage()
        }
    }

    fun clickControl(view: View) {
        view.pTxtInput2.setStartIconOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val mouth = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view: DatePicker,
                                                     mYear: Int, mMonth: Int, mDay: Int ->
                    profileTxtBday.setText("$mDay/$mMonth/$mYear")
                },
                year,
                mouth,
                day
            )

            dpd.show()
        }

        view.profileBtnUpdate.setOnClickListener {
            val newUserMap = hashMapOf<String, Any>()

            if (pRadioGroup.checkedRadioButtonId == view.pRadioMan.id) {
                newUserMap.put("userGender", 0)

                if (resources.getDrawable(R.drawable.p_man).constantState == pImageView.drawable.constantState ||
                    resources.getDrawable(R.drawable.p_woman).constantState == pImageView.drawable.constantState
                ) {
                    view.pImageView.setImageResource(R.drawable.p_man)
                }
            } else {
                newUserMap.put("userGender", 1)

                if (resources.getDrawable(R.drawable.p_man).constantState == pImageView.drawable.constantState ||
                    resources.getDrawable(R.drawable.p_woman).constantState == pImageView.drawable.constantState
                ) {
                    view.pImageView.setImageResource(R.drawable.p_woman)
                }
            }

            auth.currentUser?.uid?.let { it1 -> newUserMap.put("userId", it1) }
            newUserMap.put("userTelNo", view.profileTxtTelNo.text.toString())
            newUserMap.put("userBday", view.profileTxtBday.text.toString())
            newUserMap.put("userName", view.profileTxtName.text.toString())

            db.collection("Users").document(dId).set(newUserMap, SetOptions.merge())

            imageSaveStorage()
            Toast.makeText(context, "Update Successful", Toast.LENGTH_LONG).show()


        }

    }

    fun dialogCreater() {
        val view = View.inflate(context, R.layout.signout_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.signout_dialog_ok_btn.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginScreen::class.java)
            startActivity(intent)

            var sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences(
                "com.muhammetbaytar.ttairportassistant",
                Context.MODE_PRIVATE
            )

            sharedPreferences.edit().putBoolean("remember", false).apply()


            activity?.finish()
        }

        view.signout_dialog_cancel_btn.setOnClickListener {
            dialog.cancel()
        }

    }
}