package com.st10083210.hadeda.ui.BirdMap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.st10083210.hadeda.databinding.FragmentBirdMapBinding

class BirdMapFragment : Fragment() {

    private var _binding: FragmentBirdMapBinding? = null

    private val binding get() = _binding!!

    lateinit var bottomSheetFragment: BottomSheetDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBirdMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //bottomSheetFragment = HotspotListDialogFragment()
        //bottomSheetFragment.show(childFragmentManager, "BSDialogFragment")

        // TODO: Call activity after permission is enabled
        binding.mapBtn.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 128)
                return@setOnClickListener
            }
            val intent = Intent(requireContext(), MapActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}