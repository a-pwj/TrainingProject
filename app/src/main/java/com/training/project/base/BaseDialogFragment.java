package com.training.project.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: pwj
 * @Date: 2021/11/9 14:39
 * @FileName: BaseDialogFragment.java
 * @Description: BaseDialogFragment.java
 */
public abstract class BaseDialogFragment<DB extends ViewDataBinding> extends MyFragmentDialog {

    /**
     * 绑定布局
     */
    protected DB dialogBinding;
    private static final String TAG = "base_bottom_dialog";
    private static final int DEFAULT_GRAVITY = Gravity.CENTER;

    private static float DEFAULT_DIM = 0.5f;
    public OnDialogFragmentDismissListener mOnDialogFragmentDismissListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(MyFragmentDialog.STYLE_NO_TITLE, getStyle());
    }

    public DB getDialogBinding() {
        return dialogBinding;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isAdded()) {
            dismiss();
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        dialogBinding = reflexViewBinding();
        if (dialogBinding == null) {
            throw new IllegalArgumentException("dialog data binding error");
        }
        return dialogBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogBinding.setLifecycleOwner(this);
        initView(dialogBinding);
    }

    protected abstract int getStyle();

    public abstract void initView(DB dialogBinding);

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.dimAmount = getDimAmount();
        if (getWidth() != 0) {
            params.width = getWidth();
        } else {
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
        }
        if (getHeight() > 0) {
            params.height = getHeight();
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        params.gravity = getGravity();

        window.setAttributes(params);
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public void setDimAmount(float default_dim) {
        DEFAULT_DIM = default_dim;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    protected int getGravity() {
        return DEFAULT_GRAVITY;
    }

    public void show(FragmentManager fragmentManager, String tag) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            fragmentManager.beginTransaction().remove(this).commitAllowingStateLoss();
            if (!isAdded() && !isVisible() && !isRemoving()) {
                super.show(fragmentManager, tag);
            }
        } catch (Exception e) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }
    }

    public int show(FragmentTransaction transaction, String tag) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            transaction.remove(this).commitAllowingStateLoss();
            if (!isAdded() && !isVisible() && !isRemoving()) {
                return super.show(transaction, tag);
            }
        } catch (Exception e) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDialogFragmentDismissListener != null) {
            mOnDialogFragmentDismissListener.dialogDismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 消失监听
     */
    public interface OnDialogFragmentDismissListener {
        void dialogDismiss();
    }

    public void setOnDialogFragmentDismissListener(OnDialogFragmentDismissListener onDialogFragmentDismissListener) {
        this.mOnDialogFragmentDismissListener = onDialogFragmentDismissListener;
    }

    private DB reflexViewBinding() {
        try {
            Type superClass = getClass().getGenericSuperclass();
            if (superClass != null && superClass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) superClass).getActualTypeArguments();
                for (Type argument : actualTypeArguments) {
                    Class<?> tClass = (Class<?>) argument;
                    if (ViewDataBinding.class.isAssignableFrom(tClass)) {
                        Method method = tClass.getDeclaredMethod("inflate", LayoutInflater.class);
                        return (DB) method.invoke(null, getLayoutInflater());
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
