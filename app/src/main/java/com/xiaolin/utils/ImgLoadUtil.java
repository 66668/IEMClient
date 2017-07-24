package com.xiaolin.utils;

/**
 * databinding 高级用法 @BindingAdapter,自定义xml属性
 * .
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }

    /**
     * 会议列表图片
     * <p>
     * 该方式获取的图片按原大小展示，如果后台图片不统一，最好不要用
     * .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
     * <p>
     * //设置成统一的样式，即使后台图片不统一，也可以在展示的时候界面统一,但是这里使用，没有效果
     * .override((int) CommonUtils.getDimens(R.dimen.conference_detail_height)//conference_detail_width
     * , (int) CommonUtils.getDimens(R.dimen.conference_detail_height))
     */
//    @BindingAdapter("android:showConferenceImg")
//    public static void showConferenceImg(ImageView imageView, String url) {
//
//        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, imageView.getContext().getResources().getDisplayMetrics());
//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180f, imageView.getContext().getResources().getDisplayMetrics());
//
//        Glide.with(imageView.getContext())
//                .load(url)//url
//                .override(width, height)
//                .crossFade(500)//动画效果
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(imageView);
//    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    //    public static void displayEspImage(String url, ImageView imageView, int type) {
    //        Glide.with(imageView.getContext())
    //                .load(url)
    //                .crossFade(500)
    //                .placeholder(getDefaultPic(type))
    //                .error(getDefaultPic(type))
    //                .into(imageView);
    //    }

    //图片下载失败显示默认的图片
    //    private static int getDefaultPic(int type) {
    //        switch (type) {
    //            case 0:// 电影
    //                return R.mipmap.img_default_movie;
    //            case 1:// 妹子
    //                return R.mipmap.img_default_meizi;
    //            case 2:// 书籍
    //                return R.mipmap.img_default_book;
    //        }
    //        return R.mipmap.img_default_movie;
    //    }

    /**
     * 显示高斯模糊效果（电影详情页）
     */
    //    private static void displayGaussian(Context context, String url, ImageView imageView) {
    //        // "23":模糊度；"4":图片缩放4倍后再进行模糊
    //        Glide.with(context)
    //                .load(url)
    //                .error(R.mipmap.stackblur_default)
    //                .placeholder(R.mipmap.stackblur_default)
    //                .crossFade(500)
    //                .bitmapTransform(new BlurTransformation(context, Glide.get(context).getBitmapPool(), 4))
    //                .into(imageView);
    //    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    //    public static void displayCircle(ImageView imageView, String imageUrl) {
    //        Glide.with(imageView.getContext())
    //                .load(imageUrl)
    //                .crossFade(500)
    //                .error(R.mipmap.icon_photo)
    //                .transform(new GlideCircleTransform(imageView.getContext()))
    //                .into(imageView);
    //    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    //    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    //    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
    //        displayEspImage(url, imageView, defaultPicType);
    //    }

    /**
     * 电影详情页显示电影图片(等待被替换)（测试的还在，已可以弃用）
     * 没有加载中的图
     */
    //    @BindingAdapter("android:showImg")
    //    public static void showImg(ImageView imageView, String url) {
    //        Glide.with(imageView.getContext())
    //                .load(url)
    //                .crossFade(500)
    //                .error(getDefaultPic(0))
    //                .into(imageView);
    //    }

    /**
     * 电影列表图片
     */
    //    @BindingAdapter("android:showMovieImg")
    //    public static void showMovieImg(ImageView imageView, String url) {
    //        Glide.with(imageView.getContext())
    //                .load(url)
    //                .crossFade(500)
    //                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
    //                .placeholder(getDefaultPic(0))
    //                .error(getDefaultPic(0))
    //                .into(imageView);
    //    }

    /**
     * 书籍列表图片
     */
    //    @BindingAdapter("android:showBookImg")
    //    public static void showBookImg(ImageView imageView, String url) {
    //        Glide.with(imageView.getContext())
    //                .load(url)
    //                .crossFade(500)
    //                .override((int) CommonUtils.getDimens(R.dimen.book_detail_width), (int) CommonUtils.getDimens(R.dimen.book_detail_height))
    //                .placeholder(getDefaultPic(2))
    //                .error(getDefaultPic(2))
    //                .into(imageView);
    //    }

    /**
     * 电影详情页显示高斯背景图
     */
    //    @BindingAdapter("android:showImgBg")
    //    public static void showImgBg(ImageView imageView, String url) {
    //        displayGaussian(imageView.getContext(), url, imageView);
    //    }


}
