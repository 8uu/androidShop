package com.ponomar.shoper.ui.auth.firstName1

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ponomar.shoper.databinding.FragmentAuthFirstNameBinding
import com.ponomar.shoper.extensions.fadeIn
import com.ponomar.shoper.extensions.getActivity
import com.ponomar.shoper.ui.auth.FragmentCallBacks
import com.ponomar.shoper.util.OnSwipeTouchListener

class AuthFirstNameFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding:FragmentAuthFirstNameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthFirstNameBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.authTitle.fadeIn()
            this.authDesc.fadeIn(delay = 500)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay=1000)
            authButtonGoToNextStage.setOnClickListener {
                val firstName = authEditTextFirstName.text.toString()
                if(firstName.isNotEmpty()) (it.context as FragmentCallBacks).onFragment1NextClick(firstName)
                else{
                    val toast = Toast.makeText(root.context,"Пустое поле",Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.BOTTOM,0,100)
                    toast.show()
                }
            }

            root.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    (requireContext().getActivity() as FragmentCallBacks).onFragment1BackClick()
                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    authButtonGoToNextStage.performClick()
                }
            })
        }
    }
}