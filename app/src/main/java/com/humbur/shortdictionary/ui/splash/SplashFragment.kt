package com.humbur.shortdictionary.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.FragmentSplashBinding
import com.humbur.shortdictionary.local.AppDatabaseLocal
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.WordsEntity
import com.humbur.shortdictionary.utils.MyNavOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashFragment : Fragment(R.layout.fragment_splash) {

    //=================== VIEW BINDING ===================//
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashBinding.bind(view)
        lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(
                R.id.homeFragment,
                null,
                MyNavOption.setOption(R.id.splashFragment)
            )
        }

        //navigateToHome()
    }

    private fun navigateToHome() {
        val data = AssetDatabaseOpenHelper(requireContext(), "dictionary.db")
        val database = AppDatabaseLocal.getDatabase(requireContext())

        lifecycleScope.launch {
            val cursor = data.saveDatabase().rawQuery("SELECT * FROM `translations`", null)

            while (cursor.moveToNext()) {
                val uzb = cursor.getString(1)
                val eng = cursor.getString(2)

                withContext(Dispatchers.IO){
                    database.wordsDao().insert(WordsEntity( eng = eng, uzb = uzb, typeA = "Tr"))
                }
            }
            Toast.makeText(requireContext(), "Tamam", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
