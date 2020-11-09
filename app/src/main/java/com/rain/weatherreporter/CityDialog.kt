package com.rain.weatherreporter

import androidx.fragment.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ToggleButton
import com.rain.weatherreporter.data.WeatherResult
import kotlinx.android.synthetic.main.city_dialog.view.*

class CityDialog : DialogFragment() {
    interface CityHandler{
        fun cityCreated(item: WeatherResult)
    }

    lateinit var cityHandler: CityHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    lateinit var etCityName: EditText
    lateinit var diaView : View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Add City")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.city_dialog, null
        )

        etCityName = dialogView.etCityName
        diaView = dialogView


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
                handleItemCreate()
                dialog!!.dismiss()
            } else {
                etCityName.error = "This field can not be empty"
            }
        }
    }

    private fun getUnit() : String {
        if (diaView.swMetImper.isEnabled)
            return "metric"
        else
            return "imperial"
        //TODO: THIS DOESNT WORK CORRECTLY
    }

    private fun handleItemCreate() {
        //(context as MainActivity).weatherCreated()
        MainActivity.cityNames.add(etCityName.text.toString())
        //(context as MainActivity).cityName(etCityName.text.toString(), getUnit())

    }
}
