package jgame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Items {
	private static final List<Item> items;

	static {
		String itemInfo = null;
		try {
			itemInfo = new String(Files.readAllBytes(Paths.get("res/items/item_ids.txt")));
		} catch (IOException e) {
			System.out.println("An error has occured: Unable to read item file");
			e.printStackTrace();
		}
		String[] allItemInfo = itemInfo.split("\r?\n");
		items = new ArrayList<Item>();
		for (int i = 0; i < allItemInfo.length; i++) {
			final String current = allItemInfo[i];
			final Pattern numerical = Pattern.compile("\\d+"), name = Pattern.compile("\".*\"");
			final Matcher numMatch = numerical.matcher(current), nameMatch = name.matcher(current);
			final List<Integer> numMatches = new ArrayList<Integer>();
			while (numMatch.find())
				numMatches.add(Integer.parseInt(numMatch.group()));
			String itemName = null;
			while (nameMatch.find())
				itemName = nameMatch.group();
			items.add(new Item(numMatches.get(0), itemName.split("\"")[1], numMatches.get(1) != 0));
		}
	}

	public static Item getItem(final int id) {
		return items.stream().filter(i -> i.getId() == id).findFirst().get();
	}

}
