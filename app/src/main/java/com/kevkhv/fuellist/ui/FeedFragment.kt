package com.kevkhv.fuellist.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.kevkhv.fuellist.R
import com.kevkhv.fuellist.adapter.*
import com.kevkhv.fuellist.databinding.FragmentFeedBinding
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.viewModel.ViewModel
import java.text.SimpleDateFormat
import java.util.*


class FeedFragment : Fragment() {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentFeedBinding
    val viewModel: ViewModel by viewModels(ownerProducer = ::requireParentFragment)


    private val adapter = LutAdapter(object : LutInteractionListener {
        override fun onLutEditClicked(lut: Lut) {
            viewModel.edit(lut)
//            findNavController().navigate(
//                Bundle().apply { idArg = lut.id })
        }

        override fun onLutRemoveClicked(lut: Lut) {
            viewModel.removeByID(lut.id)
        }

        override fun showAddFuelDialog(lutId: Int) {
            val builder = AlertDialog.Builder(activity)
            val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_fuel, null)
            val litresView = dialogLayout.findViewById<EditText>(R.id.litres)
            val date = SimpleDateFormat("dd/M/yyyy", Locale.US).format(Date())

            with(builder) {
                setPositiveButton("Ок") { dialog, which ->
                    viewModel.addLiters(
                        Liters(
                            0,
                            lutId,
                            Integer.parseInt(litresView.text.toString()),
                            date
                        )
                    )
                }
                setNegativeButton("Отмена") { dialog, which ->
                    Toast.makeText(requireContext(), "CLOSE", 12).show()
                }
                setView(dialogLayout)
                show()
            }
        }
        override fun showBottomSheetWithLiters(lutId: Int) {
            showBottomSheet(lutId)
        }

    }

    )


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

    private fun showStartDialog() {
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
                viewModel.save(
                    Lut(
                        0,
                        month = monthView.editText?.text.toString(),
                        litresTotal = 0,
                        residueLitres = Integer.parseInt(litresView.text.toString()),
                        startingMileage = Integer.parseInt(startingMileageView.text.toString()),
                        endMileage = 0
                    )
                )
            }
            setView(dialogLayout)
            show()
        }
    }

    private fun showBottomSheet(lutId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.liters_list_fragment, null)
        val removeButton = dialogView.findViewById<View>(R.id.removeLine)
        dialog = BottomSheetDialog(requireContext(), R.style.MyBottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        recyclerView = dialogView.findViewById(R.id.recyclerChoice)

        val litersAdapter = LitersAdapter(object : LitersInteractionListener {
            override fun onLitersEditClicked(liters: Liters) {
                TODO("Not yet implemented")
            }

            override fun onLitersRemoveClicked(liters: Liters) {
                TODO("Not yet implemented")
            }
        })

        recyclerView.adapter = litersAdapter
        viewModel.getLitersListLiveData(lutId).observe(viewLifecycleOwner) {
            litersAdapter.submitList(it)
        }

        dialog.show()
        removeButton.setOnClickListener {
            dialog.hide()
        }
    }

}