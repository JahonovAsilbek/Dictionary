package uz.pdp.dictionary.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.pdp.dictionary.databinding.FragmentStartViewPagerBinding

class StartViewPagerFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("title")
            param2 = it.getString("text")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStartViewPagerBinding.inflate(layoutInflater)

        if (param1 != null && param2 != null) {
            binding.title.text = param1
            binding.text.text = param2
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString("title", param1)
                    putString("text", param2)
                }
            }
    }
}