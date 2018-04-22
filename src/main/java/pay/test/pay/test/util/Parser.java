package pay.test.pay.test.util;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import pay.test.pay.test.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.UUID;


/**
 * @author Marco Murakami (marco.murakami@movile.com) on 4/22/18.
 */
public class Parser {

    private static final Logger LOG = Logger.getLogger(Parser.class);

    public static Transaction parseJsonStringToTransaction (String jsonString){
        Transaction transaction = null;
        try {
            JsonObject jsonObject = (new com.google.gson.JsonParser()).parse(jsonString).getAsJsonObject();

            transaction = new Transaction();

            String uuid = (jsonObject.get("transaction_id") != null) ? jsonObject.get("transaction_id").getAsString() : UUID.randomUUID().toString();

            transaction.setUuid(uuid);
            transaction.setAmount(jsonObject.get("amount").getAsFloat());

            String dateStr = jsonObject.get("date").getAsString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            transaction.setDate(sdf.parse(dateStr));
            transaction.setDescription(jsonObject.get("description").getAsString());
            transaction.setUserId(jsonObject.get("user_id").getAsInt());
        } catch (Exception e) {
            LOG.error("Error parsing String to Json: " + jsonString);
            LOG.error(e);
        }
        return transaction;
    }

//    { "amount": 1.23, "description": "Joes Tacos", "date":"2018-12-30", "user_id": 345 }

    public static String parseTransactionToJsonString (Transaction transaction){
        LOG.info("Parsing Transaction to String in Json format");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuffer sb = new StringBuffer();
        sb.append("{ ").
                append("\"transaction_id\":\"").append(transaction.getUuid()).append("\", ").
                append("\"amount\":").append(transaction.getAmount()).append(", ").
                append("\"description\":\"").append(transaction.getDescription()).append("\", ").
                append("\"date\":\"").append(sdf.format(transaction.getDate())).append("\", ").
                append("\"user_id\":").append(transaction.getUserId()).
        append(" }");
        return sb.toString();
    }
}
