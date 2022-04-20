package com.training.project.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author:          pwj
 * @Date:            2021/10/29 15:40
 * @FileName:        BaseActivity
 * @Description:     description
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int

    var dataBindView : View? = null

}