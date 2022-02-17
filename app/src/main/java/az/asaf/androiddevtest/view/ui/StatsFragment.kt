package az.asaf.androiddevtest.view.ui

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import az.asaf.androiddevtest.R
import az.asaf.androiddevtest.databinding.FragmentStatsBinding
import az.asaf.androiddevtest.view.adapter.CategoriesAdapter
import az.asaf.androiddevtest.view.dto.BankCard
import az.asaf.androiddevtest.view.dto.Stats
import az.asaf.androiddevtest.view.dto.StatsCategory

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var stats: List<Stats>
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var indexOfStats: Int = 0

    override fun onResume() {
        super.onResume()

        initYearsMenu()
        initMonthsMenu()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stats = makeHardCode()  // simulates retrieving data from previous page

        categoriesAdapter = CategoriesAdapter(stats[0].categories) { selectedCategory ->
            CategoriesBottomDialog(selectedCategory)
                .show(childFragmentManager, "StatsCategories")
        }
        binding.listCategories.adapter = categoriesAdapter

        updateUi(0)
        initBankCardMenu()
    }


    private fun updateUi(index: Int) {
        binding.txtCardName.text = stats[index].bankCards[0].name
        binding.txtLastNums.text = stats[index].bankCards[0].lastNumbers
        binding.txtExpenses.text = stats[index].totalExpenses.toString()
        binding.txtIncoming.text = stats[index].totalIncoming.toString()
        binding.txtCashback.text = stats[index].totalCashback.toString()
        binding.txtTransportPerc.text = "Transport ${stats[index].transportPerc}%"
        binding.txtTransportPrice.text = stats[index].transportPrice.toString()

        categoriesAdapter.updateList(newList = stats[index].categories)
    }

    private fun initBankCardMenu() {
        binding.cardBankCard.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.cardBankCard)
            for (element in stats[indexOfStats].bankCards) {
                popup.menu.add(
                    1, element.id, element.id, "${element.name}  (${element.lastNumbers})"
                )
            }
            popup.setOnMenuItemClickListener { item ->
                for (element in stats[indexOfStats].bankCards) {
                    if (element.id == item.itemId) {
                        binding.txtCardName.text = element.name
                        binding.txtLastNums.text = element.lastNumbers
                    // currently code is not updating after changing the card name, but it can be added
                    }
                }
                true
            }
            popup.show()
        }
    }

    private fun initYearsMenu() {
        val years = arrayOf("2021", "2022")
        binding.txtYear.inputType = InputType.TYPE_NULL
        binding.txtYear.setAdapter(
            ArrayAdapter(requireContext(), R.layout.layout_stats_dropdown_menu, years)
        )
        binding.txtYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkStatsDate()
            }
        })
    }

    private fun initMonthsMenu() {
        val months = arrayOf("January", "February")
        binding.txtMonth.inputType = InputType.TYPE_NULL
        binding.txtMonth.setAdapter(
            ArrayAdapter(requireContext(), R.layout.layout_stats_dropdown_menu, months)
        )
        binding.txtMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkStatsDate()
            }
        })
    }

    private fun checkStatsDate() {
        for (index in stats.indices) {
            if (stats[index].year.toString() == binding.txtYear.text.toString()) {
                if (stats[index].month == binding.txtMonth.text.toString()) {
                    indexOfStats = index
                    updateUi(index)
                }
            }
        }
    }

    private fun makeHardCode(): List<Stats> {
        val expenses = ArrayList<StatsCategory.Expense>()
        val categories = ArrayList<StatsCategory>()
        val bankCards = ArrayList<BankCard>()
        val stats = ArrayList<Stats>()

        expenses.add(StatsCategory.Expense(1, "", "Uber trip 20 1", "100 $","13.02", "17.09.2018"))
        expenses.add(StatsCategory.Expense(2, "", "Uber trip 20 2", "200 $","23.09", "17.09.2019"))
        expenses.add(StatsCategory.Expense(3, "", "Uber trip 20 3", "50 $","16.07", "17.09.2020"))
        expenses.add(StatsCategory.Expense(4, "", "Uber trip 20 4", "350 $","30.05", "17.09.2021"))
        expenses.add(StatsCategory.Expense(5, "", "Uber trip 20 5", "100 $","31.12", "17.09.2022"))
        expenses.add(StatsCategory.Expense(6, "", "Uber trip 20 6", "100 $","31.12", "17.09.2022"))
        expenses.add(StatsCategory.Expense(7, "", "Uber trip 20 7", "100 $","31.12", "17.09.2022"))
        expenses.add(StatsCategory.Expense(8, "", "Uber trip 20 8", "100 $","31.12", "17.09.2022"))
        expenses.add(StatsCategory.Expense(9, "", "Uber trip 20 9", "100 $","31.12", "17.09.2022"))
        expenses.add(StatsCategory.Expense(10, "", "Uber trip 20 10", "100 $","31.12", "17.09.2022"))
        categories.add(StatsCategory(1, "", "Airlines", "January 2021", "22%", "1500 AZN", expenses))
        categories.add(StatsCategory(2, "", "Rent A Car", "February 2021", "35%", "2700 AZN", expenses))
        categories.add(StatsCategory(3, "", "Hotels And Motels", "January 2022", "13%", "300 AZN", expenses))
        categories.add(StatsCategory(4, "", "Transport", "February 2022", "45%", "7800 AZN", expenses))
        categories.add(StatsCategory(5, "", "Cars And Vehicles", "February 2022", "2%", "220 AZN", expenses))
        bankCards.add(BankCard(1, "Expresso MC 1", "", "** 4554"))
        bankCards.add(BankCard(2, "Expresso MC 2", "", "** 9328"))
        bankCards.add(BankCard(3, "Expresso MC 3", "", "** 3841"))
        stats.add(Stats(2021,"January",12550, 17600, 1530, 23, 1342, bankCards, categories))
        stats.add(Stats(2021,"February",13450, 19270, 1635, 30, 1760, bankCards, categories))
        stats.add(Stats(2022,"January",16020, 23493, 1900, 38, 2420, bankCards, categories))
        stats.add(Stats(2022,"February",17880, 34511, 2443, 33, 1967, bankCards, categories))

        return stats
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatsFragment()
    }
}