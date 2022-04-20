package com.training.project.ui.result_api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.blankj.utilcode.util.ToastUtils
import com.training.project.R
import com.training.project.ui.dialog.WelcomeDialogFragment

const val ARG_PARAM1 = "param1"
const val RESULT_KEY = "result_key"
const val RESULT_BUNDLE_KEY = "result_bundle_key"

/**
 * A simple [Fragment] subclass.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageFragment : Fragment() {
    private var param1: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text_view)
        param1?.let {
            textView.text = param1.toString()
        }

        textView.setOnClickListener {
            WelcomeDialogFragment().show(childFragmentManager, "WelcomeDialogFragment")
        }
        childFragmentManager.setFragmentResultListener(RESULT_KEY, this) { key, bundle ->
            ToastUtils.showShort("page===${param1}===接收到的Key $key value ${bundle.getString(RESULT_BUNDLE_KEY)}")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}