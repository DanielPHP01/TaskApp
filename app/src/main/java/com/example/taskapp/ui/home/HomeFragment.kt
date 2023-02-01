package com.example.taskapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.App
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentHomeBinding

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort_menu) {

            val items = arrayOf("Дата", "A-z", "z-A")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Сортировать")
            builder.setItems(items) { _, which ->
                when (which) {
                    0 -> {
                        adapter.addTasks(
                            App.database.TaskDao()?.getAllTaskByDate() as List<TaskMode>
                        )
                    }
                    1 -> {
                        adapter.addTasks(
                            App.database.TaskDao()?.getAllTaskByAlphabetAz() as List<TaskMode>
                        )

                    }
                    2 -> {
                        adapter.addTasks(
                            App.database.TaskDao()?.getAllTaskByAlphabetZa() as List<TaskMode>
                        )
                    }
                }
            }
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.newTask)
        }
    }

    private fun initViews() {
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.adapter = adapter
        setData()

//        setFragmentResultListener("new_task") { key, bundle ->
//            val title = bundle.getString("title")
//            val desc = bundle.getString("desc")
//            Log.e("ololo", "initViews: $title и $desc")
//
//            adapter.addTask(TaskMode(title, desc))
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(this::onLongClickListener,this::onUpdateClickListener)
    }

    private fun onLongClickListener(pos: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удаление")
        builder.setMessage("Вы точно хотите удалить ?")

        builder.setPositiveButton("Да") { _, _ ->
            App.database.TaskDao()?.delete(adapter.getTask(pos))
            setData()
        }
        builder.setNegativeButton("Нет") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun setData() {
        val listOfTask = App.database.TaskDao()?.getAllTask()
        adapter.addTasks(listOfTask as List<TaskMode>)
    }
    private  fun onUpdateClickListener(taskMode:TaskMode) {
        findNavController().navigate(R.id.newTask, bundleOf("update" to taskMode))
    }
}