package com.st10083210.hadeda.ui.BirdIndex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.st10083210.hadeda.databinding.FragmentBirdIndexBinding

class BirdIndexFragment : Fragment() {

    private var _binding: FragmentBirdIndexBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirdIndexBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerview = binding.rvBirdIndex
        recyclerview.layoutManager = LinearLayoutManager(context)

        val adapter = BirdIndexAdapter(BirdIndexModel.indexList)
        recyclerview.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}