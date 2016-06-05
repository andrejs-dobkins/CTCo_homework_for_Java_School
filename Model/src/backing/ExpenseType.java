package backing;

/**
 * Backing class for storing Expenses.
 */
public class ExpenseType {

    private String expense;
    private double sum;

    /**
     * Default constructor.
     */
    public ExpenseType() {
        super();
    }

    /**
     * Parametrized constructor.
     * @param expense - passed expense.
     * @param sum - passed sum.
     */
    public ExpenseType(String expense, double sum) {
        super();
        this.expense = expense;
        this.sum = sum;
    }

    public String getExpense() {
        return expense;
    }

    public double getSum() {
        return sum;
    }
}
