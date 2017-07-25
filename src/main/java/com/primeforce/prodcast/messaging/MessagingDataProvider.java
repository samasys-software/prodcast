package com.primeforce.prodcast.messaging;

import com.primeforce.prodcast.dao.DatabaseManager;

/**
 * Created by Nandhini on 8/31/2016.
 */
public interface MessagingDataProvider {
    public String[] getData(DatabaseManager databaseManager);
}
