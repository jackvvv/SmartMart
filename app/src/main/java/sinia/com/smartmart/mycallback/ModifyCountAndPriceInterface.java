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
     * @param adapterType  adapter类型 1.菜单，2.购物车
     * @param position     位置
     * @param imgAdd       增加按钮
     * @param imgSubstract 减少按钮
     * @param tvNum        数量控件
     * @param tvPrice      价格
     */
    public void doIncrease(int adapterType, int position, ImageView imgAdd, ImageView imgSubstract, TextView tvNum,
                           TextView tvPrice);

    /**
     * 减少
     *
     * @param adapterType  adapter类型 1.菜单，2.购物车
     * @param position     位置
     * @param imgAdd       增加按钮
     * @param imgSubstract 减少按钮
     * @param tvNum        数量控件
     * @param tvPrice      价格
     */
    public void doDecrease(int adapterType, int position, ImageView imgAdd, ImageView imgSubstract, TextView tvNum,
                           TextView tvPrice);
}
