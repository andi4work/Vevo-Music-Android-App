package com.friedfishapps.justplay.gui.businessobjects;

import com.friedfishapps.justplay.businessobjects.YouTube.POJOs.YouTubePlaylist;

/**
 * Interface for an object that will respond to a Playlist being clicked on
 */
public interface PlaylistClickListener {
	void onClickPlaylist(YouTubePlaylist playlist);
}
