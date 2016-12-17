package cz.GravelCZLP.MinecraftBot.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.jsonbeans.Json;

public class FriendUtils {

	public static boolean isAggressive = false;
	
	private static ArrayList<String> friends = new ArrayList<>();
	
	public static void saveFriends() throws IOException {
		File dataFolder = new File("./botData/friends");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		if (dataFolder.isFile()) {
			dataFolder.delete();
			dataFolder.mkdir();
		}
		Json json = new Json();
		String jsonText = json.toJson(friends, ArrayList.class);
		jsonText = json.prettyPrint(jsonText);
		File friendsFile = new File(dataFolder + "/friends.json");
		if (friendsFile.isDirectory()) {
			friendsFile.delete();
		}
		if (!friendsFile.exists()) {
			friendsFile.createNewFile();
		} else {
			friendsFile.delete();
			friendsFile.createNewFile();
		}
		FileWriter writer = new FileWriter(friendsFile);
		writer.write(jsonText);
		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadFriends() throws IOException {
		File dataFolder = new File("./botData/friends");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		if (dataFolder.isFile()) {
			dataFolder.delete();
			dataFolder.mkdir();
		}
		File friendsFile = new File(dataFolder + "/friends.json");
		if (friendsFile.isDirectory()) {
			friendsFile.delete();
		}
		if (!friendsFile.exists()) {
			friendsFile.createNewFile();
		} else {
			friendsFile.delete();
			friendsFile.createNewFile();
		}
		Json json = new Json();
		friends = json.fromJson(ArrayList.class, friendsFile);
	}
	
	public static ArrayList<String> getFriends() {
		return friends;
	}
	
	public static void addFriend(String name) {
		friends.add(name);
	}
}
