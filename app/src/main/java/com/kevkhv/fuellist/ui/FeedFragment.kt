package com.kevkhv.fuellist.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.kevkhv.fuellist.R
import com.kevkhv.fuellist.adapter.LutAdapter
import com.kevkhv.fuellist.adapter.OnInteractionListener
import com.kevkhv.fuellist.databinding.DialogStartBinding
import com.kevkhv.fuellist.databinding.FragmentFeedBinding
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.viewModel.ViewModel


class FeedFragment : Fragment() {
    val viewModel: ViewModel by viewModels(ownerProducer = ::requireParentFragment)
    lateinit var binding: FragmentFeedBinding
    private val adapter = LutAdapter(object : OnInteractionListener {

        override fun onEdit(lut: Lut) {
            viewModel.edit(lut)
//            findNavController().navigate(
//                Bundle().apply { idArg = lut.id })
        }

        override fun onRemoveById(lut: Lut) {
            viewModel.removeByID(lut.id)
        }

    })

//
//    val lut1 = Lut(0,"Май",20,0,100)
//    val lut2 = Lut(0,"Апрель",10,100,200)
//    val lut3 = Lut(0,"Июнь",25,200,500)
//    val lut4 = Lut(0,"Июль",22,500,600)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater)
//        viewModel.save(lut1)
//        viewModel.save(lut2)
//        viewModel.save(lut3)
//        viewModel.save(lut4)
        with(binding) {
            listView.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner) { luts ->
                if (luts.isEmpty()) {
                    showStartDialog()
                } else
                    adapter.submitList(luts)
            }
        }
        return binding.root


    }

    fun showStartDialog() {
        val builder = AlertDialog.Builder(activity)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_start, null)
        val monthView = dialogLayout.findViewById<TextInputLayout>(R.id.selectMonth)
        val items = resources.getStringArray(R.array.month_items)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (monthView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        val startingMileageView = dialogLayout.findViewById<EditText>(R.id.start)
        val litresView = dialogLayout.findViewById<EditText>(R.id.litres)

        with(builder) {
            setPositiveButton("Ок") { dialog, which ->
                //TODO
            }
            setView(dialogLayout)
            show()
        }
    }
}