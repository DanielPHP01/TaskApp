package com.example.taskapp.ui.home.new_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskapp.databinding.FragmentNewTaskBinding
import com.example.taskapp.ui.home.TaskMode
import com.example.taskapp.App

@Suppress("UNREACHABLE_CODE")
class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private var task: TaskMode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//      // initViews() метод не рабочий
//    }

    private fun initListeners() {
        getTask()
        checkTask()
    }
    private fun checkTask() {
        binding.btnSave.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty()) {
                if (task != null) {
                    updateTask()
                } else {
                    insertFun()
                }
            } else {
                binding.etTitle.error = "Заполните"
            }
        }
    }

    private fun getTask() {
        arguments?.let {
            val value = it.getSerializable("update")
            if (value != null) {
                task = value as TaskMode
                binding.etTitle.setText(task?.title.toString())
                binding.etDesc.setText(task?.desc.toString())
            }
        }
    }

    private fun updateTask() {
        task?.title = binding.etTitle.text.toString()
        task?.desc = binding.etDesc.text.toString()
        task?.let { App.database.TaskDao()?.update(it) }
        findNavController().navigateUp()
    }

    private fun insertFun() {
        binding.btnSave.setOnClickListener {
//            setFragmentResult(
//                "new_task", bundleOf(
//                    "title" to binding.etTitle.text.toString(),
//                    "desc" to binding.etDesc.text.toString()
//                )
//            ) App.database.TaskDao()?.insert(
            TaskMode(
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString()
            )
            findNavController().navigateUp()
        }
    }

}

//    fun initViews() {
//        @Override
//        public void onUserInteraction() {
//            super.onUserInteraction();
//            if (editText.hasFocus() ) { // to check if user is clicked on edit text
//                if (scroll.getVerticalScrollbarPosition() != scroll.getBottom())  // if scrollview is not already on the bottom
//                    scroll.post(() -> scroll.scrollTo(0, scroll.getBottom()));
//            }
//        }
//    }
//
//
//    private fun getSystemService(): Any {
//        return context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    }
//
//    fun showSoftKeyboard(view: View) {
//        if (view.requestFocus()) {
//            val imm = getSystemService() as InputMethodManager
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
//        }
//    }


