package com.supermax.base.common.widget.viewpager.headerpager.help;

/**
 * @Author yinzh
 * @Date 2019/2/26 17:25
 * @Description
 */
public final class IntegerVariable {
    private int mValue;

    public IntegerVariable(int value) {
        mValue = value;
    }
    public IntegerVariable() {
    }

    public final int getValue() {
        return mValue;
    }

    public final void setValue(int value) {
        mValue = value;
    }

    @Override
    public boolean equals(Object o) {
        // 地址
        if(this == o) {
            return true;
        }

        // 同类比较值
        if(o instanceof IntegerVariable) {
            return mValue == ((IntegerVariable)o).getValue();
        }

        // 异类比较值
        if(o instanceof Integer) {
            return mValue == (Integer) o;
        }

        // 无法处理的，扔给父类
        return super.equals(o);
    }

    @Override
    public String toString() {
        return String.valueOf(mValue);
    }
}
