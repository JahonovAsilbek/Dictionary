package uz.pdp.dictionary.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.pdp.dictionary.MainActivity
import uz.pdp.dictionary.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        drawerClick()

        val adapter by lazy {
            ArrayAdapter<String>(
                binding.root.context,
                R.layout.simple_dropdown_item_1line
            )
        }
        adapter.addAll("ashdbs", "djhsdc", "shcbsdkhc", "dkshbskd", "sdcskcsd", "sdjcbsdh")

        binding.searchView.setAdapter(adapter)
        binding.searchView.doOnTextChanged { text, _, _, _ ->
            // Call api to find search results for your text:

//            callApiFor(text.toString())
        }

        binding.searchView.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // call an API to find your results
                Toast.makeText(binding.root.context, "bingoo", Toast.LENGTH_SHORT).show()
//                searchResults(textView.text.toString())
                return@setOnEditorActionListener true
            }

            false
        }


        return binding.root
    }

    private fun drawerClick() {
        binding.drawerBtn.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}