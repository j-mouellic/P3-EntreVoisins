package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements ItemClickListener {

    private NeighbourApiService mApiService;
    private RecyclerView mRecyclerView;
    public List<Neighbour> favoriteNeighboursList = new ArrayList<>();
    private List<Neighbour> neighbourList;



    public static FavoriteFragment newInstance(){
        FavoriteFragment favorite = new FavoriteFragment();
        return favorite;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }


    private void initList() {
        neighbourList = mApiService.getNeighbours();
        for (Neighbour neighbour : neighbourList){
            if (neighbour.isFavorite()){
                favoriteNeighboursList.add(neighbour);
            }
        }
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(favoriteNeighboursList, this));
    }

      @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void itemListener(Neighbour neighbour) {
        Intent intent = new Intent(requireContext(), ShowNeighbourProfile.class);
        intent.putExtra("neighbour", neighbour);
        requireActivity().startActivity(intent);
    }
}
