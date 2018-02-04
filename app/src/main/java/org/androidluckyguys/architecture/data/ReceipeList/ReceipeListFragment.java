package org.androidluckyguys.architecture.data.ReceipeList;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.androidluckyguys.architecture.R;
import org.androidluckyguys.architecture.data.data.Receipe;
import org.androidluckyguys.architecture.data.viewModel.ReciepeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReceipeListFragment extends Fragment {


    private ReciepeViewModel mReceipeViewModel;

    @BindView(R.id.receipes_recyclerview)
    RecyclerView receipesRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private ReceipesListAdapter mReceipesListAdapter;
    private ArrayList<Receipe> mReceipesArrayList = new ArrayList<>();

    public ReceipeListFragment() {
        // Required empty public constructor
    }

    public static ReceipeListFragment newInstance(){
        ReceipeListFragment homeFragment = new ReceipeListFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceipeViewModel = ViewModelProviders.of(getActivity()).get(ReciepeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.receipe_fragment, container, false);

        ButterKnife.bind(this,rootView);

        setupReceipesRecyclerView();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadReceipes();

    }

    private void setupReceipesRecyclerView() {
        receipesRecyclerView.setHasFixedSize(true);
        receipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mReceipesListAdapter = new ReceipesListAdapter(getActivity(), mReceipesArrayList,
                new ReceipesListAdapter.ReceipesListAdapterListener() {
                    @Override
                    public void onReceipeClicked(Receipe receipe) {
                        loadReceipeDetailsOnUI(receipe);
                    }
                });
        receipesRecyclerView.setAdapter(mReceipesListAdapter);
    }

    private void loadReceipeDetailsOnUI(Receipe receipe) {

        Toast.makeText(getActivity(),receipe.getName(),Toast.LENGTH_SHORT).show();


    }

    private void loadReceipes() {

        progressBar.setVisibility(View.VISIBLE);


        //  First way to get data
       /* mReceipeViewModel.getReceipesListData().observe(this, new Observer<Receipe[]>() {
            @Override
            public void onChanged(@Nullable Receipe[] receipes) {
                if (receipes != null) {
                    updateReceipesListDataOnUI(receipes);
                   // receipesListDataMediatorLiveData.removeObserver(this);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });*/



        // Second way to perform same task
        // only differnece this technique we are explicitly removing the observer once data loaded.
        final LiveData<Receipe[]> receipesListDataMediatorLiveData = mReceipeViewModel.getReceipesListData();

        Observer<Receipe[]> receipiesListDataResponseObserver = new Observer<Receipe[]>() {
            @Override
            public void onChanged(@Nullable Receipe[] receipes) {
                if (receipes != null) {
                    updateReceipesListDataOnUI(receipes);
                    receipesListDataMediatorLiveData.removeObserver(this);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        receipesListDataMediatorLiveData.removeObservers(this);
        receipesListDataMediatorLiveData.observe(ReceipeListFragment.this, receipiesListDataResponseObserver);
    }

    private void updateReceipesListDataOnUI(Receipe[] receipesArray) {

        if(receipesArray.length > 0){
            mReceipesArrayList.clear();

            List<Receipe> receipes = Arrays.asList(receipesArray);
            mReceipesArrayList.addAll(receipes);
            mReceipesListAdapter.notifyDataSetChanged();
        }

        progressBar.setVisibility(View.GONE);
    }

}
