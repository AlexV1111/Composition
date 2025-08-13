package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener { retryGame() }

        setImage()
        setText()
    }

    private fun setImage() {
        if (args.gameResult.winner) {
            val drawableSmile = ContextCompat.getDrawable(requireContext(), R.drawable.ic_smile)
            binding.emojiResult.setImageDrawable(drawableSmile)
        } else {
            val drawableSad = ContextCompat.getDrawable(requireContext(), R.drawable.ic_sad)
            binding.emojiResult.setImageDrawable(drawableSad)
        }
    }

    private fun setText() {

        with(binding) {
            tvRequiredAnswers.text = String.format(
                requireContext().resources.getString(R.string.required_score),
                args.gameResult.gameSettings.minCountOfRightAnswers
            )

            tvScoreAnswers.text = String.format(
                requireContext().resources.getString(R.string.score_answers),
                args.gameResult.countOfRightAnswers
            )

            tvRequiredPercentage.text = String.format(
                requireContext().resources.getString(R.string.required_percentage),
                args.gameResult.gameSettings.minPercentOfRightAnswers
            )

            tvScorePercentage.text = String.format(
                requireContext().resources.getString(R.string.score_percentage),
                if (args.gameResult.countOfQuestions == 0) ZERO_RESULT
                else ((args.gameResult.countOfRightAnswers / args.gameResult.countOfQuestions.toDouble())
                        * 100).toInt()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun retryGame() {
        findNavController().popBackStack()
    }

    companion object {
        private const val ZERO_RESULT = 0
    }

}