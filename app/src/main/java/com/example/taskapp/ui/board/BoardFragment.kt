package com.example.taskapp.ui.board

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBoardBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BoardAdapter(
            childFragmentManager,
            this::SkipClick,
            this::NextClick,
            this::changeBackground
        )
        binding.vpBoard.adapter = adapter
        binding.dotsIndicator.attachTo(binding.vpBoard)

    }

    private fun SkipClick() {
        binding.vpBoard.currentItem = 2
    }

    private fun NextClick() {
        binding.vpBoard.currentItem += 1
    }

    private fun changeBackground() {
        when (binding.vpBoard.currentItem) {
            0 -> {
                binding.vpBoard.setBackgroundColor(R.drawable.back_page_one)
            }
            1 -> {
                binding.vpBoard.setBackgroundColor(R.drawable.back_page_two)
            }
            2 -> {
                binding.vpBoard.setBackgroundColor(R.drawable.back_page_third)
            }
            else -> {
                binding.vpBoard.setBackgroundColor(Color.RED)
            }
        }
    }
}