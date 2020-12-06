package com.urveshtanna.recipescaavo.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.urveshtanna.recipescaavo.R
import com.urveshtanna.recipescaavo.databinding.CartFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()
    lateinit var binding: CartFragmentBinding
    lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.cartItems.observe(viewLifecycleOwner, {
            adapter.addAll(it)
        })
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(arrayListOf()) {
            viewModel.updateQuantity(it)
            if (adapter.list.size == 0) {
                Toast.makeText(requireContext(), getString(R.string.your_cart_is_empty_please_add_some_products), Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
    }

}