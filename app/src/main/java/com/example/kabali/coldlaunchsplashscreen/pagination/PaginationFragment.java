package com.example.kabali.coldlaunchsplashscreen.pagination;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kabali.coldlaunchsplashscreen.R;

public class PaginationFragment extends Fragment {

    private int PAGE_SIZE = 6;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.pagination_fragment,container,false);

        return rootview;
    }
}
