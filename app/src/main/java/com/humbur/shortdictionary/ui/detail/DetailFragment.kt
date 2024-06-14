package com.humbur.shortdictionary.ui.detail

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.FragmentDetailBinding


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var tts: TextToSpeech


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        tts = TextToSpeech(requireContext(), object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {

            }

        })

        viewModel.getWord.observe(viewLifecycleOwner){
            binding.wordEng.text = it.full
            binding.wordUz.text = it.uzb
            binding.title.text = it.short
        }

        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnVoice.setOnClickListener {
            speakOut()
        }


    }

    private fun speakOut() {
        val text = binding.wordEng.text.toString()
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
        _binding = null
    }

}