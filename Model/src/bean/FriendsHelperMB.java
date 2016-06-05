package bean;

import backing.ExpenseType;
import backing.PersonType;


import backing.TransactionType;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import utils.StringUtils;

/**
 * Managed bean performs operations on Expenses made by people.
 * Calculates transactions need to be made.
 */
public class FriendsHelperMB {

    private List<PersonType> people = new LinkedList<PersonType>();
    private List<TransactionType> tansactions =
        new LinkedList<TransactionType>();

    private double total;
    private double average;

    private String name;
    private String expense;
    private String sum;

    /**
     * Default constructor.
     */
    public FriendsHelperMB() {
        super();
    }

    /**
     * Checks whether List of people already has a Person with such a name.
     * @param name - passed name.
     * @return true if List of people already has a Person with such a name, false otherwise.
     */
    private boolean hasName(String name) {
        for (PersonType person : people) {
            if (name.trim().toLowerCase().equals(person.getName().trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * If List of people already has a Person with such a name, returns that Person.
     * @param name - passed name.
     * @return Person with such name.
     */
    private PersonType getPersonByName(String name) {
        for (PersonType person : people) {
            if (name.trim().toLowerCase().equals(person.getName().trim().toLowerCase())) {
                return person;
            }
        }
        return null;
    }

    /**
     * Calculates total amount of money spent by all people.
     */
    private void calculateTotal() {
        total = 0;
        for (PersonType person : people) {
            total += person.getTotal();
        }
    }

    /**
     * Calculates average amount of money spent by all people.
     */
    private void calculateAverage() {
        average = total / people.size();
    }

    /**
     * Calculates minimal transactions need to be made and by whom,
     * so that none of the people would owe anyone.
     */
    private void calculateTransactions() {
        for (PersonType person : people) {
            person.setResult(person.getTotal());
            person.setDebt(average - person.getTotal());
        }

        List<PersonType> reciepients = new LinkedList<PersonType>();
        List<PersonType> donors = new LinkedList<PersonType>();
        for (PersonType person : people) {
            if (person.getTotal() > average) {
                reciepients.add(person);
            } else if (person.getTotal() < average) {
                donors.add(person);
            }
        }

        Collections.sort(reciepients, new PersonTypeComparator());
        Collections.sort(donors, new PersonTypeComparator());

        for (PersonType reciepient : reciepients) {
            do {
                for (PersonType donor : donors) {
                    double delta = 0;
                    if (donor.getDebt() <= Math.abs(reciepient.getDebt())) {
                        delta = donor.getDebt();
                    } else {
                        //                        delta =
                        //                                Math.abs(reciepient.getDebt()) - donor.getDebt();
                        delta = Math.abs(reciepient.getDebt());

                    }
                    tansactions.add(new TransactionType(donor.getName(),
                                                        reciepient.getName(),
                                                        delta));

                    donor.setDebt(donor.getDebt() - delta);
                    donor.setResult(donor.getResult() + delta);

                    reciepient.setDebt(reciepient.getDebt() + delta);
                    reciepient.setResult(reciepient.getResult() - delta);

                    if (donor.getDebt() == 0) {
                        donors.remove(donor);
                        break;
                    }
                }
            } while (reciepient.getDebt() != 0);
        }
    }

    /**
     * Compares PersonType instances using their amount of Debt.
     */
    private class PersonTypeComparator implements Comparator<PersonType> {
        @Override
        public int compare(PersonType pt1, PersonType pt2) {
            return Double.valueOf(pt1.getDebt()).compareTo(Double.valueOf(pt2.getDebt()));
        }
    }

    /**
     * Adds Expense to the list according to the correct person.
     * Calculates total and average of money spent by everyone.
     * Calculates transactions need to be made.
     * Clears fields in the UI.
     * @param event - Action Event triggered by 'addExpense' button.
     */
    public void addExpense(ActionEvent event) {
        double sumValue;
        if (StringUtils.isDouble(sum)) {
            sumValue = Double.valueOf(sum);


            if (hasName(name)) {
                getPersonByName(name).addExpense(new ExpenseType(expense,
                                                                 sumValue));
            } else {
                people.add(new PersonType(name,
                                          new ExpenseType(expense, sumValue)));
            }

            clearTransactions();

            if (people != null && people.size() > 0) {
                calculateTotal();
                calculateAverage();
                calculateTransactions();
            }
            clearCurrentStringValues();
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          null, "Sucker!"));
        }
    }

    /**
     * Nullifies list of Transactions.
     */
    public void clearTransactions() {
        tansactions = new LinkedList<TransactionType>();
    }

    /**
     * Nullifies all the Expenses people made.
     * @param event  - Action Event triggered by 'clearExpenses' button.
     */
    public void clearExpenses(ActionEvent event) {
        clearCurrentStringValues();
        total = 0;
        average = 0;
        people = new LinkedList<PersonType>();
        clearTransactions();
    }

    /**
     * Nullifies UI fields' values.
     */
    private void clearCurrentStringValues() {
        name = null;
        expense = null;
        sum = null;
    }

    public List<PersonType> getPeople() {
        return people;
    }

    public List<TransactionType> getTansactions() {
        return tansactions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getExpense() {
        return expense;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getSum() {
        return sum;
    }

    public double getTotal() {
        return total;
    }

    public double getAverage() {
        return average;
    }
}
