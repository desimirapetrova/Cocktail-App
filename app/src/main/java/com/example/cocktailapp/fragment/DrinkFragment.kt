package com.example.cocktailapp.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cocktailapp.R
import com.example.cocktailapp.adapter.MIAdapter
import com.example.cocktailapp.activity.MainActivity
import com.example.cocktailapp.databinding.FragmentDrinkDetailsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DrinkFragment : Fragment() {

    private lateinit var binding: FragmentDrinkDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrinkDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        obverseData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedDrinkId = arguments?.getString("drink_id", null)
        GlobalScope.launch {
            (activity as? MainActivity)?.drinkViewModel?.getDrink(
                selectedDrinkId ?: return@launch
            )
        }
    }

    private fun obverseData() {
        GlobalScope.launch {
            (activity as? MainActivity)?.drinkViewModel?.selectedDrink?.collect {
                binding.drink = it ?: return@collect
                val list = listOf(
                    it.strMeasure1?.plus(it.strIngredient1),
                    it.strMeasure2?.plus(it.strIngredient2),
                    it.strMeasure3?.plus(it.strIngredient3),
                    it.strMeasure4?.plus(it.strIngredient4),
                    it.strMeasure5?.plus(it.strIngredient5),
                    it.strMeasure6?.plus(it.strIngredient6),
                    it.strMeasure7?.plus(it.strIngredient7),
                    it.strMeasure8?.plus(it.strIngredient8),
                    it.strMeasure9?.plus(it.strIngredient9),
                    it.strMeasure10?.plus(it.strIngredient10),
                    it.strMeasure11?.plus(it.strIngredient11),
                    it.strMeasure12?.plus(it.strIngredient12),
                    it.strMeasure13?.plus(it.strIngredient13),
                    it.strMeasure14?.plus(it.strIngredient14),
                    it.strMeasure15?.plus(it.strIngredient15)
                )
                (activity as? MainActivity)?.runOnUiThread {
                    setDataBinding()

                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it?.strDrinkThumb)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivLogo)

                    val adapter = MIAdapter(list)
                    binding.tvMIList.adapter = adapter
                }
            }
        }
    }

    private fun setDataBinding() {
        binding.drink ?: return
        if (binding.drink?.favourite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.btnLike.setOnClickListener {
            val drink = binding.drink
            drink?.favourite = drink?.favourite != true

            if (drink?.favourite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            GlobalScope.launch {
                (activity as? MainActivity)?.drinkViewModel?.updateFavourites(
                    drink ?: return@launch
                )
            }
        }
    }
}