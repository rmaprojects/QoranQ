package com.rmaproject.myqoran.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
/*
            Code writen by Raka M.A
             */
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.databinding.FragmentHomeBinding
import com.rmaproject.myqoran.ui.aboutus.AboutUsFragment
import com.rmaproject.myqoran.ui.quran.indexby.jozz.QuranIndexFragmentByJozz
import com.rmaproject.myqoran.ui.quran.indexby.page.QuranIndexFragmentByPage
import com.rmaproject.myqoran.ui.quran.indexby.surah.QuranIndexFragmentBySurah
import com.rmaproject.myqoran.ui.rating.RatingFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    //ViewBinding kita tidak harus mengetik findViewById.
    private val bindings: FragmentHomeBinding by viewBinding()
    private val titles:List<String> = listOf("Surah","Juz","Page")
    private val fragmentList:List<Fragment> = listOf(QuranIndexFragmentBySurah(), QuranIndexFragmentByJozz(), QuranIndexFragmentByPage()) //List Fragment yang bakal bisa digeser
    private val viewModel:QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Dengan tanpa ViewBinding
        val textusername:TextView = view.findViewById(R.id.txt_username)
        textusername.setText("Raka M.A")
        //Dengan ViewBinding
        bindings.txtAyahCounter.text = "Ayat 1"
        //Bisa juga dijadikan variable
        val namasurat = bindings.txtSurahName
        namasurat.text = "Al-Baqarah"
       val adapter:ViewPageAdapter = ViewPageAdapter(this, fragmentList)

        bindings.viewPager.adapter = adapter
        bindings.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setPositionTab(position)
            }
        })
        TabLayoutMediator(bindings.tabLayout, bindings.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
//Struktur: variableBinding.id.function
    }
}