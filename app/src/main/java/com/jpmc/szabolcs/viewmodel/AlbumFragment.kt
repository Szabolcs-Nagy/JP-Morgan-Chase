package com.jpmc.szabolcs.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpmc.szabolcs.R
import com.jpmc.szabolcs.databinding.FragmentBinding
import com.jpmc.szabolcs.model.DatabaseAlbum
import com.jpmc.szabolcs.utils.LoadingState
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Creating the fragment that will inflate
 * and display the list of albums
 */
class AlbumFragment : Fragment() {
    /**
     * A nullable [AlbumAdapter] instance
     */
    private var viewModelAdapter: AlbumAdapter? = null

    /**
     * A ViewModel instance
     */
    private val viewModel by viewModel<AlbumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment,container,false)
        binding.setLifecycleOwner (viewLifecycleOwner)
        binding.viewmodel = viewModel
        viewModelAdapter = AlbumAdapter()

        binding.root.findViewById<RecyclerView>(R.id.rv_albums).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()

    }

    private fun setUpObserver() {
        viewModel.albumsResults.observe(viewLifecycleOwner, Observer<List<DatabaseAlbum>> { album ->
            album?.apply {
                viewModelAdapter?.result = album
            }
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.RUNNING -> {

                }
                LoadingState.Status.SUCCESS -> {

                }
            }
        })
    }
}