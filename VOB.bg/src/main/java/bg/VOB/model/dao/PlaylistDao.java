package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import java.sql.Timestamp;

import bg.VOB.controller.DBManager;
import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;

public class PlaylistDao implements IPlaylistDao {

	private static PlaylistDao instance;
	private Connection connection;

	private PlaylistDao() {
		connection = DBManager.getInstance().getConnection();
	}

	public synchronized static PlaylistDao getInstance() {
		if (instance == null) {
			instance = new PlaylistDao();
		}
		return instance;
	}

	// Add a new playlist and save it in DB
	@Override
	public Playlist addPlaylist(User u, String name) throws SQLException{
		Playlist p = new Playlist(name);
		savePlaylistInDB(u, p);
		return p;
	}

	// Save a playlist in the database
	// TODO make it private if its not use anywhere else
	@Override
	public void savePlaylistInDB(User u, Playlist p) throws SQLException{
		String sql = "INSERT INTO playlist(name, date, user_id) VALUES(?,?,?)";
		Date date = new Date();
		Object param = new Timestamp(date.getTime());
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, p.getName());
			ps.setObject(2, param);
			ps.setInt(3, u.getId());
			ps.executeUpdate();
		} 
	}

	// Add a video to a playlist and save it in DB
	@Override
	public void addVideoToPlaylist(User u, String videoName, String playlistName) throws SQLException{
		Playlist p = getPLaylistByUserAndName(u, playlistName);
		Video v = VideoDao.getInstance().getVideoByUserAndName(u, videoName);
		saveVideoInPlaylistInDB(p, v.getId());
	}

	@Override
	public Playlist getPLaylistByUserAndName(User u, String name) throws SQLException{
		String sql = "SELECT id FROM playlist WHERE user_id = ? AND name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Playlist(rs.getInt(1), name);
			}
		} 
		return null;
	}

	
	public Playlist getPLaylistByName(String name) throws SQLException{
		String sql = "SELECT id, name, date, user_id FROM playlist WHERE name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Playlist(rs.getInt("id"), rs.getString("name"),rs.getTimestamp("date").toLocalDateTime(),rs.getInt("user_id"));
			}
		} 
		return null;
	}
	
	@Override
	public Playlist getPLaylistByUser(User u) throws SQLException{
		String sql = "SELECT id,name FROM playlist WHERE user_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Playlist(rs.getInt("id"), rs.getString("name"));
			}
		} 
		return null;
	}

	@Override
	public void saveVideoInPlaylistInDB(Playlist p, int videoId) throws SQLException{
		String sql = "INSERT INTO playlist_has_video(playlist_id, video_id) VALUES(?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, p.getId());
			ps.setInt(2, videoId);
			ps.executeUpdate();
		} 
	}
	
	public void removeVideoFromPlaylistInDB(Playlist p, int videoId) throws SQLException{
		String sql = "DELETE FROM playlist_has_video WHERE playlist_id = ? AND video_id =?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, p.getId());
			ps.setInt(2, videoId);
			ps.executeUpdate();
		}
	}

	@Override
	public boolean checkIfVideoIsInPlaylist(Playlist p, int videoId) throws SQLException{
		String sql = "SELECT playlist_id, video_id FROM playlist_has_video WHERE playlist_id = ? AND video_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, p.getId());
			ps.setInt(2, videoId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} 
		return false;
	}

	@Override
	public ArrayList<Video> getVideosFromPlaylist(User u) throws SQLException{
		Playlist p = getPLaylistByUser(u);
		ArrayList<Video> videos = new ArrayList<>();
		if(p != null) {
			String sql = "SELECT v.id,v.name,v.date,v.views,v.description,v.path FROM video AS v "
						+ "JOIN playlist_has_video AS p ON v.id = p.video_id JOIN playlist AS l ON p.playlist_id = l.id WHERE l.user_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					videos.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(),
							0,0,0, rs.getString("description"), rs.getString("path")));
				}
			} 
		}
		return videos;
	}

	@Override
	public ArrayList<Playlist> searchForPlaylist(String text) throws SQLException{
		ArrayList<Playlist> matches = new ArrayList<>();
		String sql = "SELECT id, name, date, user_id FROM playlist WHERE name LIKE ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%" + text + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				matches.add(new Playlist(rs.getInt("id"), rs.getString("name"),
						rs.getTimestamp("date").toLocalDateTime(), rs.getInt("user_id")));
			}

		} 
		return matches;
	}
}
