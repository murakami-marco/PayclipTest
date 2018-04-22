package pay.test.controller;

import org.apache.log4j.Logger;
import pay.test.dao.TransactionDao;
import pay.test.dao.file.TransactionDaoImpl;
import pay.test.pay.test.model.Transaction;
import pay.test.pay.test.util.Parser;

import java.util.List;
import java.util.UUID;

/**
 * @author Marco Murakami (marco.murakami@movile.com) on 4/20/18.
 */
public class PayclipController {

    private static final Logger LOG = Logger.getLogger(PayclipController.class);
    private TransactionDao transactionDao;

    public void selectService (String params []) {
        LOG.info("Selecting service");
        if (params.length >= 2) {
            switch (params[1]) {
                case "add" :
                    add(params);
                    break;
                case "list" :
                    list(params);
                    break;
                case "sum" :
                    sum(params);
                    break;
                default:
                    search(params);
            }
        }
    }

    public void add (String params []) {
        LOG.info("add");
        if (params.length == 3) {
            transactionDao = TransactionDaoImpl.getInstance();
            Transaction transaction = Parser.parseJsonStringToTransaction(params[2]);
            transaction.setUuid(UUID.randomUUID().toString());
            transactionDao.add(transaction);
            LOG.info("Resp: " + Parser.parseTransactionToJsonString(transaction));
        } else {
            LOG.info("Wrong format petition. Ensure to make the petition like this: { \"amount\": amount, \"description\": \"Description\", \"date\":\"date\", \"user_id\": user_id }");
        }
    }

    public void list (String params []) {
        LOG.info("list");
        transactionDao = TransactionDaoImpl.getInstance();
        List<Transaction>transactions =  transactionDao.listByUserId(params[0]);
        if (transactions != null && !transactions.isEmpty()) {
            StringBuffer sb = new StringBuffer("[");
            for (Transaction transaction : transactions) {
                sb.append(Parser.parseTransactionToJsonString(transaction)).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(",")).append("]");
            LOG.info("Resp: " + sb.toString());
        } else {
            LOG.info("[]");
        }

    }

//    { “user_id”: 123, “sum”: 234.76 }

    public void sum (String params []) {
        LOG.info("sum");
        transactionDao = TransactionDaoImpl.getInstance();
        List<Transaction> transactions = transactionDao.listByUserId(params[0]);
        if (transactions != null && !transactions.isEmpty()) {
            float sum = 0;
            for (Transaction transaction : transactions) {
                sum += transaction.getAmount();
            }
            StringBuffer sb = new StringBuffer();
            sb.append("{ \"user_id\":").append(params[0]).append(", \"sum\":").append(sum).append(" }");
            LOG.info("Resp: " + sb.toString());
        } else {
            LOG.info("Transactions not found");
        }
    }

    public void search (String params []) {
        LOG.info("search");
        transactionDao = TransactionDaoImpl.getInstance();
        Transaction transaction = transactionDao.getByUserIdAndTransactionId(params[0], params[1]);
        if (transaction != null) {
            LOG.info("Resp: " + Parser.parseTransactionToJsonString(transaction));
        } else {
            LOG.info("Transaction not found");
        }
    }
}
