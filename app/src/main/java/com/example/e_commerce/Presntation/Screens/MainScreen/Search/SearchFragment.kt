package com.example.e_commerce.Presntation.Screens.MainScreen.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.HomePage.MainScreenFragment
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSearchBinding
import com.hardwarestore.presentation.adapters.ProductSearchAdapter
import com.hardwarestore.presentation.screens.search.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: ProductSearchAdapter

    private var searchJob: Job? = null
    private var currentQuery: String = ""
    private var selectedFilter: String = "All"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityInstance = (activity as? MainScreen)
        searchViewModel = SearchViewModel(activityInstance!!.productsViwModel.productList.value!!)
        setupViews()
        setupRecyclerView()
        setupObservers()
        setupSearchFunctionality()
        setupFilterButtons()

    }

    private fun setupViews() {
        binding.apply {
            backButton.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MainScreenFragment()).commit()
            }

            voiceSearch.setOnClickListener {
                Toast.makeText(context, "Voice search coming soon!", Toast.LENGTH_SHORT).show()
            }

            filterButton.setOnClickListener {
                showAdvancedFilters()
            }

            sortButton.setOnClickListener {
                showSortOptions()
            }
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = ProductSearchAdapter(onItemClick = { product ->
            navigateToProductDetail(product)
        }, onAddToCartClick = { product ->
            searchViewModel.addToCart(product)
            showAddToCartSuccess(product)
        }, onFavoriteClick = { product ->
        })

        binding.searchResultsRecycler.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 1)

            // Scroll to top when new search results arrive
            searchAdapter.registerAdapterDataObserver(object :
                androidx.recyclerview.widget.RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0) {
                        scrollToPosition(0)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchResults.collect { products ->
                searchAdapter.submitList(products)
                updateResultsCount(products.size)
                toggleEmptyState(products.isEmpty() && currentQuery.isNotEmpty())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.isLoading.collect { isLoading ->
                // Show/hide loading state if needed
                // binding.loadingIndicator.isVisible = isLoading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.errorMessage.collect { error ->
                error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    searchViewModel.clearError()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.cartMessage.collect { message ->
                message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    searchViewModel.clearCartMessage()
                }
            }
        }
    }

    private fun setupSearchFunctionality() {
        binding.searchInput.addTextChangedListener { editable ->
            val query = editable?.toString()?.trim() ?: ""

            // Cancel previous search job
            searchJob?.cancel()

            // Debounce search to avoid too many API calls
            searchJob = lifecycleScope.launch {
                delay(300) // Wait 300ms before searching
                if (query != currentQuery) {
                    currentQuery = query
                    performSearch(query)
                }
            }
        }

        // Handle search action from IME
        binding.searchInput.setOnEditorActionListener { _, _, _ ->
            val query = binding.searchInput.text.toString().trim()
            if (query.isNotEmpty()) {
                performSearch(query)
                hideKeyboard()
            }
            true
        }
    }

    private fun setupFilterButtons() {
        val filterButtons = listOf(
            binding.filterAll to "All",
            binding.filterRam to "RAM",
            binding.filterSsd to "SSD",
            binding.filterCpu to "CPU",
            binding.filterGpu to "GPU"
        )

        filterButtons.forEach { (button, category) ->
            button.setOnClickListener {
                selectFilter(category)
                updateFilterButtons(category)
                performSearch(currentQuery, category)
            }
        }
    }

    private fun selectFilter(category: String) {
        selectedFilter = category
    }

    private fun updateFilterButtons(selectedCategory: String) {
        val filterButtons = mapOf(
            "All" to binding.filterAll,
            "RAM" to binding.filterRam,
            "SSD" to binding.filterSsd,
            "CPU" to binding.filterCpu,
            "GPU" to binding.filterGpu
        )

        filterButtons.forEach { (category, cardView) ->
            val textView = cardView.getChildAt(0) as android.widget.TextView
            if (category == selectedCategory) {
                cardView.setCardBackgroundColor(
                    resources.getColor(R.color.primary_blue, null)
                )
                textView.setTextColor(resources.getColor(android.R.color.white, null))
            } else {
                cardView.setCardBackgroundColor(
                    resources.getColor(android.R.color.white, null)
                )
                textView.setTextColor(resources.getColor(R.color.text_secondary, null))
            }
        }

    }

    private fun performSearch(query: String, category: String = selectedFilter) {
        searchViewModel.searchProducts(query, category)
    }

    private fun updateResultsCount(count: Int) {
        binding.resultsCount.text = when {
            currentQuery.isEmpty() -> "Popular products"
            count == 0 -> "No products found"
            count == 1 -> "1 product found"
            else -> "$count products found"
        }
    }

    private fun toggleEmptyState(show: Boolean) {
        binding.emptyState.visibility = if (show) View.VISIBLE else View.GONE
        binding.searchResultsRecycler.visibility = if (show) View.GONE else View.VISIBLE
        binding.resultsHeader.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun showAdvancedFilters() {
        // TODO: Implement advanced filters dialog/bottom sheet
        Toast.makeText(context, "Advanced filters coming soon!", Toast.LENGTH_SHORT).show()
    }

    private fun showSortOptions() {
        val sortOptions = arrayOf(
            "Popular",
            "Price: Low to High",
            "Price: High to Low",
            "Newest First",
            "Best Rating",
            "Most Reviews"
        )

        androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle("Sort by")
            .setItems(sortOptions) { _, which ->
                val selectedSort = sortOptions[which]
                binding.sortButton.text = selectedSort
                searchViewModel.setSortOption(selectedSort)
                performSearch(currentQuery, selectedFilter)
            }.show()
    }

    private fun navigateToProductDetail(product: Product) {
        // TODO: Navigate to product detail fragment
        // findNavController().navigate(
        //     SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id)
        // )
        Toast.makeText(requireContext(), "Opening ${product.name}", Toast.LENGTH_SHORT).show()
    }

    private fun showAddToCartSuccess(product: Product) {
        Toast.makeText(
            requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT
        ).show()
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchInput.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}


