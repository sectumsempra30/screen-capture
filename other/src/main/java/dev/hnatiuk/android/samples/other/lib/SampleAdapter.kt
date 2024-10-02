package dev.hnatiuk.android.samples.other.lib;

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hnatiuk.android.samples.other.R
import dev.hnatiuk.android.samples.other.databinding.ItemHeaderBinding
import dev.hnatiuk.android.samples.other.databinding.ItemListBinding
import dev.hnatiuk.android.samples.other.lib.v2.StickHeaderItemDecoration

sealed interface SampleItem {

    data class SampleModel(
        val id: Int,
        val title: String
    ) : SampleItem

    data class HeaderModel(
        val id: Int,
        val text: String,
        val backgroundColor: Int
    ) : SampleItem
}

object SampleModelDiffCallback : DiffUtil.ItemCallback<SampleItem>() {

    override fun areItemsTheSame(oldItem: SampleItem, newItem: SampleItem): Boolean {
        return when {
            oldItem is SampleItem.SampleModel && newItem is SampleItem.SampleModel -> oldItem.id == newItem.id
            oldItem is SampleItem.HeaderModel && newItem is SampleItem.HeaderModel -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: SampleItem, newItem: SampleItem): Boolean {
        return oldItem == newItem
    }

}

typealias OnClickListener = (view: View, index: Int) -> Unit

class SampleAdapter(
    private val onClick: OnClickListener
) : ListAdapter<SampleItem, RecyclerView.ViewHolder>(SampleModelDiffCallback), StickHeaderItemDecoration.StickyHeaderInterface {

    companion object {

        private val ITEM = R.layout.item_list
        private val HEADER = R.layout.item_header
    }

    override fun getHeaderPositionForItem(_itemPosition: Int): Int {
        var itemPosition = _itemPosition
        var headerPosition = 0
        do {
            if (isHeader(itemPosition)) {
                headerPosition = itemPosition
                break
            }
            itemPosition -= 1
        } while (itemPosition >= 0)
        return headerPosition
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        //TODO("Not yet implemented")
        return HEADER
    }

    override fun bindHeaderData(header: View?, headerPosition: Int) {
        //TODO("Not yet implemented")
    }

    override fun isHeader(itemPosition: Int): Boolean {
        return currentList[itemPosition] is SampleItem.HeaderModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = getViewFromViewType(viewType, parent)
        return when (viewType) {
            ITEM -> SampleViewHolder.create(view, onClick)
            HEADER -> HeaderViewHolder.create(view, onClick)
            else -> throw IllegalArgumentException("No ViewHolder for $viewType type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listItem = currentList[position]
        Log.i("checkOnBind", listItem.toString())
        when (holder) {
            is SampleViewHolder -> holder.populate(listItem as SampleItem.SampleModel)
            is HeaderViewHolder -> holder.populate(listItem as SampleItem.HeaderModel)
            else -> throw IllegalArgumentException("ViewHolder $holder is not supported")
        }
    }

    override fun getItemViewType(position: Int) = when (currentList[position]) {
        is SampleItem.SampleModel -> ITEM
        is SampleItem.HeaderModel -> HEADER
        else -> ITEM
    }

    private fun getViewFromViewType(viewType: Int, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        return layoutInflater.inflate(viewType, parent, false)
    }
}

class HeaderViewHolder(
    view: View,
    onClick: OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {

        fun create(view: View, onClick: OnClickListener) = HeaderViewHolder(view, onClick)
    }

    private val binding = ItemHeaderBinding.bind(view)

    init {
        itemView.setOnClickListener {
            onClick.invoke(it, adapterPosition)
        }
    }

    fun populate(model: SampleItem.HeaderModel) = with(binding) {
        //root.setBackgroundColor(model.backgroundColor)
        headerText.text = model.text
    }
}

class SampleViewHolder(
    view: View,
    onClick: OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {

        fun create(view: View, onClick: OnClickListener) = SampleViewHolder(view, onClick)
    }

    private val binding = ItemListBinding.bind(view)

    init {
        itemView.setOnClickListener {
            onClick.invoke(it, adapterPosition)
        }
    }

    fun populate(model: SampleItem.SampleModel) = with(binding) {
        title.text = model.title
    }
}