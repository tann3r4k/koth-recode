package com.benzimmer123.koth.storage;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.benzimmer123.koth.KOTH;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GsonStorage {

	public static <T> void serialize(T type, String filePath, String setterValue) {
		Gson gson = getGson();
		String jsonString = gson.toJson(type);
		File file = new File(KOTH.getInstance().getDataFolder(), filePath);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
		fileConfig.set(setterValue, jsonString);

		try {
			fileConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> T deserialize(Class<T> type, String filePath, String setterValue) {
		File file = new File(filePath);
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
		String jsonString = fileConfig.getString(setterValue);
		Gson gson = getGson();
		T object = gson.fromJson(jsonString, type);
		return object;
	}
	
	public static Gson getGson() {
		return new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
			@Override
			public void write(JsonWriter out, ZonedDateTime value) throws IOException {
				out.value(value.toString());
			}

			@Override
			public ZonedDateTime read(JsonReader in) throws IOException {
				return ZonedDateTime.parse(in.nextString());
			}
		}).enableComplexMapKeySerialization().setPrettyPrinting().create();
	}

}
