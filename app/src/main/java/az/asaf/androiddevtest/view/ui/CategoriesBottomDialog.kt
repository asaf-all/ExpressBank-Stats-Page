package az.asaf.androiddevtest.view.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import az.asaf.androiddevtest.R
import az.asaf.androiddevtest.databinding.DialogStatsCategoriesBinding
import az.asaf.androiddevtest.view.adapter.ExpensesAdapter
import az.asaf.androiddevtest.view.dto.StatsCategory
import az.asaf.androiddevtest.view.extension.dpToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.roundToInt

class CategoriesBottomDialog(
    private val category: StatsCategory
) : BottomSheetDialogFragment() {

    private var _binding: DialogStatsCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.StatsCategoriesBottomDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogStatsCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtCategoryName.text = category.name
        binding.txtDate.text = "Expenses for ${category.name}"
        binding.txtPrice.text = category.price
        binding.txtPercentage.text = "${category.percentage} of all"


        binding.expensesList.adapter = ExpensesAdapter(category.expenses) { expense -> }
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val bottomSheet: View =
                dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val marginTop = 140.dpToPx(requireContext()).roundToInt()
            view?.post {
                val parent = view?.parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior as CoordinatorLayout.Behavior
                val dialogBehavior = behavior as BottomSheetBehavior
                dialogBehavior.peekHeight = (view?.measuredHeight ?: marginTop) - marginTop
                (bottomSheet.parent as View).setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }
}