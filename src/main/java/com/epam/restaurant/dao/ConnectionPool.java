package com.epam.restaurant.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

// TODO закрывать соединение, если к нему долго никто не обращается
public class ConnectionPool {
    private enum DBProperties {URL, USER, PASSWORD, DRIVER_NAME, CONNECTION_POOL_SIZE}

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<MyConnection> connections;
    private final String driverName;
    private final String url;
    private final String username;
    private final String password;
    private final int poolSize;

    // Initializing variables for database connection
    private ConnectionPool() {
        ResourceBundle dbProperty = ResourceBundle.getBundle("database");

        url = dbProperty.getString(DBProperties.URL.toString());
        username = dbProperty.getString(DBProperties.USER.toString());
        password = dbProperty.getString(DBProperties.PASSWORD.toString());
        driverName = dbProperty.getString(DBProperties.DRIVER_NAME.toString());
        poolSize = Integer.parseInt(dbProperty.getString(DBProperties.CONNECTION_POOL_SIZE.toString()));
        connections = new ArrayBlockingQueue<>(poolSize);
    }

    // Initializing storage for db connections
    public void initConnectionPool() throws DAOException {
        if (connections.isEmpty()) {
            try {
                Class.forName(driverName);

                for (int i = 0; i < poolSize; i++) {
                    connections.put(new MyConnection(getConnection()));
                }
            } catch (ClassNotFoundException e) {
                throw new DAOException("Error loading the database driver...", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new DAOException("Error when it trying to put connection to the pool", e);
            }
        }
    }

    /**
     * Getting a connection to a database of the Connection type.
     * Used to fill the connections array.
     *
     * @return (Connection) connection
     */
    private Connection getConnection() throws DAOException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DAOException("Connection failed...", e);
        }

        return connection;
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * takes a free connection from an array of connections
     *
     * @return (MyConnection) connection
     */
    public Connection takeConnection() throws DAOException {
        Connection connection = null;

        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("error taking a connection", e);
        }

        return connection;
    }

    public void closeConnection(Connection connection) throws DAOException {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DAOException("error when trying to close the connection...", e);
        }
        ((MyConnection) connection).reallyClose();
    }

    public void closeConnections() throws DAOException {
        for (int i = connections.size(); i > 0; i--) {
            Connection connection = this.takeConnection();
            if (connection != null) {
                this.closeConnection(connection);
            }
        }
    }

    /**
     * Wraps the Connection class to override the close method and some others
     */
    private class MyConnection implements Connection {
        private final Connection connection;

        public MyConnection(Connection connection) {
            this.connection = connection;
        }

        private void putConnection(MyConnection connection) throws InterruptedException {
            connections.put(connection);

        }

        /**
         * Instead of closing the connection, returns it to the connections array
         */
        @Override
        public void close() {
            try {
                putConnection(this);
            } catch (InterruptedException e) {
                LOGGER.error("Error when trying to put a connection in the pool... {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        public void reallyClose() {
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("error to close connection: {}", e.getMessage());
            }
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(java.util.Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }
}