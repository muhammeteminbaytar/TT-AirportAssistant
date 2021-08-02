package com.muhammetbaytar.ttairportassistant.view.widget

import android.app.Activity
import android.app.Dialog
import com.muhammetbaytar.ttairportassistant.R

class CustomLoadDilaog() {
        var dialog: Dialog? =null

        fun createLoadDialog(activity: Activity){
            dialog= Dialog(activity)
            dialog?.setContentView(R.layout.load_dialog)
            dialog?.setCancelable(false)
            dialog?.show()
        }
        fun cancelLoadDialog(){
            dialog?.cancel()
        }

}