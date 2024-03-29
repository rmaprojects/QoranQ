package com.rmaproject.myqoran.ui.quran.indexby.surah

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
/*
            Code writen by Raka M.A
             */
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.data.QuranDatabase
import com.rmaproject.myqoran.databinding.FragmentQuranIndexBySurahBinding
import com.rmaproject.myqoran.ui.home.QuranViewModel
import com.rmaproject.myqoran.ui.quran.read.surah.FragmentQuranRead

class QuranIndexFragmentBySurah : Fragment(R.layout.fragment_quran_index_by_surah) {

    private val binding:FragmentQuranIndexBySurahBinding by viewBinding()
    private val viewModel:QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) //arrayListof() berguna sebagai default value bagi sebuah array
        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.btmAppBar)

        binding.RecyclerViewAyat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    bottomAppBar?.performHide()
                } else if (dy < 0) {
                    bottomAppBar?.performShow()
                }
            }
        })

        context?.let {
            val database = QuranDatabase.getInstance(it)
            val quranDao = database.quranDao()
            var isFromSurah = false
            //
            quranDao.showQuranIndexBySurah().asLiveData().observe(viewLifecycleOwner, { surahList ->
                viewModel.setTotalAyahList(surahList)
                Log.d("CHECKDATA", surahList.size.toString())
                val adapter = QuranIndexAdapterSurah(surahList) { surah -> // It = ya itu tuh. Bisa diganti
                    val surahNumber: Int = surah.surahNumber ?: 1
                    val surahName:String = surah.surahNameEN.toString()
                    isFromSurah = true
                    val bundle = bundleOf(FragmentQuranRead.KEY_SURAH_NUMBER to surahNumber,
                        FragmentQuranRead.KEY_NAMA_SURAT to surahName,
                        FragmentQuranRead.KEYSURAHNUMBERBOOKMARK to surah.surahNumber,
                        FragmentQuranRead.KEYFROMINDEXSURAH to isFromSurah,
                        FragmentQuranRead.KEYSURAHNUMBERFORSEARCH to surah.surahNumber
                    )
                    findNavController().navigate(R.id.action_nav_home_to_nav_read_quran, bundle)
                }
                binding.RecyclerViewAyat.setAdapter(adapter)
                binding.RecyclerViewAyat.setLayoutManager(LinearLayoutManager(context))
            })

        }

    }

}

