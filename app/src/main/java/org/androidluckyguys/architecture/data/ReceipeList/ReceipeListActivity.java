package org.androidluckyguys.architecture.data.ReceipeList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.androidluckyguys.architecture.R;


/**
 * Created by LuckyRana on 04/02/2018.
 */

public class ReceipeListActivity extends AppCompatActivity{

    ReceipeListFragment mReciepeListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipe_list_activity);



        if(savedInstanceState == null) {
            addReciepeListFragment();
        }
    }

    private void addReciepeListFragment() {

        if(mReciepeListFragment == null){
            mReciepeListFragment = ReceipeListFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.receipe_fragment_container,mReciepeListFragment)
                .commit();
    }
}
