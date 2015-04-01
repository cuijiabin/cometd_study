package com.xiaoma.kefu.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.Assert;


/**
 * Utility class to format UUID.
 * 
 * @author ivanchoo
 * 
 */
public class UUIDUtil {
	
	private static final long BASE_TIME = 1262275200000L; // new Date(2010, 0, 1, 0, 0, 0, 0)

	private static final long[] ID32_RESERVED = new long[] {400760440L, 4294967295L};

	private static final long ID32_MAX_INCREMENTAL = 10;

	private static final int ID32_MAX = Integer.MAX_VALUE;

	private static long lastId32Time = 0;

	private static long lastId32Incremental = 0;

	/**
	 * 
	 * @return 
	 */
	public static long id32() {
		long id = 0;
		int attempts = 10000000;

		while (true) {
			try {
				id = calcId32();
				break;
			} catch (Error e) {
				attempts--;
				if (attempts < 0) {
					throw e;
				}
				// Reached max limit, continue in loop into the next second
			}
		}

		if (id >= ID32_MAX) {
			throw new Error("id32 reached max limit");
		}
		return id;
	}

	public static long calcId32() {

		Date now = Calendar.getInstance().getTime();
		long id32Time = (long) (Math.floor((now.getTime() - BASE_TIME) / 1000) * ID32_MAX_INCREMENTAL);

		if (lastId32Time == id32Time) {
			//  last calculation was in the same second, we go into next incremental
			lastId32Incremental++;
		} else {
			lastId32Time = id32Time;
			lastId32Incremental = 0;
		}

		if (lastId32Incremental >= ID32_MAX_INCREMENTAL) {
			//  only allow ID32_MAX_INCREMENTAL, throw error
			//  and let caller retry
			throw new Error("Max limit for id32() at current timestamp");
		}
		long id = lastId32Time + lastId32Incremental;

		for (long id32 : ID32_RESERVED) {
			if (id32 == id) {
				new Error("Reserved ID: " + id32);
			}
		}
		return id;
	}

	/**
	 * 
	 * @return 32 character UUID containing only alpha-numeric characters.
	 */
	public static String randomUUIDString() {
		return parseUUIDString(UUID.randomUUID());
	}

	/**
	 * Extracts and formats a UUID string.
	 * 
	 * <pre>
	 * UUID uuid = UUID.randomUUID();
	 * System.out.print(uuid);
	 * //  ed949888-22d8-488c-a335-1cb808418eb8
	 * System.out.print(UUIDUtil.parseUUIDString(uuid));
	 * //  ED94988822D8488CA3351CB808418EB8
	 * </pre>
	 * 
	 * @param uuid
	 * @return 32 character UUID containing only alpha-numeric characters.
	 */
	public static String parseUUIDString(UUID uuid) {
		Assert.notNull(uuid);
		return uuid.toString().replace("-", "").toUpperCase();
	}

	public static boolean validateUUID(String uuid) {
		boolean b = uuid.matches("[A-F0-9]{32}");
		return b;
	}
}
