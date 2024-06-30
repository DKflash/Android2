package com.guy.common;

import java.util.List;

public abstract class DataManagerBase<T> {

    // Method to fetch data from a source (e.g., a database or an API)
    public abstract void fetchData(DataCallback<T> callback);

    // Method to add a new data item
    public abstract void addData(T data, DataCallback<T> callback);

    // Method to update an existing data item
    public abstract void updateData(T data, DataCallback<T> callback);

    // Method to delete a data item
    public abstract void deleteData(T data, DataCallback<T> callback);

    // Interface to handle data operations callbacks
    public interface DataCallback<T> {
        void onSuccess(List<T> data);
        void onFailure(Exception e);
    }
}
