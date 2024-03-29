package com.ysxsoft.home.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.ysxsoft.home.R;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import cn.bingoogolapple.photopicker.widget.BGAHeightWrapGridView;
import cn.bingoogolapple.photopicker.widget.BGAImageView;

/**
 * create by Sincerly on 2019/6/3 0003
 **/
public class NineImageView extends FrameLayout implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int ITEM_NUM_COLUMNS = 3;
    private PhotoAdapter mPhotoAdapter;
    private BGAImageView mPhotoIv;
    private BGAHeightWrapGridView mPhotoGv;
    private Delegate mDelegate;
    private int mCurrentClickItemPosition;

    private int mItemCornerRadius;
    private boolean mShowAsLargeWhenOnlyOne;
    private int mItemWhiteSpacing;
    private int mOtherWhiteSpacing;
    private int mPlaceholderDrawableResId;
    private int mItemSpanCount;

    private int mItemWidth;

    public NineImageView(Context context) {
        this(context, null);
    }

    public NineImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultAttrs();
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
    }

    private void initDefaultAttrs() {
        mItemWidth = 0;
        mShowAsLargeWhenOnlyOne = true;
        mItemCornerRadius = 0;
        mItemWhiteSpacing = BGABaseAdapterUtil.dp2px(4);
        mPlaceholderDrawableResId = R.mipmap.bga_pp_ic_holder_light;
        mOtherWhiteSpacing = BGABaseAdapterUtil.dp2px(100);
        mItemSpanCount = 3;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineImageView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.NineImageView_bga_npl_showAsLargeWhenOnlyOne) {
            mShowAsLargeWhenOnlyOne = typedArray.getBoolean(attr, mShowAsLargeWhenOnlyOne);
        } else if (attr == R.styleable.NineImageView_bga_npl_itemCornerRadius) {
            mItemCornerRadius = typedArray.getDimensionPixelSize(attr, mItemCornerRadius);
        } else if (attr == R.styleable.NineImageView_bga_npl_itemWhiteSpacing) {
            mItemWhiteSpacing = typedArray.getDimensionPixelSize(attr, mItemWhiteSpacing);
        } else if (attr == R.styleable.NineImageView_bga_npl_otherWhiteSpacing) {
            mOtherWhiteSpacing = typedArray.getDimensionPixelOffset(attr, mOtherWhiteSpacing);
        } else if (attr == R.styleable.NineImageView_bga_npl_placeholderDrawable) {
            mPlaceholderDrawableResId = typedArray.getResourceId(attr, mPlaceholderDrawableResId);
        } else if (attr == R.styleable.NineImageView_bga_npl_itemWidth) {
            mItemWidth = typedArray.getDimensionPixelSize(attr, mItemWidth);
        } else if (attr == R.styleable.NineImageView_bga_npl_itemSpanCount) {
            mItemSpanCount = typedArray.getInteger(attr, mItemSpanCount);
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        if (mItemWidth == 0) {
            mItemWidth = (BGAPhotoPickerUtil.getScreenWidth() - mOtherWhiteSpacing - (mItemSpanCount - 1) * mItemWhiteSpacing) / mItemSpanCount;
        }

        mPhotoIv = new BGAImageView(getContext());
        mPhotoIv.setClickable(true);
        mPhotoIv.setOnClickListener(this);

        mPhotoGv = new BGAHeightWrapGridView(getContext());
        mPhotoGv.setHorizontalSpacing(mItemWhiteSpacing);
        mPhotoGv.setVerticalSpacing(mItemWhiteSpacing);
        mPhotoGv.setNumColumns(ITEM_NUM_COLUMNS);

        mPhotoGv.setOnItemClickListener(this);
        mPhotoAdapter = new PhotoAdapter(getContext());

        mPhotoGv.setAdapter(mPhotoAdapter);
        addView(mPhotoIv, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mPhotoGv);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mCurrentClickItemPosition = position;
        if (mDelegate != null) {
            mDelegate.onClickNinePhotoItem(this, view, mCurrentClickItemPosition, mPhotoAdapter.getItem(mCurrentClickItemPosition), mPhotoAdapter.getData());
        }
    }

    @Override
    public void onClick(View view) {
        mCurrentClickItemPosition = 0;
        if (mDelegate != null) {
            mDelegate.onClickNinePhotoItem(this, view, mCurrentClickItemPosition, mPhotoAdapter.getItem(mCurrentClickItemPosition), mPhotoAdapter.getData());
        }
    }

    /**
     * 设置图片路径数据集合
     *
     * @param photos
     */
    public void setData(ArrayList<String> photos) {
        if (photos.size() == 0) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);

            if (photos.size() == 1 && mShowAsLargeWhenOnlyOne) {
                mPhotoGv.setVisibility(GONE);
                mPhotoAdapter.setData(photos);
                mPhotoIv.setVisibility(VISIBLE);

                int size = mItemWidth * 2 + mItemWhiteSpacing + mItemWidth / 4;
                mPhotoIv.setMaxWidth(size);
                mPhotoIv.setMaxHeight(size);

                if (mItemCornerRadius > 0) {
                    mPhotoIv.setCornerRadius(mItemCornerRadius);
                }

                BGAImage.display(mPhotoIv, mPlaceholderDrawableResId, photos.get(0), size);
            } else {
                mPhotoIv.setVisibility(GONE);
                mPhotoGv.setVisibility(VISIBLE);
                ViewGroup.LayoutParams layoutParams = mPhotoGv.getLayoutParams();

                if (mItemSpanCount > 3) {
                    int itemSpanCount = photos.size() < mItemSpanCount ? photos.size() : mItemSpanCount;
                    mPhotoGv.setNumColumns(itemSpanCount);
                    //layoutParams.width = mItemWidth * itemSpanCount + (itemSpanCount - 1) * mItemWhiteSpacing;
                } else {
                    if (photos.size() == 1) {
                        mPhotoGv.setNumColumns(1);
                        //layoutParams.width = mItemWidth * 1;
                    } else if (photos.size() == 2) {
                        mPhotoGv.setNumColumns(2);
                        //layoutParams.width = mItemWidth * 2 + mItemWhiteSpacing;
                    } else if (photos.size() == 4) {
                        mPhotoGv.setNumColumns(2);
                        //layoutParams.width = mItemWidth * 2 + mItemWhiteSpacing;
                    } else {
                        mPhotoGv.setNumColumns(3);
                        //layoutParams.width = mItemWidth * 3 + 2 * mItemWhiteSpacing;
                    }
                }

                mPhotoGv.setLayoutParams(layoutParams);
                mPhotoAdapter.setData(photos);
            }
        }
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    public ArrayList<String> getData() {
        return (ArrayList<String>) mPhotoAdapter.getData();
    }

    public int getItemCount() {
        return mPhotoAdapter.getCount();
    }

    public String getCurrentClickItem() {
        return mPhotoAdapter.getItem(mCurrentClickItemPosition);
    }

    public int getCurrentClickItemPosition() {
        return mCurrentClickItemPosition;
    }

    private class PhotoAdapter extends BGAAdapterViewAdapter<String> {
        private int mImageSize;

        public PhotoAdapter(Context context) {
            super(context, R.layout.bga_pp_item_nine_photo);
            int size = mItemSpanCount;
            switch (mItemSpanCount) {
                case 1:
                    size=1;
                    break;
                case 2:
                    size=2;
                    break;
                case 3:
                    size=3;
                    break;
                case 4:
                    size=2;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    size=3;
                    break;
            }
            mImageSize = BGAPhotoPickerUtil.getScreenWidth() /size;
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, String model) {
            if (mItemCornerRadius > 0) {
                BGAImageView imageView = helper.getView(R.id.iv_item_nine_photo_photo);
                imageView.setCornerRadius(mItemCornerRadius);
            }

            BGAImage.display(helper.getImageView(R.id.iv_item_nine_photo_photo), mPlaceholderDrawableResId, model, mImageSize);
        }
    }

    public interface Delegate {
        void onClickNinePhotoItem(NineImageView ninePhotoLayout, View view, int position, String model, List<String> models);
    }
}
