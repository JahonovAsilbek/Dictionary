package uz.pdp.dictionary.ui.home

import android.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import uz.pdp.dictionary.MainActivity
import uz.pdp.dictionary.database.AppDatabase
import uz.pdp.dictionary.database.WordDao
import uz.pdp.dictionary.database.models.Dictionary
import uz.pdp.dictionary.database.models.History
import uz.pdp.dictionary.databinding.FragmentHomeBinding
import uz.pdp.dictionary.retrofit.ApiClient
import uz.pdp.dictionary.retrofit.ApiHelperImp
import uz.pdp.dictionary.ui.home.adapters.HistoryAdapter
import uz.pdp.dictionary.utils.Status
import uz.pdp.dictionary.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var adapter: ArrayAdapter<String>
    lateinit var getDao: WordDao
    val suggestions = ArrayList<Dictionary>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        drawerClick()
        setupSearch()
        copyClick()


        return binding.root
    }

    private fun setHistory() {
        val historyAdapter = HistoryAdapter()
        homeViewModel.getLatest().observe(viewLifecycleOwner, {
            historyAdapter.setAdapter(it)
            binding.bottomsheet.rvLatest.adapter = historyAdapter
        })
    }

    private fun voiceClick() {
        binding.bottomsheet.layout2.setOnClickListener {
            if (suggestions.isNotEmpty()) {
                val uri = "https:${suggestions.last().phonetics.last().audio}"
                val mediaPlayer = MediaPlayer.create(binding.root.context, Uri.parse(uri))
                mediaPlayer?.start()
            }
        }
    }

    private fun copyClick() {
        binding.bottomsheet.layout1.setOnClickListener {
            val clipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", binding.bottomsheet.word.text.toString())
            clipboardManager.setPrimaryClip(clipData)
        }
    }

    override fun onStart() {
        super.onStart()
        loadDataToView()
        setHistory()
    }

    private fun loadDataToView() {
        setupViewModel()
        setupDate()
        setupDatabase()
        loadWord()
        voiceClick()
    }

    private fun loadWord() {
        homeViewModel.getLatest().observe(viewLifecycleOwner, { data ->
            if (data.isEmpty()) {
                callApiFor("kind")
            } else {
                data.last().word?.let { callApiFor(it) }
                binding.bottomsheet.word.text = data.last().word
            }
        })
    }

    private fun setupDate() {
        val format = SimpleDateFormat("dd.MM.yyyy").format(Date(System.currentTimeMillis()))
        binding.bottomsheet.date.text = format
    }

    private fun setupDatabase() {
        getDao = AppDatabase.getDatabase().wordDao()
    }

    private fun setupSearch() {
        adapter = ArrayAdapter<String>(
            binding.root.context,
            R.layout.simple_dropdown_item_1line
        )
        binding.searchView.setAdapter(adapter)

        //text change listener
        binding.searchView.doOnTextChanged { text, start, before, count ->
            callApiFor(text.toString())
        }

        //suggestion click
        binding.searchView.setOnItemClickListener { parent, view, position, id ->
            val text = (view as TextView).text.toString()
            callApiFor(text)
            binding.bottomsheet.word.text = text
            homeViewModel.insertHistory(
                History(
                    word = text,
                    saved = false,
                    origin = suggestions.last().origin
                )
            )
        }

        //search click
        binding.searchView.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callApiFor(textView.text.toString())

                homeViewModel.insertHistory(
                    History(
                        word = textView.text.toString(),
                        origin = suggestions.last().origin,
                        saved = false
                    )
                )

                binding.bottomsheet.word.text = textView.text.toString()
                return@setOnEditorActionListener true
            }

            false
        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(ApiHelperImp(ApiClient.apiService))
            )[HomeViewModel::class.java]
    }

    private fun callApiFor(word: String) {
        homeViewModel.getWords(word).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.clear()
                    suggestions.clear()
                    for (dictionary in it.data!!) {
                        adapter.add(dictionary.word)
                        suggestions.add(dictionary)
                        Log.d("AAAA", "callApiFor: ${dictionary.word}")
                        adapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {}
            }

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

}