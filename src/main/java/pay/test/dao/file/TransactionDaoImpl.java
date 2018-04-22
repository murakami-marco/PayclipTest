package pay.test.dao.file;

import org.apache.log4j.Logger;
import pay.test.dao.TransactionDao;
import pay.test.pay.test.model.Transaction;
import pay.test.pay.test.util.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Marco Murakami (marco.murakami@movile.com) on 4/20/18.
 */
public class TransactionDaoImpl implements TransactionDao{

    private static final TransactionDaoImpl INSTANCE = new TransactionDaoImpl();

    private static final Logger LOG = Logger.getLogger(TransactionDaoImpl.class);
    private static final String dbFile = "data.txt";
    private File dataFile;

    public static TransactionDaoImpl getInstance() {
        return INSTANCE;
    }

    private TransactionDaoImpl() {
        Path dPath = FileSystems.getDefault().getPath("", dbFile);
        dataFile = new File(dPath.toString());
    }

    @Override
    public void add(Transaction transaction) {
        LOG.info("add");
        LOG.info("Path: " + dataFile.getAbsolutePath());

        boolean existing = false;
        List<Transaction> transactions = list();
        for (Transaction savedTransaction: transactions) {
            if (savedTransaction.getUserId().equals(transaction.getUserId()) &&
                    savedTransaction.getAmount().equals(transaction.getAmount()) &&
                    savedTransaction.getDate().equals(transaction.getDate()) &&
                    savedTransaction.getDescription().equals(transaction.getDescription()) ) {
                existing = true;
                LOG.info("Record already exist");
                break;
            }
        }
        if (!existing) {
            transaction.setUuid(UUID.randomUUID().toString());
            transactions.add(transaction);
            syncToFile(transactions);
        }
    }

    @Override
    public List<Transaction> list() {
        LOG.info("Get all records");
        Scanner sc;
        String record = null;
        List<Transaction> transactions = new ArrayList<Transaction>();

        try {
            sc = new Scanner(dataFile);
            while (sc.hasNextLine()) {
                record = sc.nextLine();
                Transaction transaction = Parser.parseJsonStringToTransaction(record);
                transactions.add(transaction);
            }
        } catch (FileNotFoundException e) {
            LOG.error("No records found");
        }
        return transactions;
    }

    @Override
    public List<Transaction> listByUserId(String userId) {
        Scanner sc;
        String record = null;
        List<Transaction> transactions = new ArrayList<Transaction>();

        try {
            sc = new Scanner(dataFile);
            while (sc.hasNextLine()) {
                record = sc.nextLine();
                Transaction transaction = Parser.parseJsonStringToTransaction(record);
                if (Integer.valueOf(userId).equals(transaction.getUserId())) {
                    transactions.add(transaction);
                }
            }
        } catch (FileNotFoundException e) {
            LOG.error("No records found");
        }
        return transactions;
    }

    @Override
    public Transaction getByUserIdAndTransactionId(String userId, String transactionId) {
        Scanner sc;
        String record = null;
        Transaction transaction = null;

        try {
            sc = new Scanner(dataFile);
            while (sc.hasNextLine()) {
                record = sc.nextLine();
                Transaction savedTransaction = Parser.parseJsonStringToTransaction(record);
                if (Integer.valueOf(userId).equals(savedTransaction.getUserId()) && transactionId.equals(savedTransaction.getUuid())) {
                    transaction = savedTransaction;
                }
            }
        } catch (FileNotFoundException e) {
            LOG.error("No records found");
        }
        return transaction;
    }

    @Override
    public void sum(Transaction transaction) {

    }

    @Override
    public void search(Transaction transaction) {

    }

    public void syncToFile (List<Transaction> transactions) {
        LOG.info("Sync info to datafile");
        if (transactions == null) {
            return;
        }
        try {
            FileWriter out = new FileWriter(dataFile);
            for (Transaction transaction: transactions) {
                String resp = Parser.parseTransactionToJsonString(transaction);
                out.append(resp + "\n");
            }
            out.close();
        }catch (IOException e) {
            LOG.error(e);
        }
    }
}
