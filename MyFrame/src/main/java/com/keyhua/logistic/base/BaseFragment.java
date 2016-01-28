package com.keyhua.logistic.base;

import java.util.ArrayList;
import java.util.List;

import com.example.importotherlib.R;
import com.keyhua.logistic.alertview.MainActivity;
import com.keyhua.logistic.util.MyLogger;
import com.keyhua.logistic.view.CustomProgressDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

/**
 * @author 曾金叶
 * @2015-8-5 @下午2:34:14
 * @category 跟BaseActivity相同功能
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected ImageLoader imageLoader = null;
	protected ImageLoader imageLoaderRectF = null;
	protected BackHandledInterface mBackHandledInterface;
	protected com.nostra13.universalimageloader.core.ImageLoader mImageLoader = com.nostra13.universalimageloader.core.ImageLoader
			.getInstance();
	protected DisplayImageOptions options;
	protected DisplayImageOptions optionsrenwu;
	// log
	public MyLogger loghxd = MyLogger.hxdLog();
	public MyLogger logzt = MyLogger.ztLog();
	public MyLogger logzjy = MyLogger.zjyLog();
	protected RadioButton radiobutton_select_huodong = null;// 活动

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageLoader = ImageLoaderFactory.create(getActivity());
		imageLoaderRectF = ImageLoaderFactory.create(getActivity());
		DefaultImageLoadHandler handler = new DefaultImageLoadHandler(getActivity());
		// pick one of the following method
		// handler.setLoadingBitmap(Bitmap loadingBitmap);
		// handler.setLoadingResources(int loadingBitmap);
		// handler.setLoadingImageColor(int color);
		// handler.setLoadingImageColor(String colorString);
		handler.setLoadingImageColor(getResources().getColor(R.color.white));
		handler.setErrorResources(R.mipmap.dmbg_default);
		imageLoader.setImageLoadHandler(handler);

		DefaultImageLoadHandler handler1 = new DefaultImageLoadHandler(getActivity());
		// pick one of the following method
		// handler.setLoadingBitmap(Bitmap loadingBitmap);
		// handler.setLoadingResources(int loadingBitmap);
		// handler.setLoadingImageColor(int color);
		// handler.setLoadingImageColor(String colorString);
		handler1.setLoadingImageColor(getResources().getColor(R.color.white));
		handler1.setErrorResources(R.mipmap.dmbg_default);
		handler1.setImageRounded(true, 8);
		imageLoaderRectF.setImageLoadHandler(handler1);
		options = new DisplayImageOptions.Builder()
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.mipmap.dmbg_default)
				// 设置图片URL为空或是错误的时候显示的图片
				.showImageOnFail(R.mipmap.dmbg_default)
				// 设置图片加载或解码过程中发生错误显示的图片
				.displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.displayer(new FadeInBitmapDisplayer(100)).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象
		optionsrenwu = new DisplayImageOptions.Builder()
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.mipmap.dmbg_default)
				// 设置图片URL为空或是错误的时候显示的图片
				.showImageOnFail(R.mipmap.dmbg_default)
				// 设置图片加载或解码过程中发生错误显示的图片
				.displayer(new RoundedBitmapDisplayer(8)) // 设置成圆角图片
				.displayer(new FadeInBitmapDisplayer(100)).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象
		init();
	}

	/**
	 * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
	 * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
	 * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
	 */
	public boolean onBackPressed() {
		return false;
	}

	/**
	 * 获取版本名称
	 * 
	 */
	public String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = String.valueOf(info.versionName);
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0";
		}
	}

	/**
	 * 获取版本名称code
	 * 
	 */
	public String getVersioncode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = String.valueOf(info.versionCode);
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 判断某个界面是否在前台
	 * 
	 * @param context
	 * @param className
	 *            某个界面名称
	 */
	private boolean isForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!(getActivity() instanceof BackHandledInterface)) {
			throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
		} else {
			this.mBackHandledInterface = (BackHandledInterface) getActivity();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// 告诉FragmentActivity，当前Fragment在栈顶
//		mBackHandledInterface.setSelectedFragment(this);
	}

	// 左拉菜单改变主界面tap点击效果
	public void changeTap() {
//		radiobutton_select_huodong = (RadioButton) getActivity().findViewById(R.id.radiobutton_select_huodong);
	}

	public void init() {
		initMainHeader();
		onInitData();
		onResload();
		setMyViewClick();
		headerOrFooterViewControl();
	}

	// 判断是否有空格
	protected static boolean isTextReplaceAll(String text) {
		if (TextUtils.isEmpty(text.replaceAll("\\s*", ""))) {
			return true;
		} else {
			return false;
		}
	}

	/** * 检测Android设备是否支持摄像机 */
	protected static boolean checkCameraDevice(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return container;
	}

	protected void openActivity(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		startActivity(intent);
	}

	protected void openActivity(Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		intent.putExtras(extras);
		startActivity(intent);
	}

	/*** [初始化arraylist] ***********************************************/
	public static <T> ArrayList<T> createArrayList(T... elements) {
		ArrayList<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	/*** [重写的toast] ***********************************************/
	protected Toast toast;

	protected void showToast(String text) {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText(text);
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	protected void showToastLogin() {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText("登录失效，请重新登录");
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	protected void showToastDengLu() {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText("请先登录");
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	protected void showToastNet() {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText("没有网络连接，请检查网络");
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	/** 当构造Fragment时将来调用, 主要用做数据初始化 , 在onResload之前调用 */
	protected abstract void onInitData();

	/** 每当Fragment创建出来时,将先调用, 主要用做资源的加载 */
	protected abstract void onResload();

	/** 每当Fragment创建出来时,主要是针对控件的点击事件 */
	protected abstract void setMyViewClick();

	/** 每当Fragment创建出来时,针对控件的显示与隐藏 */
	protected abstract void headerOrFooterViewControl();

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 主界面关联的Frg都放在这里面,主要用来显示与隐藏
	 */

	protected void initMainHeader() {
	}

	/** Fragment中底部的按钮 */

	protected void initMainFooter(String str1, String str2, String str3) {
	}


	// pop TODO

	protected void initPopwindow() {// TODO
	}

	/**
	 * 程序退出dialog id
	 */
	protected static final int DIALOG_ID_FINISH = 0;
	/**
	 * 等待dialog id
	 */
	protected static final int DIALOG_ID_PROGRESS = 1;
	protected Dialog dialog = null;

	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DIALOG_ID_FINISH:
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			dialog = builder.setTitle("退出").setMessage("是否要退出？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// SPUtils.clear(BaseActivity.this);
							System.exit(0);
							getActivity().finish();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).create();
			break;
		case DIALOG_ID_PROGRESS:
			dialog = new ProgressDialog(getActivity());
			((ProgressDialog) dialog).setMessage("数据加载中，请等待...");
			break;
		}
		return dialog;
	}

	private CustomProgressDialog mydialog = null;

	/**
	 * 显示美团进度对话框
	 * 
	 * @param
	 */
	protected void showdialog() {
		try {
			mydialog = new CustomProgressDialog(getActivity(), "正在加载中...", R.anim.frame);
			mydialog.show();
		} catch (Exception e) {
		}
	}

	protected void showdialogtext(String text) {
		try {
			mydialog = new CustomProgressDialog(getActivity(), text, R.anim.frame);
			mydialog.show();
		} catch (Exception e) {
		}
	}

	protected void closedialog() {
		try {
			if (mydialog != null && getActivity() != null && mydialog.isShowing()) {
				mydialog.dismiss();
			}
		} catch (Exception e) {
		}
	}

	protected boolean isshowdialog() {
		if (mydialog != null && getActivity() != null) {
			if (mydialog.isShowing()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	protected CustomProgressDialog getCustomProgressDialog() {
		try {
			if (mydialog != null) {
				return mydialog;
			}
		} catch (Exception e) {
		}
		return null;
	}
}
