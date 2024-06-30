package com.guy.class24b_andb_2;

import android.os.Bundle;

import com.guy.common.Activity_PanelBase;

public class Activity_Panel extends Activity_PanelBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataManagerBase = new DataManagerCars();
        super.onCreate(savedInstanceState);


    }

}