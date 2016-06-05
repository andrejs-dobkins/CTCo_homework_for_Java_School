package backing;

/**
 * Backing class for storing Transactions.
 */
public class TransactionType {

    private String donor;
    private String reciepient;
    private double sum;


    /**
     * Default constructor.
     */
    public TransactionType() {
        super();
    }

    /**
     * Parametrized constructor.
     * @param donor - passed donor's name.
     * @param reciepient - passed reciepient's name.
     * @param sum - passed sum.
     */
    public TransactionType(String donor, String reciepient, double sum) {
        super();
        this.donor = donor;
        this.reciepient = reciepient;
        this.sum = sum;
    }

    public String getDonor() {
        return donor;
    }

    public String getReciepient() {
        return reciepient;
    }

    public double getSum() {
        return sum;
    }
}
