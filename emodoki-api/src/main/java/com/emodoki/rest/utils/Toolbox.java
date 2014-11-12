package com.emodoki.rest.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.emodoki.rest.api.IConstants;
import com.emodoki.rest.beans.Credential;

public class Toolbox {
	public static List<String> aoKey = new ArrayList<String>();
	public static List<Credential> aoUser = new ArrayList<Credential>();
	
	static {
		aoUser.add(new Credential(stringToSHA1("sunnydyal"),stringToSHA1("password")));
		aoUser.add(new Credential(stringToSHA1("guest"),stringToSHA1("password")));
		aoUser.add(new Credential(stringToSHA1("admin"),stringToSHA1("password")));
		aoUser.add(new Credential(stringToSHA1("user"),stringToSHA1("password")));
		
		aoKey.add("683c32dd-b515-4db0-a9bc-45ea1ed85bfb");
		aoKey.add("304c751e-4414-4652-bb88-88e2135681de");
		aoKey.add("8f2f806f-e48e-459e-a8fb-48b84e712e7e");
		aoKey.add("359a7156-3676-479d-90f2-ff8011fda829");
		
	}

	/**
	 * Generate random UUIDs
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * Convert a string based locale into a Locale Object. Assumes the string
	 * has form "{language}_{country}_{variant}". Examples: "en", "de_DE",
	 * "_GB", "en_US_WIN", "de__POSIX", "fr_MAC"
	 * 
	 * @param localeString
	 *            The String
	 * @return the Locale
	 */
	public static Locale getLocaleFromString(String localeString) {
		if (localeString == null) {
			return null;
		}
		localeString = localeString.trim();
		if (localeString.toLowerCase().equals("default")) {
			return Locale.getDefault();
		}

		// Extract language
		int languageIndex = localeString.indexOf('_');
		String language = null;
		if (languageIndex == -1) {
			// No further "_" so is "{language}" only
			return new Locale(localeString, "");
		} else {
			language = localeString.substring(0, languageIndex);
		}

		// Extract country
		int countryIndex = localeString.indexOf('_', languageIndex + 1);
		String country = null;
		if (countryIndex == -1) {
			// No further "_" so is "{language}_{country}"
			country = localeString.substring(languageIndex + 1);
			return new Locale(language, country);
		} else {
			// Assume all remaining is the variant so is
			// "{language}_{country}_{variant}"
			country = localeString.substring(languageIndex + 1, countryIndex);
			String variant = localeString.substring(countryIndex + 1);
			return new Locale(language, country, variant);
		}
	}
	/**
	 * String to SHA1
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String stringToSHA1(String message) {
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		algorithm.update(message.getBytes());

		byte[] hash = algorithm.digest();

		return byteArray2Hex(hash);
	}
	/**
	 * Converts byte[] to hex
	 * @param hash
	 * @return
	 */
	@SuppressWarnings("resource")
	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}
	
	/**
	 * String To Date
	 * @param inputDate
	 * @return
	 */
	public static Date stringToDate(String inputDate) {
		String dateString = inputDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat(IConstants.DATE_FORMAT);
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedDate;
	}
	/**
	 * String to Date with Format
	 * @param inputDate
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String inputDate,String format) {
		String dateString = inputDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedDate;
	}
	
	public static Date stringToDate2(String inputDate) {
		String dateString = inputDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat(IConstants.DATE_FORMAT_DD_MM_YYYY);
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedDate;
	}
}
