package sinia.com.smartmart.mycallback;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 忧郁的眼神 on 2016/10/28 0028.
 */

public interface ModifyCountAndPriceInterface {

    /**
     * 增加
     *
     * @param position     位置
     * @param imgAdd       增加按钮
     * @param imgSubstract 减少按钮
     * @param tvNum        数量控件
     */
    public void doIncrease(int position, ImageView imgAdd, ImageView imgSubstract, TextView tvNum);

    /**
     * 减少
     *
     * @param position     位置
     * @param imgAdd       增加按钮
     * @param imgSubstract 减少按钮
     * @param tvNum        数量控件
     */
    public void doDecrease(int position, ImageView imgAdd, ImageView imgSubstract, TextView tvNum);
}
