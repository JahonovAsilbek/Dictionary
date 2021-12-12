package uz.pdp.dictionary.ui.home

import android.R
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*
import uz.pdp.dictionary.MainActivity
import uz.pdp.dictionary.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var adapter: ArrayAdapter<String>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        drawerClick()

        adapter = ArrayAdapter<String>(
            binding.root.context,
            R.layout.simple_dropdown_item_1line
        )


        binding.searchView.doOnTextChanged { text, _, _, _ ->
            // Call api to find search results for your text:
            DebouncingQueryTextListener(
                requireActivity().lifecycle
            ) { newText ->
                newText?.let {
                    if (it.isEmpty()) {
                        //
                    } else {
                        callApiFor(it)
                    }
                }
            }
        }

        binding.searchView.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // call an API to find your results
                callApiFor(textView.text.toString())
                Toast.makeText(binding.root.context, "bingoo", Toast.LENGTH_SHORT).show()
//                searchResults(textView.text.toString())
                return@setOnEditorActionListener true
            }

            false
        }


        return binding.root
    }

    private fun callApiFor(word: String) {
        homeViewModel.fetchWord(word).observe(viewLifecycleOwner, {
            adapter.clear()
            for (dictionary in it) {
                adapter.add(dictionary.word)
                Log.d("AAAA", "callApiFor: ${dictionary.word}")
            }
            binding.searchView.setAdapter(adapter)
        })
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

    internal class DebouncingQueryTextListener(
        lifecycle: Lifecycle, private val onDebouncingQueryTextChange: (String?) -> Unit
    ) : TextView.OnEditorActionListener, LifecycleObserver {

        var debouncePeriod: Long = 500
        private val coroutineScope: CoroutineScope = lifecycle.coroutineScope
        private var searchJob: Job? = null

        init {
            lifecycle.addObserver(this)
        }

        override fun onEditorAction(newText: TextView?, actionId: Int, event: KeyEvent?): Boolean {

            Log.d("AAAA", "onEditorAction: ")
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.text?.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(newText.text.toString())
                }
            }

            return true
        }

    }
}