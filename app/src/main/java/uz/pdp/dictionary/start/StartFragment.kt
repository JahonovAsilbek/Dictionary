package uz.pdp.dictionary.start

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import uz.pdp.dictionary.MainActivity
import uz.pdp.dictionary.databinding.FragmentStartBinding
import uz.pdp.dictionary.start.adapters.StartVPAdapter

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStartBinding.inflate(layoutInflater, container, false)

        val title = arrayListOf(
            "O’zingizga mos kursni tanlash",
            "O’quv jarayoni",
            "Real loyihalarni amalga oshirish"
        )
        val text = arrayListOf(
            "Biz nafaqat yurtimizdagi balki butun dunyoda eng rivojlangan sohalar bo'yicha kurslarni taqdim etamiz va tanlash imkonini beramiz.",
            "O'quv jarayoni video darslar, amaliy mashqlar, mentor bilan ishlash imkoniyati hamda live meetinglar orqali olib boriladi.",
            "Har bir kursdagi mavzulashtirilgan video darslarda real loyihalardan qismlar orqali tushuntirib beriladi."
        )
        val vpAdapter = StartVPAdapter(title, text, childFragmentManager)
        binding.vp.adapter = vpAdapter
        binding.tabLayout.setupWithViewPager(binding.vp)

        binding.skip.setOnClickListener {
            startActivity(Intent(binding.root.context, MainActivity::class.java))
        }
        return binding.root
    }
}