package az.asaf.androiddevtest.view.dto


data class Stats(
    val year: Int,
    val month: String,
    val totalExpenses: Int,
    val totalIncoming: Int,
    val totalCashback: Int,
    val transportPerc: Int,
    val transportPrice: Int,
    val bankCards: List<BankCard>,
    val categories: List<StatsCategory>
)

data class BankCard(
    val id: Int,
    val name: String,
    val photo: String,
    val lastNumbers: String
)

data class StatsCategory(
    val id: Int,
    val photo: String,
    val name: String,
    val date: String,
    val percentage: String,
    val price: String,
    val expenses: List<Expense>
) {
    data class Expense(
        val id: Int,
        val photo: String,
        val name: String,
        val price: String,
        val time: String,
        val date: String
    )
}