package com.ermilova.android.diary.ui.details_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.databinding.FragmentDetailsBinding
import com.ermilova.android.diary.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class DetailsScreenFragment : DialogFragment() {

    private lateinit var binding: FragmentDetailsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailsScreenViewModel>

    private val viewModel: DetailsScreenViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val bundle = arguments
        bundle?.let {
            val args = DetailsScreenFragmentArgs.fromBundle(bundle)
            if (viewModel.event.value?.id != args.eventId) {
                viewModel.setId(args.eventId)
            }
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            binding.eventName.text = event.name
            binding.eventStartTime.text = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(event.startTime)
            binding.eventFinishTime.text = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(event.finishTime)
            binding.eventDescription.text = event.description
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeBtn.setOnClickListener() {
            findNavController().navigateUp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as MyApplication).appComponent.detailsScreenComponent().create().inject(this)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}