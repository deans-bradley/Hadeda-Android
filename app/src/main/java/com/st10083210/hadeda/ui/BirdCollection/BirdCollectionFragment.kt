package com.st10083210.hadeda.ui.BirdCollection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.st10083210.hadeda.databinding.FragmentBirdCollectionBinding
import com.st10083210.hadeda.ui.AddBird.AddBirdActivity
import com.st10083210.hadeda.ui.AddBird.BirdModel
import com.st10083210.hadeda.ui.Signin.SigninActivity

class BirdCollectionFragment : Fragment() {

    private var _binding: FragmentBirdCollectionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirdCollectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // TODO: Fix view
        /*
        val profileFragment = ProfileFragment()

        // Get the FragmentManager
        val fragmentManager = parentFragmentManager

        // Begin a transaction to replace the fragment container with your ProfileFragment
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(binding.profile_view, profileFragment) // R.id.profile_fragment is the ID of your fragment placeholder
        transaction.addToBackStack(null) // Optional, to add the transaction to the back stack
        transaction.commit()
        */

        binding.loginBtn.setOnClickListener {
            val intent = Intent(context, SigninActivity::class.java)
            startActivity(intent)
        }

        val recyclerview = binding.rvBirdCollection
        recyclerview.layoutManager = GridLayoutManager(context, 2)

        val adapter = BirdCollectionAdapter(BirdModel.birdLst)
        recyclerview.adapter = adapter

        binding.fabAddBird.setOnClickListener {
            val intent = Intent(context, AddBirdActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}