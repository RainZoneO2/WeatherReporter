package com.rain.weatherreporter

import androidx.fragment.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.city_dialog.view.*

class CityDialog : DialogFragment() {
    interface CityHandler{
        fun cityCreated(city: String)
    }

    lateinit var cityHandler: CityHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityHandler) {
            cityHandler = context
        } else {
            throw RuntimeException(
                "The activity isn't implementing the ItemHandler interface!"
            )
        }
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
        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCityName.text.isNotEmpty()) {
                handleItemCreate()
                dialog!!.dismiss()
            } else {
                etCityName.error = "This field can not be empty"
            }
        }
    }

    private fun handleItemCreate() {
        cityHandler.cityCreated(etCityName.text.toString())
    }
}
