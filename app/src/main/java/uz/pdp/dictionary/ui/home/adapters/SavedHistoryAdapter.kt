package uz.pdp.dictionary.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.dictionary.R
import uz.pdp.dictionary.database.models.History
import uz.pdp.dictionary.databinding.ItemLatestBinding

class SavedHistoryAdapter : RecyclerView.Adapter<SavedHistoryAdapter.HistoryVH>() {

    private lateinit var data: List<History>
    var onItemClick: OnItemClick? = null

    fun setAdapter(data: List<History>) {
        this.data = data
    }

    inner class HistoryVH(var itemLatestBinding: ItemLatestBinding) :
        RecyclerView.ViewHolder(itemLatestBinding.root) {

        fun onBind(history: History) {
            itemLatestBinding.word.text = history.word
            itemLatestBinding.transcription.text = history.origin
            if (history.saved == true) {
                itemLatestBinding.save.setImageResource(R.drawable.ic_saved_back)
            } else
                itemLatestBinding.save.setImageResource(R.drawable.ic_save)

            itemLatestBinding.save.setOnClickListener {
                onItemClick?.onSaveClick(history, itemLatestBinding.save)
            }

            itemView.setOnClickListener {
                onItemClick?.onClick(history)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        return HistoryVH(
            ItemLatestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface OnItemClick {
        fun onClick(history: History)
        fun onSaveClick(history: History, imageView: ImageView)
    }
}