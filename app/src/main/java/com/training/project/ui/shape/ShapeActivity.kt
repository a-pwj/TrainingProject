package com.training.project.ui.shape

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.shape.*
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityShapeBinding
import com.training.project.ext.dp2px
import com.training.project.test.CustomViewModel


/**
 * @Author:          pwj
 * @Date:            2021/11/9 13:46
 * @FileName:        ShapeActivity.kt
 * @Description:     MaterialShapeDrawable
 */
class ShapeActivity : BaseVBActivity<ActivityShapeBinding>() {
    val viewModel by viewModels<CustomViewModel.DataViewModel> { CustomViewModel.DataFactory(1) }

    @SuppressLint("RestrictedApi")
    override fun initView(savedInstanceState: Bundle?) {

        (mDataBinding.viewShape1.parent as ViewGroup).clipChildren = false


        val shapePathModel1 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(dp2px(10F).toFloat())
            .build()

        val backgroundDrawable1 = MaterialShapeDrawable(shapePathModel1).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        mDataBinding.viewShape1.background = backgroundDrawable1


        val markerEdgeTreatment = MarkerEdgeTreatment(50f)
        val offsetEdgeTreatment = OffsetEdgeTreatment(MarkerEdgeTreatment(20f), 20f)
        val bottomAppBarTopEdgeTreatment = BottomAppBarTopEdgeTreatment(5f, 10f, 0f).apply {
            //就是那个floatingActionBar的直径
            fabDiameter = 80f
        }
        val topEdgeTreatment = TriangleEdgeTreatment(dp2px(10F).toFloat(), false)
        val shapePathModel2 = ShapeAppearanceModel.builder()
            .setTopEdge(markerEdgeTreatment)
            .setLeftEdge(bottomAppBarTopEdgeTreatment)
            .setBottomEdge(topEdgeTreatment)
            .setRightEdge(offsetEdgeTreatment)
            .build()
        val backgroundDrawable2 = MaterialShapeDrawable(shapePathModel2).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        mDataBinding.viewShape2.background = backgroundDrawable2


        val shapePathModel3 = ShapeAppearanceModel.builder()
            .setTopLeftCorner(InnerCutCornerTreatment())
            .setTopRightCorner(InnerRoundCornerTreatment())
            .setBottomLeftCorner(ExtraRoundCornerTreatment())
            .setAllCornerSizes(dp2px(10F).toFloat())
            .build()

        val backgroundDrawable3 = MaterialShapeDrawable(shapePathModel3).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        mDataBinding.viewShape3.background = backgroundDrawable3


        val shapePathModel4 = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setAllCornerSizes(dp2px(10F).toFloat())
            .setTopEdge(ArgEdgeTreatment(20f, false))
            .setBottomEdge(QuadEdgeTreatment(50f))
            .build()

        val backgroundDrawable4 = MaterialShapeDrawable(shapePathModel4).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        mDataBinding.viewShape4.background = backgroundDrawable4

        val shapePathModel5 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(dp2px(10F).toFloat())
            .build()

        val backgroundDrawable5 = MaterialShapeDrawable(shapePathModel5).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(this@ShapeActivity)
            elevation = 20f
            setShadowColor(Color.parseColor("#D2D2D2"))
            shadowVerticalOffset = 5
        }

        mDataBinding.viewShape5.background = backgroundDrawable5


    }

    /**
     * 自定义
     */
    inner class InnerCutCornerTreatment : CornerTreatment() {

        override fun getCornerPath(shapePath: ShapePath, angle: Float, interpolation: Float, radius: Float) {
            val radius = radius * interpolation
            shapePath.reset(0f, radius, 180f, 180 - angle)
            shapePath.lineTo(radius, radius)
            shapePath.lineTo(radius, 0f)
        }
    }

    class InnerRoundCornerTreatment : CornerTreatment() {
        override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
            val radius = size * f
            shapePath.reset(0f, radius, 180f, 180 - angle)
            shapePath.addArc(-radius, -radius, radius, radius, angle, -90f)
        }
    }

    class ExtraRoundCornerTreatment : CornerTreatment() {
        override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
            val radius = size * f
            shapePath.reset(0f, radius, 180f, 180 - angle)
            shapePath.addArc(-radius, -radius, radius, radius, angle, 270f)
        }
    }


    class ArgEdgeTreatment(val size: Float, val inside: Boolean) : EdgeTreatment() {
        override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
            val radius = size * f
            shapePath.lineTo(center - radius, 0f)
            shapePath.addArc(
                center - radius, -radius,
                center + radius, radius,
                180f,
                if (inside) -180f else 180f
            )
            shapePath.lineTo(length, 0f)
        }
    }

    class QuadEdgeTreatment(val size: Float) : EdgeTreatment() {
        override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
            shapePath.quadToPoint(center, size * f, length, 0f)
        }
    }
}