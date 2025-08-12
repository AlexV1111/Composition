package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult


class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })

        setImage()
        setText()
    }

    private fun setImage() {
        if (gameResult.winner) {
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
                gameResult.gameSettings.minCountOfRightAnswers
            )

            tvScoreAnswers.text = String.format(
                requireContext().resources.getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )

            tvRequiredPercentage.text = String.format(
                requireContext().resources.getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )

            tvScorePercentage.text = String.format(
                requireContext().resources.getString(R.string.score_percentage),
                if (gameResult.countOfQuestions == 0) ZERO_RESULT
                else (gameResult.countOfRightAnswers / gameResult.countOfQuestions) * 100
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let { gameResult = it }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"
        private const val ZERO_RESULT = 0

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }

}