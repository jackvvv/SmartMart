package sinia.com.smartmart.view.swipmenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author baoyz
 * @date 2014-8-18
 */
public class SwipeMenuListView extends ListView {

    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
    private int mDirection = 1;//swipe from right to left by default

    private int MAX_Y = 5;
    private int MAX_X = 3;
    private float mDownX;
    private float mDownY;
    private int mTouchState;
    private int mTouchPosition;
    private SwipeMenuLayout mTouchView;
    private OnSwipeListener mOnSwipeListener;

    private SwipeMenuCreator mMenuCreator;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private OnMenuStateChangeListener mOnMenuStateChangeListener;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;

    public SwipeMenuListView(Context context) {
        super(context);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        MAX_X = dp2px(MAX_X);
        MAX_Y = dp2px(MAX_Y);
        mTouchState = TOUCH_STATE_NONE;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(new SwipeMenuAdapter(getContext(), adapter) {
            @Override
            public void createMenu(SwipeMenu menu) {
                if (mMenuCreator != null) {
                    mMenuCreator.create(menu);
                }
            }

            @Override
            public void onItemClick(SwipeMenuView view, SwipeMenu menu,
                                    int index) {
                boolean flag = false;
                if (mOnMenuItemClickListener != null) {
                    flag = mOnMenuItemClickListener.onMenuItemClick(
                            view.getPosition(), menu, index);
                }
                if (mTouchView != null && !flag) {
                    mTouchView.smoothCloseMenu();
                }
            }
        });
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return mCloseInterpolator;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //鍦ㄦ嫤鎴澶勭悊锛屽湪婊戝姩璁剧疆浜嗙偣鍑讳簨浠剁殑鍦版柟涔熻兘swip锛岀偣鍑绘椂鍙堜笉鑳藉奖鍝嶅師鏉ョ殑鐐瑰嚮浜嬩欢
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                boolean handled = super.onInterceptTouchEvent(ev);
                mTouchState = TOUCH_STATE_NONE;
                mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                View view = getChildAt(mTouchPosition - getFirstVisiblePosition());

                //鍙湪绌虹殑鏃跺�欒祴鍊� 浠ュ厤姣忔瑙︽懜閮借祴鍊硷紝浼氭湁澶氫釜open鐘舵��
                if (view instanceof SwipeMenuLayout) {
                    //濡傛灉鏈夋墦寮�浜� 灏辨嫤鎴�.
                    if (mTouchView != null && mTouchView.isOpen() && !inRangeOfView(mTouchView.getMenuView(), ev)) {
                        return true;
                    }
                    mTouchView = (SwipeMenuLayout) view;
                    mTouchView.setSwipeDirection(mDirection);
                }
                //濡傛灉鎽稿湪鍙﹀涓獀iew
                if (mTouchView != null && mTouchView.isOpen() && view != mTouchView) {
                    handled = true;
                }

                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }
                return handled;
            case MotionEvent.ACTION_MOVE:
                float dy = Math.abs((ev.getY() - mDownY));
                float dx = Math.abs((ev.getX() - mDownX));
                if (Math.abs(dy) > MAX_Y || Math.abs(dx) > MAX_X) {
                    //姣忔鎷︽埅鐨刣own閮芥妸瑙︽懜鐘舵�佽缃垚浜員OUCH_STATE_NONE 鍙湁杩斿洖true鎵嶄細璧皁nTouchEvent 鎵�浠ュ啓鍦ㄨ繖閲屽氨澶熶簡
                    if (mTouchState == TOUCH_STATE_NONE) {
                        if (Math.abs(dy) > MAX_Y) {
                            mTouchState = TOUCH_STATE_Y;
                        } else if (dx > MAX_X) {
                            mTouchState = TOUCH_STATE_X;
                            if (mOnSwipeListener != null) {
                                mOnSwipeListener.onSwipeStart(mTouchPosition);
                            }
                        }
                    }
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() != MotionEvent.ACTION_DOWN && mTouchView == null)
            return super.onTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                int oldPos = mTouchPosition;
                mDownX = ev.getX();
                mDownY = ev.getY();
                mTouchState = TOUCH_STATE_NONE;

                mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());

                if (mTouchPosition == oldPos && mTouchView != null
                        && mTouchView.isOpen()) {
                    mTouchState = TOUCH_STATE_X;
                    mTouchView.onSwipe(ev);
                    return true;
                }

                View view = getChildAt(mTouchPosition - getFirstVisiblePosition());

                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                    mTouchView = null;
                    // return super.onTouchEvent(ev);
                    // try to cancel the touch event
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    onTouchEvent(cancelEvent);
                    if (mOnMenuStateChangeListener != null) {
                        mOnMenuStateChangeListener.onMenuClose(oldPos);
                    }
                    return true;
                }
                if (view instanceof SwipeMenuLayout) {
                    mTouchView = (SwipeMenuLayout) view;
                    mTouchView.setSwipeDirection(mDirection);
                }
                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //鏈変簺鍙兘鏈塰eader,瑕佸噺鍘籬eader鍐嶅垽鏂�
                mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY()) - getHeaderViewsCount();
                //濡傛灉婊戝姩浜嗕竴涓嬫病瀹屽叏灞曠幇锛屽氨鏀跺洖鍘伙紝杩欐椂鍊檓TouchView宸茬粡璧嬪�硷紝鍐嶆粦鍔ㄥ彟澶栦竴涓笉鍙互swip鐨剉iew
                //浼氬鑷磎TouchView swip 銆� 鎵�浠ヨ鐢ㄤ綅缃垽鏂槸鍚︽粦鍔ㄧ殑鏄竴涓獀iew
                if (!mTouchView.getSwipEnable() || mTouchPosition != mTouchView.getPosition()) {
                    break;
                }
                float dy = Math.abs((ev.getY() - mDownY));
                float dx = Math.abs((ev.getX() - mDownX));
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(ev);
                    }
                    getSelector().setState(new int[]{0});
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                } else if (mTouchState == TOUCH_STATE_NONE) {
                    if (Math.abs(dy) > MAX_Y) {
                        mTouchState = TOUCH_STATE_Y;
                    } else if (dx > MAX_X) {
                        mTouchState = TOUCH_STATE_X;
                        if (mOnSwipeListener != null) {
                            mOnSwipeListener.onSwipeStart(mTouchPosition);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        boolean isBeforeOpen = mTouchView.isOpen();
                        mTouchView.onSwipe(ev);
                        boolean isAfterOpen = mTouchView.isOpen();
                        if (isBeforeOpen != isAfterOpen && mOnMenuStateChangeListener != null) {
                            if (isAfterOpen) {
                                mOnMenuStateChangeListener.onMenuOpen(mTouchPosition);
                            } else {
                                mOnMenuStateChangeListener.onMenuClose(mTouchPosition);
                            }
                        }
                        if (!isAfterOpen) {
                            mTouchPosition = -1;
                            mTouchView = null;
                        }
                    }
                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeEnd(mTouchPosition);
                    }
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    	// TODO Auto-generated method stub
    	super.onScrollChanged(l, t, oldl, oldt);
    	smoothCloseMenu();
    }
    public void smoothOpenMenu(int position) {
        if (position >= getFirstVisiblePosition()
                && position <= getLastVisiblePosition()) {
            View view = getChildAt(position - getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                mTouchPosition = position;
                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                }
                mTouchView = (SwipeMenuLayout) view;
                mTouchView.setSwipeDirection(mDirection);
                mTouchView.smoothOpenMenu();
            }
        }
    }

    public void smoothCloseMenu(){
        if (mTouchView != null && mTouchView.isOpen()) {
            mTouchView.smoothCloseMenu();
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }

    public void setOnMenuStateChangeListener(OnMenuStateChangeListener onMenuStateChangeListener) {
        mOnMenuStateChangeListener = onMenuStateChangeListener;
    }

    public static interface OnMenuItemClickListener {
        boolean onMenuItemClick(int position, SwipeMenu menu, int index);
    }

    public static interface OnSwipeListener {
        void onSwipeStart(int position);

        void onSwipeEnd(int position);
    }

    public static interface OnMenuStateChangeListener {
        void onMenuOpen(int position);

        void onMenuClose(int position);
    }

    public void setSwipeDirection(int direction) {
        mDirection = direction;
    }

    /**
     * 鍒ゆ柇鐐瑰嚮浜嬩欢鏄惁鍦ㄦ煇涓獀iew鍐�
     *
     * @param view
     * @param ev
     * @return
     */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
}
