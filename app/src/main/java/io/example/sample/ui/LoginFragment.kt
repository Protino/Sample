package io.example.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.example.sample.databinding.FragmentLoginBinding
import io.example.sample.repository.LoginModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = LoginViewModelFactory(activity!!.application, LoginModel())

        viewModel = ViewModelProviders.of(this, factory)
                .get<LoginViewModel>(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater)
        binding.run {
            setLifecycleOwner(this@LoginFragment)
            viewmodel = viewModel
        }
        return binding.root
    }
}
