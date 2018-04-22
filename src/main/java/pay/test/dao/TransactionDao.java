package pay.test.dao;

import pay.test.pay.test.model.Transaction;

import java.util.List;

/**
 * @author Marco Murakami (marco.murakami@movile.com) on 4/20/18.
 */
public interface TransactionDao {

    public void add(Transaction transaction);
    public List<Transaction> list();
    public List<Transaction> listByUserId(String userId);
    public Transaction getByUserIdAndTransactionId(String userId, String transactionId);
    public void sum(Transaction transaction);
    public void search(Transaction transaction);
}
