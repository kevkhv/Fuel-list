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
import com.kevkhv.fuellist.adapter.LitersAdapter
import com.kevkhv.fuellist.adapter.LitersViewHolder
import com.kevkhv.fuellist.adapter.LutAdapter
import com.kevkhv.fuellist.adapter.OnInteractionListener
import com.kevkhv.fuellist.databinding.FragmentFeedBinding
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.viewModel.ViewModel


class FeedFragment : Fragment() {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var recyclerView: RecyclerView
    val viewModel: ViewModel by viewModels(ownerProducer = ::requireParentFragment)
    lateinit var binding: FragmentFeedBinding


    private val adapter = LutAdapter(object : OnInteractionListener<Lut> {

        override fun onEdit(lut: Lut) {
            viewModel.edit(lut)
//            findNavController().navigate(
//                Bundle().apply { idArg = lut.id })
        }

        override fun onRemoveById(lut: Lut) {
            viewModel.removeByID(lut.id)
        }

        override fun showAddFuelDialog(lutId: Int) {
            val builder = AlertDialog.Builder(activity)
            val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_fuel, null)
            val litresView = dialogLayout.findViewById<EditText>(R.id.litres)

            with(builder) {
                setPositiveButton("Ок") { dialog, which ->
                    viewModel.addLiters(
                        Liters(
                            0,
                            lutId,
                            Integer.parseInt(litresView.text.toString()),
                            "date"
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

        override fun showBottomSheet(lutId: Int) {
            Log.d("tesstt",lutId.toString())
            showBottomSheet()
        }

    }


    )

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
        //TODO HERE
//        val recyclerViewLiters = view?.findViewById<RecyclerView>(R.id.recyclerListLiters)
//
//        val testLit  = arrayListOf<Liters>(Liters(1,1,11,"11111"))
//
//        recyclerViewLiters?.adapter = litersAdapter
//
//        litersAdapter.submitList(testLit)

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
    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.liters_list_fragment, null)
        val removeButton = dialogView.findViewById<View>(R.id.removeLine)
        val litersAdapter = LitersAdapter(object : OnInteractionListener<Liters> {
            override fun onRemoveById(lut: Liters) {
                TODO("Not yet implemented")
            }

            override fun showAddFuelDialog(lutId: Int) {
                TODO("Not yet implemented")
            }

            override fun showBottomSheet(lutId: Int) {
                TODO("Not yet implemented")
            }


        })

        dialog = BottomSheetDialog(requireContext(), R.style.MyBottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        recyclerView = dialogView.findViewById(R.id.recyclerChoice)

        recyclerView.adapter = litersAdapter

        val testLiters = arrayListOf(Liters(1,2,44,"22"))
        litersAdapter.submitList(testLiters)


//        viewModel.data.value.let { litersAdapter.submitList(it) }

        dialog.show()

        removeButton.setOnClickListener {
            dialog.hide()
        }
    }

}