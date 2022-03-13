package com.uho.artbookkotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.adapter.ArtRecyclerAdapter
import com.uho.artbookkotlin.databinding.FragmentArtsBinding
import com.uho.artbookkotlin.util.OnClickListener
import com.uho.artbookkotlin.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
): Fragment(), OnClickListener {

   private var dataBinding: FragmentArtsBinding? = null
    lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_arts,container,false)
        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        dataBinding!!.clickedListener = this

        subscribeToObservers()

        dataBinding!!.rvArt.adapter = artRecyclerAdapter
        dataBinding!!.rvArt.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(dataBinding!!.rvArt)
    }

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
           return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }
    }

    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer{
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroy() {
        dataBinding = null
        super.onDestroy()
    }

    override fun onFabClicked(v: View) {
        val action = ArtFragmentDirections.actionArtFragmentToArtDetailFragment()
        Navigation.findNavController(v).navigate(action)
    }
}