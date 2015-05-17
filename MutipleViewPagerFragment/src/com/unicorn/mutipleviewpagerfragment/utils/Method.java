package com.unicorn.mutipleviewpagerfragment.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/**
 * 常用方法工具类
 * 
 * @author hls
 * 
 */
public class Method {

	public static final int XHDPI_WIDTH = 720;
	public static final int HDPI_WIDTH = 480;
	public static final int XXHDPI_WIDTH = 960;
	private static final String TAG = Method.class.getSimpleName();

	private Method() {
		throw new RuntimeException("Method class cannot be Instantiation");
	}

	public static String formatColorString(String source, String color) {
		return "<font color='" + color + "'>" + source + "</font>";
	}

	public static String formatColorString(String source, String color, Integer size) {
		return "<font color='" + color + "' size='" + size + "'>" + source + "</font>";
	}

	public static Spanned formatHtml(String source) {
		return Html.fromHtml(source);
	}

	public static Spanned formatHtml(String source, String color) {
		return Html.fromHtml(formatColorString(source, color));
	}

	public static String getXmlNode(String src, String node) {
		String open = "<" + node + ">";
		String close = "</" + node + ">";
		int index = src.indexOf(open);
		if (index < 0)
			return null;
		int end = src.indexOf(close);
		if (end < 0)
			return null;
		return src.substring(index + open.length(), end);
	}

	public static String replaceSpace(String str) {
		return str.replaceAll("\\s", "");
	}

	public static byte[] readByte(String fileName) {
		byte[] buffer = null;
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(fileName);
			int length = fin.available();
			buffer = new byte[length];
			fin.read(buffer);
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		return buffer;
		// res = EncodingUtils.getString(buffer, "UTF-8");
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(File file) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String data;
		try {
			br = new BufferedReader(new FileReader(file));
			// 一次读入一行，直到读入null为文件结束
			data = br.readLine();
			while (data != null) {
				sb.append(data);
				data = br.readLine(); // 接着读下一行
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * 根据传入的身份证号返回真假
	 * */
	public static boolean veryfySfzCode(String id) {
		int[] ary = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		char[] ch = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
		int sum = 0;
		char data;
		switch (id.length()) {
		case 17:
			char[] ary1 = id.toCharArray();
			for (int i = 0; i < ary1.length; i++) {
				sum += (ary1[i] - '0') * ary[i];
			}
			data = ch[sum % 11];
			// String s1 = "您是输入的是17位的身份证号码，合法的身份证号码为";
			return false;

		case 18:
			char[] ary2 = id.toCharArray();
			for (int i = 0; i < ary2.length - 1; i++) {
				sum += (ary2[i] - '0') * ary[i];
			}
			data = ch[sum % 11];
			char lastNum = id.charAt(17);
			lastNum = lastNum == 'x' ? 'X' : lastNum;
			if (data == lastNum) {
				return true;
			}
			// String s2 = "您输入的身份证号码是非法的，合法的为";
			char[] ary3 = new char[17];
			for (int i = 0; i < id.length() - 1; i++) {
				ary3[i] = ary2[i];
			}
			return false;

		default:
			return false;
		}
	}

	/**
	 * 如果格式正确，返回True
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		// ^[0-9\\-._a-zA-Z]{1,}@[0-9\\-._a-zA-Z]{1,}\\.[0-9\\-._a-zA-Z]{1,}$
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(replaceSpace(email));
		return m.matches();
	}

	// 判断手机格式是否正确
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(\\+86)*(86)*1\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断是否为邮编
	public static boolean isPostCode(String postcode) {
		if (postcode.length() != 6)
			return false;
		else
			return true;
	}

	/**
	 * 把图片修成正方形
	 * 
	 * @hls 2014-1-23
	 * */
	public static Bitmap cutBmp(Bitmap bmp) {
		if (bmp == null)
			return null;
		Bitmap result;
		int w = bmp.getWidth();// 输入长方形宽
		int h = bmp.getHeight();// 输入长方形高
		int nw;// 输出正方形宽
		if (w > h) {
			// 宽大于高
			nw = h;
			result = Bitmap.createBitmap(bmp, (w - nw) / 2, 0, nw, nw);

		} else {
			// 高大于宽
			nw = w;
			result = Bitmap.createBitmap(bmp, 0, (h - nw) / 2, nw, nw);
		}
		if (!bmp.isMutable() && !bmp.isRecycled()) {
			bmp.recycle();
			bmp = null;
		}

		return result;
	}

	/**
	 * 将 Dp值转换为pixel值
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static float dip2pix(Resources res, float dipValue) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
	}

	/**
	 * 删除一个标准文件或空文件夹
	 * 
	 * @param file
	 *            标准文件或空文件夹
	 * @throws IOException
	 *             删除失败
	 */
	private static void deleteFile(File file) throws IOException {
		if (file.canWrite()) {
			if (!file.delete()) {
				throw new IOException("failed to delete file : " + file.getAbsolutePath());
			}
		} else {
			throw new IOException("can't delete non file or file is can't write : "
					+ file.getAbsolutePath());
		}
	}

	/**
	 * 删除一个文件夹内所有文件
	 * 
	 * @param dir
	 *            文件夹，该文件夹本身不会被删除
	 * @throws IOException
	 *             删除失败
	 */
	public static void deleteFiles(File dir) throws IOException {
		if (!dir.isDirectory())
			throw new IOException("can't delete files under non-directory file:"
					+ dir.getAbsolutePath());
		File[] files = dir.listFiles();
		if (files != null) {
			for (File subfile : files) {
				if (subfile.isDirectory()) {
					deleteFiles(subfile);
				}
				deleteFile(subfile);
			}
		}
	}

	/**
	 * 统计一个目录内所占的存储空 jain大小
	 * 
	 * @param dir
	 *            目录
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
			}
		}
		return dirSize;
	}



	/**
	 * 安装apk文件
	 * 
	 * @param context
	 * @param apkFile
	 */
	public static void installApk(Context context, File apkFile) {
		if (!apkFile.exists()) {
			Log.e(TAG, new StringBuffer(apkFile.getAbsolutePath()).append("not exist~!").toString());
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(i);

	}


	

	/**
	 *  * Try to return the absolute file path from the given Uri  *  *
	 * 
	 * @param context
	 * @param uri
	 * @return the file path or null  
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 判断Fragment是否处于附属状态
	 * 
	 * @param fragment
	 * @return
	 */
	public static boolean isAttach(Fragment fragment) {
		if (fragment == null || fragment.getActivity() == null
				|| fragment.getActivity().isFinishing() || fragment.isDetached())
			return false;
		else
			return true;
	}

	/**
	 * 判断Fragment是否处于脱离状态
	 * 
	 * @param fragment
	 * @return
	 * @see {@link #isAttatch}
	 */
	public static boolean isDetached(Fragment fragment) {
		return !isAttach(fragment);
	}

	/**
	 * 判断Activity是否存活
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isAlive(Activity activity) {
		if (activity == null || activity.isFinishing())
			return false;
		else
			return true;
	}

	/**
	 * 判断Activity是否死亡
	 * @param activity
	 * @return
	 * @see {@link #isAlive}
	 */
	public static boolean isDead(Activity activity) {
		return !isAlive(activity);
	}
}
