package com.friedfishapps.justplay.businessobjects.db.Tasks;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import com.friedfishapps.justplay.app.SkyTubeApp;
import com.friedfishapps.justplay.businessobjects.AsyncTaskParallel;
import com.friedfishapps.justplay.businessobjects.Logger;
import com.friedfishapps.justplay.businessobjects.YouTube.POJOs.YouTubeChannel;
import com.friedfishapps.justplay.businessobjects.db.SubscriptionsDb;
import com.friedfishapps.justplay.gui.businessobjects.adapters.SubsAdapter;

/**
 * An Asynctask class that unsubscribes user from all the channels at once.
 */
public class UnsubscribeFromAllChannelsTask extends AsyncTaskParallel<YouTubeChannel, Void, Void> {

	private Handler handler = new Handler();

	@Override
	protected Void doInBackground(YouTubeChannel... youTubeChannels) {
		try {
			List<YouTubeChannel> channelList = SubscriptionsDb.getSubscriptionsDb().getSubscribedChannels();

			for (final YouTubeChannel youTubeChannel : channelList) {
				SubscriptionsDb.getSubscriptionsDb().unsubscribe(youTubeChannel);

				handler.post(new Runnable() {
					@Override
					public void run() {
						SubsAdapter.get(SkyTubeApp.getContext()).removeChannel(youTubeChannel);
					}
				});
			}
		} catch (IOException e) {
			Logger.e(this, "Error while unsubscribing from all channels", e);
		}

		return null;
	}

}
