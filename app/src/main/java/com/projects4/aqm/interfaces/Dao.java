package com.projects4.aqm.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    List<T> getAll() throws SQLException;
    void insert(String title) throws SQLException;
    void update(T t) throws SQLException;
    void delete(String id) throws SQLException;
}
