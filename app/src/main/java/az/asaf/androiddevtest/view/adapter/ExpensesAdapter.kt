package az.asaf.androiddevtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import az.asaf.androiddevtest.databinding.AdapterExpensesBinding
import az.asaf.androiddevtest.view.dto.StatsCategory

class ExpensesAdapter(
    private var items: List<StatsCategory.Expense>,
    private val onClick: (List<StatsCategory.Expense>) -> Unit
) : RecyclerView.Adapter<ExpensesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        return ExpensesViewHolder(
            AdapterExpensesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.bindTo(items, position)
        holder.itemView.setOnClickListener {
            onClick.invoke(items)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newList: List<StatsCategory.Expense>) {
        val diffUtil = ExpensesDiffUtil(items, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        items = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

class ExpensesViewHolder(private val binding: AdapterExpensesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(items: List<StatsCategory.Expense>, position: Int) {

        val item = items[position]
        binding.txtServiceName.text = item.name
        binding.txtPrice.text = item.price
        binding.txtTime.text = item.time
        binding.txtDate.text = item.date
    }
}

class ExpensesDiffUtil(
    private val oldList: List<StatsCategory.Expense>,
    private val newList: List<StatsCategory.Expense>
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