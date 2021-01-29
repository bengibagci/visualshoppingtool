package com.bengibagci.visualshoppingtool;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<ListView> ProductList = new MutableLiveData<>();

    public LiveData<ListView> getProductList() {
        return ProductList;
    }

    public void setProductList(ListView productList) {

        ProductList.setValue(productList);  }
}