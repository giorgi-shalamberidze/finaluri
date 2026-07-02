package com.example.finaluri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finaluri.databinding.FragmentHomeBinding
import android.graphics.Color

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        gshsubmitButton.setOnClickListener { //ვიღებ ინფორმაციას//
            val salary = gshsalaryEditText.text.toString()
            val rent = gshrentEditText.text.toString()
            val food = gshfoodEditText.text.toString()
            val surname = gshsurnameText.text.toString()
            val dayOfBorn = gshdateText.text.toString()

            val salaryInt = salary.toIntOrNull() ?: 0 //მონაცემი გადამყავს ინტიჯერში თუ კი არსებობს//
            val rentInt = rent.toIntOrNull() ?: 0

            if (salary.isNullOrEmpty() || rent.isEmpty() || food.isEmpty() || surname.isEmpty() || dayOfBorn.isNullOrEmpty()) {
                gshwentWrongText.text = "შეიყვანეთ ყველა მონაცემი"
            } else if(salaryInt < rentInt) {
                gshwentWrongText.text = "ხელფასი გადასახადზე ნაკლებია"
                gshsalaryEditText.setTextColor(Color.RED)
                gshrentEditText.setTextColor(Color.RED)
            } else {
                val bundle = Bundle()

                bundle.putString("SALARY", salary)
                bundle.putString("RENT", rent)
                bundle.putString("FOOD", food)
                bundle.putString("SURNAME", surname)
                bundle.putString("DAYOFBORN", dayOfBorn)

                val nextfragment = ProceedFragment()
                nextfragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, nextfragment)
                    .addToBackStack(null)
                    .commit()

            }
        }

    }


}