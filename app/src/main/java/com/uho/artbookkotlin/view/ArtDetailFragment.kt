package com.uho.artbookkotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.databinding.FragmentArtDetailsBinding
import com.uho.artbookkotlin.util.Status
import com.uho.artbookkotlin.viewmodel.ArtViewModel
import javax.inject.Inject


class ArtDetailFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel
    private var binding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val fragmentBinding = FragmentArtDetailsBinding.bind(view)
        binding = fragmentBinding

        subscribeToObservers()

        binding!!.ivArtDetail.setOnClickListener {
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment())
        }

        val callBack = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding!!.btnSaveArt.setOnClickListener {
            viewModel.makeArt(binding!!.etArtDetailName.text.toString(),
            binding!!.etArtistDetailName.text.toString(),
            binding!!.etArtistDetailYear.text.toString())
        }
    }

    private fun subscribeToObservers(){
        viewModel.selectedImageURL.observe(viewLifecycleOwner, Observer { url ->
            binding?.let {
                glide.load(url).into(it.ivArtDetail)
            }
        })

        viewModel.insertedArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMessage()
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING ->{

                }
            }
        })
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}