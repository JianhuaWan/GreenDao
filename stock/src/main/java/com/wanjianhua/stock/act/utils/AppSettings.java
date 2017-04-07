package com.wanjianhua.stock.act.utils;

import android.content.SharedPreferences;

/**
 * 设置定义，提供统一的设置数据保存和获取方式。
 *
 * @author wanjianhua
 */
public abstract class AppSettings {

    public IntPreference FRAMEWORK_VERSION = new IntPreference(
            "framework_version", 0);
    public StringPreference FRAMEWORK_PACKAGE_DATA = new StringPreference(
            "framework_package_data",
            "{'tabbar':{'height':50,'focus':1,'display':1,'bg_color':'#F3F3F3','bg_image':'','is_frame':1,'frame_color':'#D1D1D1','items':[{'index':1,'uri':'mod://mr_main_findwork','text':'找工作','size':10,'type':1,'is_enable':1,'status':[{'state':1,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_home_page_selicon@2x.png','text_color':'#0075DFFF'},{'state':2,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_home_page_selicon@2x.png','text_color':'#0075DFFF'},{'state':0,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_home_page_icon@2x.png','text_color':'#6A6A6AFF'}]},{'index':2,'uri':'mod://mr_main_message','text':'消息','size':10,'type':1,'is_enable':1,'status':[{'state':1,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_dynamic_selicon@2x.png','text_color':'#0075DFFF'},{'state':2,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_dynamic_selicon@2x.png','text_color':'#0075DFFF'},{'state':0,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_dynamic_icon@2x.png','text_color':'#6A6A6AFF'}]},{'index':3,'uri':'mod://mr_main_me','text':'我','size':10,'type':1,'is_enable':1,'status':[{'state':1,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_me_selicon@2x.png','text_color':'#0075DFFF'},{'state':2,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_me_selicon@2x.png','text_color':'#0075DFFF'},{'state':0,'icon':'http://192.168.1.8:81/resource/system/ios/bottom_me_icon@2x.png','text_color':'#6A6A6AFF'}]}]}}");

    protected abstract SharedPreferences getGlobalPreferences();

    public abstract class CommonPreference<T> {
        private final String id;
        private T defaultValue;

        /**
         * @param id           数据保存的Key
         * @param defaultValue 初始值
         */
        public CommonPreference(String id, T defaultValue) {
            this.id = id;
            this.defaultValue = defaultValue;
        }

        protected T getDefaultValue() {
            return defaultValue;
        }

        public abstract T getValue();

        public abstract boolean setValue(T val);

        public String getId() {
            return id;
        }

        /**
         * 重置为初始值
         */
        public void resetToDefault() {
            setValue(getDefaultValue());
        }
    }

    /**
     * 整型数值参数保存
     */
    public class IntPreference extends CommonPreference<Integer> {
        public IntPreference(String id, int defaultValue) {
            super(id, defaultValue);
        }

        @Override
        public Integer getValue() {
            return getGlobalPreferences().getInt(getId(), getDefaultValue());
        }

        @Override
        public boolean setValue(Integer val) {
            return getGlobalPreferences().edit().putInt(getId(), val).commit();
        }
    }

    /**
     * 长整型数值参数保存
     */
    public class LongPreference extends CommonPreference<Long> {
        public LongPreference(String id, long defaultValue) {
            super(id, defaultValue);
        }

        @Override
        public Long getValue() {
            return getGlobalPreferences().getLong(getId(), getDefaultValue());
        }

        @Override
        public boolean setValue(Long val) {
            return getGlobalPreferences().edit().putLong(getId(), val).commit();
        }
    }

    /**
     * 布尔型参数保存
     */
    public class BooleanPreference extends CommonPreference<Boolean> {
        public BooleanPreference(String id, boolean defaultValue) {
            super(id, defaultValue);
        }

        @Override
        public Boolean getValue() {
            return getGlobalPreferences()
                    .getBoolean(getId(), getDefaultValue());
        }

        @Override
        public boolean setValue(Boolean val) {
            return getGlobalPreferences().edit().putBoolean(getId(), val)
                    .commit();
        }
    }

    /**
     * String参数保存
     */
    public class StringPreference extends CommonPreference<String> {

        public StringPreference(String id, String defaultValue) {
            super(id, defaultValue);
        }

        @Override
        public String getValue() {
            return getGlobalPreferences().getString(getId(), getDefaultValue());
        }

        @Override
        public boolean setValue(String val) {
            return getGlobalPreferences().edit().putString(getId(), val)
                    .commit();
        }
    }

    /**
     * String参数保存
     */
    public class FloatPreference extends CommonPreference<Float> {

        public FloatPreference(String id, Float defaultValue) {
            super(id, defaultValue);
        }

        @Override
        public Float getValue() {
            return getGlobalPreferences().getFloat(getId(), getDefaultValue());
        }

        @Override
        public boolean setValue(Float fvalue) {
            return getGlobalPreferences().edit().putFloat(getId(), fvalue)
                    .commit();
        }
    }

    /**
     * 枚举型设置参数保存，适用于多选一。
     */
    public class EnumIntPreference<E extends Enum<E>> extends
            CommonPreference<E> {
        private final E[] values;

        public EnumIntPreference(String id, E defaultValue, E[] values) {
            super(id, defaultValue);
            this.values = values;
        }

        @Override
        public E getValue() {
            try {
                int i = getGlobalPreferences().getInt(getId(), -1);
                if (i >= 0 && i < values.length) {
                    return values[i];
                }
            } catch (ClassCastException ex) {
                setValue(getDefaultValue());
            }
            return getDefaultValue();
        }

        @Override
        public boolean setValue(E val) {
            return getGlobalPreferences().edit().putInt(getId(), val.ordinal())
                    .commit();
        }
    }

}
