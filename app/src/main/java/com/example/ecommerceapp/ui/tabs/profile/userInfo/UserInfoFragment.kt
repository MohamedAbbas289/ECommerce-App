package com.example.ecommerceapp.ui.tabs.profile.userInfo

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecommerceapp.databinding.FragmentUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment : Fragment() {
    private lateinit var binding: FragmentUserInfoBinding
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        viewModel.invokeAction(UserInfoContract.Action.GetUser())
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewStates(it) }
            }
        }

        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.userName.observe(viewLifecycleOwner) {
            binding.fullNameEditText.text = Editable.Factory.getInstance().newEditable(it)
        }
        viewModel.email.observe(viewLifecycleOwner) {
            binding.emailEditText.text = Editable.Factory.getInstance().newEditable(it)
        }
    }

    private fun handleEvents(event: UserInfoContract.Event) {
        when (event) {
            is UserInfoContract.Event.NavigateToProfile -> navigateToProfile()
        }
    }

    private fun navigateToProfile() {
        parentFragmentManager.popBackStack()
    }

    private fun renderViewStates(state: UserInfoContract.State) {
        when (state) {
            is UserInfoContract.State.Error -> handleError(state.message)
            is UserInfoContract.State.Loading -> handleLoading()
            is UserInfoContract.State.Success -> handleSuccess()
            is UserInfoContract.State.Initial -> return
            is UserInfoContract.State.UpdateUserError -> showErrorToast(state.message)
            is UserInfoContract.State.UpdateUserSuccess -> showSuccessToast(state.message)
        }
    }

    private fun showSuccessToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
        binding.loadingView.isVisible = false
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
        binding.loadingView.isVisible = false
    }

    private fun handleError(message: String) {
        binding.successView.isVisible = false
        binding.errorView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(UserInfoContract.Action.GetUser())
        }
    }

    private fun handleSuccess() {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false
    }

    private fun handleLoading() {
        binding.loadingView.isVisible = true
    }

    private fun initViews() {
        binding.updateProfileBtn.setOnClickListener {
            val userName = binding.fullNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            viewModel.invokeAction(UserInfoContract.Action.UpdateUser(userName, email, phone))
        }
    }
}
