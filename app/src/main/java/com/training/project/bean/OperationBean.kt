package com.training.project.bean

/**
 * @Author:          pwj
 * @Date:            2021/11/9 13:55
 * @FileName:        RecyclerViewDataBean
 * @Description:     description
 */
data class OperationBean(
    val roomOperation: OperationEnum,
    val action: () -> Unit = {}
)

enum class OperationEnum(val defaultName: String) {

    ACTIVITY_SHAPE("MaterialShapeDrawable"),
    ACTIVITY_ANIM_SPRING("Spring Animation"),
    ACTIVITY_ANIM_MOTION("motion Animation"),
    ACTIVITY_ANIM_TEST_VIEW("test view Animation"),
    ACTIVITY_ANIM_ACTIVITY("activity Animation"),
    ACTIVITY_ANIM_DIALOG("dialog Animation"),
    ACTIVITY_VIEW_STUB("ViewStub Demo"),
    RESULT_API("Result Api"),
    ACTIVITY_DIALOG_FRAGMENT_LEAK("DialogFragment Leak Demo"),
    ACTIVITY_OPENGL_2("opengl2"),
    ACTIVITY_VIEWPAGER_2("ViewPager2"),


    /*******************************************  opengl2  ********************************************************/
    ACTIVITY_OPENGL_2_simple("基础框架"),
    ACTIVITY_OPENGL_2_point("点绘制"),
    ACTIVITY_OPENGL_2_shape_1("图形绘制"),
    ACTIVITY_OPENGL_2_shape_2("多边形"),
    ACTIVITY_OPENGL_2_ortho("正交投影"),
    ACTIVITY_OPENGL_2_colorful("渐变色"),
    ACTIVITY_OPENGL_2_colorful_2("渐变色-代码封装"),
}