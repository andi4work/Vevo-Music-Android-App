package com.friedfishapps.justplay.gui.businessobjects.fragments;

import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friedfishapps.justplay.gui.businessobjects.adapters.OrderableVideoGridAdapter;
import com.friedfishapps.justplay.gui.businessobjects.SimpleItemTouchHelperCallback;
import com.friedfishapps.justplay.gui.fragments.VideosGridFragment;

/**
 * A VideosGridFragment that supports reordering of the videos in the Grid.
 */
public abstract class OrderableVideosGridFragment extends VideosGridFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		if(videoGridAdapter instanceof OrderableVideoGridAdapter) {

			ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((OrderableVideoGridAdapter)videoGridAdapter);
			ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
			touchHelper.attachToRecyclerView(gridView);
		}
		return view;
	}
}
