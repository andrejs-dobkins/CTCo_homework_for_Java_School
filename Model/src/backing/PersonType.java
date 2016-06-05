package backing;

import java.util.LinkedList;
import java.util.List;

/**
 * Backing class for storing Person information.
 */
public class PersonType {

    private String name;
    private List<ExpenseType> expenses = new LinkedList<ExpenseType>();
    private double debt;
    private double result;

    /**
     * Default constructor.
     */
    public PersonType() {
        super();
    }

    /**
     * Parametrized constructor.
     * @param name - passed name.
     * @param expenses - passed list of expenses.
     */
    public PersonType(String name, List<ExpenseType> expenses) {
        super();
        this.name = name;
        this.expenses = expenses;
    }

    /**
     * Parametrized constructor.
     * @param name - passed name.
     * @param expense - passed expense.
     */
    public PersonType(String name, ExpenseType expense) {
        super();
        this.name = name;
        addExpense(expense);
    }

    /**
     * Calculates total amount of money spent.
     * @return
     */
    public double getTotal() {
        double total = 0;
        for (ExpenseType expense : expenses) {
            total += expense.getSum();
        }
        return total;
    }

    /**
     * Adds ExpenseType to the list of ExpenseTypes.
     * @param expense
     */
    public void addExpense(ExpenseType expense) {
        expenses.add(expense);
    }

    public String getName() {
        return name;
    }

    public List<ExpenseType> getExpenses() {
        return expenses;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getDebt() {
        return debt;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }
}
