package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.view.View;

import com.openclassrooms.entrevoisins.model.Neighbour;

public interface ItemClickListener {

    void itemListener(int position, Neighbour neighbour);
}
