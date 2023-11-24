package com.st10083210.hadeda.ui.BirdMap

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.st10083210.hadeda.R
import com.st10083210.hadeda.databinding.HotspotItemBinding
import com.st10083210.hadeda.databinding.HotspotBottomModalBinding

class HotspotListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: HotspotBottomModalBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HotspotBottomModalBinding.inflate(inflater, container, false)
        //dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val recyclerView: RecyclerView = binding.hotspotRv

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<HotspotViewModel>()

        val spot1: HotspotViewModel = HotspotViewModel("Name1", "Location1")
        val spot2: HotspotViewModel = HotspotViewModel("Name2", "Location2")
        val spot3: HotspotViewModel = HotspotViewModel("Name3", "Location3")
        val spot4: HotspotViewModel = HotspotViewModel("Name4", "Location4")
        val spot5: HotspotViewModel = HotspotViewModel("Name5", "Location5")

        data.add(spot1)
        data.add(spot2)
        data.add(spot3)
        data.add(spot4)
        data.add(spot5)

        // This will pass the ArrayList to our Adapter
        val adapter = HotspotAdapter(data)
        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}