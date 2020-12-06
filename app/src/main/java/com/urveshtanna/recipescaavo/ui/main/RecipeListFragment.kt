package com.urveshtanna.recipescaavo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.urveshtanna.recipescaavo.R
import com.urveshtanna.recipescaavo.data.model.Recipe
import com.urveshtanna.recipescaavo.databinding.BottomSheetRecipeInformationBinding
import com.urveshtanna.recipescaavo.databinding.RecipeListFragmentBinding
import com.urveshtanna.recipescaavo.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private val viewModel: RecipeListViewModel by viewModels()
    lateinit var binding: RecipeListFragmentBinding
    lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCartClick()
        setupObserver()
    }

    private fun setupCartClick() {
        binding.btnGoToCart.setOnClickListener {
            if (viewModel.cartCount.value == 0) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.your_cart_is_empty_please_add_some_products),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.action_recipe_list_to_cart)
            }
        }
    }

    private fun setupObserver() {
        viewModel.recipes.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    updateAdapter(it.data)
                    viewModel.getCartCount()
                    binding.progressBar.visibility = View.GONE
                    binding.recipeRecyclerView.visibility = View.VISIBLE
                }

                Resource.Status.ERROR -> {
                    it.message?.let { it1 ->
                        Snackbar.make(binding.root, it1, Snackbar.LENGTH_SHORT).show()
                    }
                }

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recipeRecyclerView.visibility = View.GONE
                }
            }
        })

        viewModel.cartCount.observe(viewLifecycleOwner, {
            if (it != null) {
                val text = resources.getQuantityString(R.plurals.cart_items, it, it)
                binding.btnGoToCart.text = text
            }
        })
    }

    private fun updateAdapter(data: List<Recipe>?) {
        adapter.addRecipes(data)
    }

    private fun setupRecyclerView() {
        adapter = RecipeAdapter(arrayListOf()) { recipe: Recipe, showInfo: Boolean ->
            if (showInfo) {
                showInfoBottomSheet(recipe)
            } else {
                viewModel.updateQuantity(recipe)
            }
        }
        binding.recipeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recipeRecyclerView.adapter = adapter
    }

    private fun showInfoBottomSheet(recipe: Recipe) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetRecipeDetailBinding = BottomSheetRecipeInformationBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetRecipeDetailBinding.model = recipe
        bottomSheetDialog.setContentView(bottomSheetRecipeDetailBinding.root)
        bottomSheetRecipeDetailBinding.btnAddToCart.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

}