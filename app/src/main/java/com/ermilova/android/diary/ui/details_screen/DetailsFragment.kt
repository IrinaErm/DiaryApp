package com.ermilova.android.diary.ui.details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.databinding.FragmentDetailsBinding

class DetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val bundle = arguments
        bundle?.let {
            val args = DetailsFragmentArgs.fromBundle(bundle)
            binding.eventName.text = args.eventId.toString()
        }

        binding.closeBtn.setOnClickListener() {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}