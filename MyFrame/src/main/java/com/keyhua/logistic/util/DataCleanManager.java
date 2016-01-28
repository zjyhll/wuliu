package com.keyhua.logistic.util;

/*  * 文 件 名:  DataCleanManager.java  
 * * 描    述:  主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录  
 * */

import java.io.File;
import java.math.BigDecimal;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/** * 本应用数据清除管理器 */
public class DataCleanManager {
	/**
	 * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
	 * 
	 * @param context
	 */
	public static boolean cleanInternalCache(Context context) {
		return deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
	 * 
	 * @param context
	 */
	public static boolean cleanDatabases(Context context) {
		return deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}

	/**
	 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
	 * 
	 * @param context
	 */
	public static boolean cleanSharedPreference(Context context) {
		return deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
	}

	/**
	 * * 按名字清除本应用数据库 * *
	 * 
	 * @param context
	 * @param dbName
	 */
	public static boolean cleanDatabaseByName(Context context, String dbName) {
		return context.deleteDatabase(dbName);
	}

	/**
	 * * 清除/data/data/com.xxx.xxx/files下的内容 * *
	 * 
	 * @param context
	 */
	public static boolean cleanFiles(Context context) {
		return deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 * 
	 * @param context
	 */
	public static boolean cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				return deleteFilesByDirectory(context.getExternalCacheDir());
			} catch (Exception e) {
				return deleteFilesByDirectory(context.getCacheDir());
			}
		} else {
			return false;
		}
	}

	/**
	 * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
	 * 
	 * @param filePath
	 */
	public static boolean cleanCustomCache(String filePath) {
		return deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * * 清除本应用所有的数据 * *
	 * 
	 * @param context
	 * @param filepath
	 */
	public static void cleanApplicationData(Context context, String... filepath) {
		// cleanInternalCache(context);
		cleanExternalCache(context);
		// cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		if (filepath == null) {
			return;
		}
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
	 * 
	 * @param directory
	 */
	// private static void deleteFilesByDirectory(File directory) {
	// if (directory != null && directory.exists() && directory.isDirectory()) {
	// for (File item : directory.listFiles()) {
	// item.delete();
	// }
	// }
	// }

	private static boolean deleteFilesByDirectory(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteFilesByDirectory(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	// 获取文件
	// Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
	// 目录，一般放一些长时间保存的数据
	// Context.getExternalCacheDir() -->
	// SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static String getDiskCacheDir(Context context) {
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			try {
				cachePath = context.getExternalCacheDir().getPath();
			} catch (Exception e) {
				cachePath = context.getCacheDir().getPath();
			}
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 如果下面还有文件
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 将SD卡文件删除
	public static void deleteFile(File file) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
				// 如果它是一个目录
				else if (file.isDirectory()) {
					// 声明目录下所有的文件 files[];
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				file.delete();
			}
		}
	}

	/**
	 * 格式化单位
	 * 
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "B";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	public static String getCacheSize(File file) throws Exception {
		return getFormatSize(getFolderSize(file));
	}
}
