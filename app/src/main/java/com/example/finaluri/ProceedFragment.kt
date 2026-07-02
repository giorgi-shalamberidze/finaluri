package com.example.finaluri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finaluri.databinding.ActivityMainBinding
import com.example.finaluri.databinding.FragmentProceedBinding

class ProceedFragment : Fragment() {

    lateinit var binding: FragmentProceedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProceedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val salary = arguments?.getString("SALARY")
        val rent = arguments?.getString("RENT")
        val food = arguments?.getString("FOOD")
        val surname = arguments?.getString("SURNAME") ?: ""
        val dayofborn = arguments?.getString("DAYOFBORN")

        val salaryToInt = salary?.toIntOrNull() ?: 0
        val rentToInt = rent?.toIntOrNull() ?: 0
        val dayofbornToInt = dayofborn?.toIntOrNull() ?: 0

        val priceB4Dis = calculateb4Discount(salaryToInt, rentToInt)

        val totalPrice = discount(surname, dayofbornToInt, priceB4Dis)
        val ttlPriceFormated = "%.1f".format(totalPrice)

        gshfoodEditText.text = "საჭმელი არის: $food \n საჭმელი არ ვიცოდი რაში გამომეყენებინა :)"
        gshfinalInfoText.text = "საბოლოო გადასახადი არის $ttlPriceFormated"
    }

    //ფუნქცია მუშაობს ფასდაკლებაზე//
    private fun discount(surnameChars: String, dateOfBorn: Int, totalPrice: Int): Double {
        val surnameLength = surnameChars.length
        val sum = surnameLength + dateOfBorn
        val discountAmount = ((100 - sum) / 100.0) * totalPrice

        return discountAmount
    }

    private fun calculateb4Discount(salary: Int, rent: Int): Int {
        val price = salary - rent
        return price
    }

}