package az.asaf.androiddevtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import az.asaf.androiddevtest.databinding.AdapterCategoriesBinding
import az.asaf.androiddevtest.view.dto.StatsCategory

class CategoriesAdapter(
    private var items: List<StatsCategory>,
    private val onClick: (StatsCategory) -> Unit
) : RecyclerView.Adapter<CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            AdapterCategoriesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bindTo(items, position)
        holder.itemView.setOnClickListener {
            onClick.invoke(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newList: List<StatsCategory>) {
        val diffUtil = CategoriesDiffUtil(items, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        items = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

class CategoriesViewHolder(private val binding: AdapterCategoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(items: List<StatsCategory>, position: Int) {

        val item = items[position]
        binding.txtCategoryName.text = item.name
        binding.txtPercentage.text = item.percentage
        binding.txtPrice.text = item.price
    }
}

class CategoriesDiffUtil(
    private val oldList: List<StatsCategory>,
    private val newList: List<StatsCategory>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}