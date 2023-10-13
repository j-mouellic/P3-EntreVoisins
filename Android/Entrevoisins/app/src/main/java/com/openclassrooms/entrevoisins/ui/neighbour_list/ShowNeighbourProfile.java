package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowNeighbourProfile extends AppCompatActivity {

    private NeighbourApiService mApiService;
    FavoriteFragment favoriteFragment;
    @BindView(R.id.userNameTop)
    TextView userNameTop;
    @BindView(R.id.userNameCardView)
    TextView userNameCardview;
    @BindView(R.id.userAboutMe)
    TextView userAboutMe;
    @BindView(R.id.userPicture)
    ImageView userPicture;
    @BindView(R.id.userAddress)
    TextView userAddress;
    @BindView(R.id.userPhoneNumber)
    TextView userPhoneNumber;
    @BindView(R.id.floatingReturnButton)
    FloatingActionButton floatingReturnButton;

    @BindView(R.id.floatingFavoriteButton)
    FloatingActionButton floatingFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        setContentView(R.layout.activity_show_neighbour_profile);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        /** User details implementation */
        Intent intent = getIntent();
        Neighbour neighbour = (Neighbour) intent.getSerializableExtra("neighbour");
        userNameTop.setText(neighbour.getName());
        userNameCardview.setText(neighbour.getName());
        userAboutMe.setText(neighbour.getAboutMe());
        if (neighbour.isFavorite()){
            floatingFavoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .apply(new RequestOptions().override(1200, 1200))
                .into(userPicture);
        userAddress.setText(neighbour.getAddress());
        userPhoneNumber.setText(neighbour.getPhoneNumber());




        /** Return button */
        floatingReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent returnButton = new Intent(context, ListNeighbourActivity.class);
                context.startActivity(returnButton);
            }
        });

        /** Favorite button */
        floatingFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = neighbour.isFavorite();
                isFavorite = !isFavorite;
                neighbour.setFavorite(isFavorite);
                mApiService.setFavroiteNeighbour(neighbour);

                if (isFavorite) {
                    floatingFavoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                } else {
                    floatingFavoriteButton.setImageResource(R.drawable.ic_star_grey_24dp);
                }
            }
        });
    }
}
