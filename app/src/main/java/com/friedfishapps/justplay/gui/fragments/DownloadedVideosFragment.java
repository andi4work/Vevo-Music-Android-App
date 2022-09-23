package com.friedfishapps.justplay.gui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import com.friedfishapps.justplay.R;
import com.friedfishapps.justplay.app.SkyTubeApp;
import com.friedfishapps.justplay.businessobjects.AsyncTaskParallel;
import com.friedfishapps.justplay.businessobjects.VideoCategory;
import com.friedfishapps.justplay.businessobjects.db.DownloadedVideosDb;
import com.friedfishapps.justplay.gui.businessobjects.adapters.OrderableVideoGridAdapter;
import com.friedfishapps.justplay.gui.businessobjects.fragments.OrderableVideosGridFragment;

/**
 * A fragment that holds videos downloaded by the user.
 */
public class DownloadedVideosFragment extends OrderableVideosGridFragment implements DownloadedVideosDb.DownloadedVideosListener {
	@BindView(R.id.noDownloadedVideosText)
	View noDownloadedVideosText;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		videoGridAdapter = new OrderableVideoGridAdapter(getActivity(), DownloadedVideosDb.getVideoDownloadsDb());
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		swipeRefreshLayout.setEnabled(false);
		populateList();
	}


	@Override
	protected int getLayoutResource() {
		return R.layout.fragment_downloads;
	}


	@Override
	protected VideoCategory getVideoCategory() {
		return VideoCategory.DOWNLOADED_VIDEOS;
	}


	@Override
	public String getFragmentName() {
		return SkyTubeApp.getStr(R.string.downloads);
	}


	@Override
	public void onDownloadedVideosUpdated() {
		populateList();
		videoGridAdapter.refresh(true);
	}


	private void populateList() {
		new PopulateDownloadsTask().executeInParallel();
	}


	/**
	 * A task that:
	 *   1. gets the current total number of downloads
	 *   2. updated the UI accordingly (wrt step 1)
	 *   3. get the downloaded videos asynchronously.
	 */
	private class PopulateDownloadsTask extends AsyncTaskParallel<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			return DownloadedVideosDb.getVideoDownloadsDb().getNumDownloads();
		}


		@Override
		protected void onPostExecute(Integer numVideosDownloaded) {
			// If no videos have been bookmarked, show the text notifying the user, otherwise
			// show the swipe refresh layout that contains the actual video grid.
			if (numVideosDownloaded <= 0) {
				swipeRefreshLayout.setVisibility(View.GONE);
				noDownloadedVideosText.setVisibility(View.VISIBLE);
			} else {
				swipeRefreshLayout.setVisibility(View.VISIBLE);
				noDownloadedVideosText.setVisibility(View.GONE);

				// set video category and get the bookmarked videos asynchronously
				videoGridAdapter.setVideoCategory(VideoCategory.DOWNLOADED_VIDEOS);
			}
		}

	}
}