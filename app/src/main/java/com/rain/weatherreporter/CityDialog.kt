package com.rain.weatherreporter

import androidx.fragment.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.city_dialog.view.*

class CityDialog : DialogFragment() {
//    interface ItemHandler{
//        fun itemCreated(item: ShoppingItem)
//        fun itemUpdated(item: ShoppingItem)
//    }
//
//    lateinit var itemHandler: ItemHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

//        if (context is ItemHandler) {
//            itemHandler = context
//        } else {
//            throw RuntimeException(
//                "The activity isn't implementing the ItemHandler interface!"
//            )
//        }
    }

    lateinit var etCityName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Add City")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.city_dialog, null
        )

        etCityName = dialogView.etCityName

        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("Ok") { dialog, which ->
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
        }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        //TODO: ADD ALL FIELDS AS MUST WRITE, CANNOT BE NULL OR ELSE CRASH
        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCityName.text.isNotEmpty()) {
                val arguments = this.arguments
                handleItemCreate()

                dialog!!.dismiss()
            } else {
                etCityName.error = "This field can not be empty"
            }
        }
    }

    private fun handleItemCreate() {

    }
}
