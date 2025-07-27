package com.hardwarestore.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Domain.Entity.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val allProducts: List<Product> = emptyList()
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _cartMessage = MutableStateFlow<String?>(null)
    val cartMessage: StateFlow<String?> = _cartMessage.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _cartItems = MutableStateFlow<MutableList<Product>>(mutableListOf())
    val cartItems: StateFlow<MutableList<Product>> = _cartItems.asStateFlow()


    private var currentSortOption: String = "Popular"
    private var filteredResults: List<Product> = emptyList()

    init {
        loadRecentSearches()
        // Show all products initially
        _searchResults.value = allProducts
        filteredResults = allProducts
    }

    fun searchProducts(query: String, category: String = "All") {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val results = if (query.isEmpty()) {
                    // Show all products when no search query
                    allProducts
                } else {
                    // Add to recent searches if not empty
                    addToRecentSearches(query)

                    // Perform local search
                    searchInProductList(query, category)
                }

                filteredResults = results
                applySorting(currentSortOption)

            } catch (e: Exception) {
                _errorMessage.value = "Search failed: ${e.message}"
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun searchInProductList(query: String, category: String): List<Product> {
        val searchQuery = query.lowercase().trim()

        return allProducts.filter { product ->
            // Category filter
            val matchesCategory = when (category) {
                "All" -> true
                else -> product.category.equals(category, ignoreCase = true)
            }

            // Text search in multiple fields
            val matchesQuery = if (searchQuery.isEmpty()) {
                true
            } else {
                product.name.lowercase().contains(searchQuery) ||
                        product.description.lowercase().contains(searchQuery) ||
                        product.category.lowercase().contains(searchQuery)
            }

            matchesCategory && matchesQuery
        }
    }

    fun setSortOption(sortOption: String) {
        currentSortOption = sortOption
        applySorting(sortOption)
    }

    private fun applySorting(sortOption: String) {
        val sortedResults = when (sortOption) {
            "Price: Low to High" -> filteredResults.sortedBy { it.price }
            "Price: High to Low" -> filteredResults.sortedByDescending { it.price }
            "Best Rating" -> filteredResults.sortedByDescending { it.rating }
            "Most Reviews" -> filteredResults.sortedByDescending { "64 review" }
            "Name A-Z" -> filteredResults.sortedBy { it.name }
            "Name Z-A" -> filteredResults.sortedByDescending { it.name }
            else -> filteredResults
        }
        _searchResults.value = sortedResults
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val currentCart = _cartItems.value.toMutableList()

                // Check if product already exists in cart
                val existingIndex = currentCart.indexOfFirst { it.id == product.id }
                if (existingIndex != -1) {
                    // If exists, you might want to increase quantity (depending on your Product model)
                    _cartMessage.value = "${product.name} already in cart"
                } else {
                    currentCart.add(product)
                    _cartItems.value = currentCart
                    _cartMessage.value = "${product.name} added to cart"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add to cart: ${e.message}"
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                val currentCart = _cartItems.value.toMutableList()
                currentCart.removeAll { it.id == productId }
                _cartItems.value = currentCart
            } catch (e: Exception) {
                _errorMessage.value = "Failed to remove from cart: ${e.message}"
            }
        }
    }



    fun filterByCategory(category: String) {
        val currentQuery = "" // You might want to store current query
        searchProducts(currentQuery, category)
    }

//    fun filterByPriceRange(minPrice: Double, maxPrice: Double) {
//        viewModelScope.launch {
//            try {
//                _isLoading.value = true
//
//                val priceFilteredResults = filteredResults.filter { product ->
//                    product.price >= minPrice && product.price <= maxPrice
//                }
//
//                val sortedResults = when (currentSortOption) {
//                    "Price: Low to High" -> priceFilteredResults.sortedBy { it.currentPrice }
//                    "Price: High to Low" -> priceFilteredResults.sortedByDescending { it.currentPrice }
//                    "Newest First" -> priceFilteredResults.sortedByDescending { it.createdAt }
//                    "Best Rating" -> priceFilteredResults.sortedByDescending { it.rating }
//                    "Most Reviews" -> priceFilteredResults.sortedByDescending { it.reviewCount }
//                    "Popular", "Relevance" -> priceFilteredResults.sortedByDescending { it.popularity }
//                    else -> priceFilteredResults
//                }
//
//                _searchResults.value = sortedResults
//            } catch (e: Exception) {
//                _errorMessage.value = "Failed to filter by price: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    fun getSearchSuggestions(query: String): List<String> {
        val suggestions = mutableListOf<String>()

        // Add recent searches that match
        suggestions.addAll(
            _recentSearches.value.filter {
                it.contains(query, ignoreCase = true)
            }.take(3)
        )

        // Add product names and brands that match
        val productSuggestions = allProducts
            .filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.brand.contains(query, ignoreCase = true)
            }
            .map { it.name }
            .distinct()
            .take(5 - suggestions.size)

        suggestions.addAll(productSuggestions)

        // Add popular search terms if we still need more
        if (suggestions.size < 5) {
            val popularTerms = listOf(
                "RAM DDR4", "SSD 1TB", "Intel CPU", "AMD Ryzen",
                "Graphics Card", "Motherboard", "Power Supply", "Gaming Memory"
            )

            suggestions.addAll(
                popularTerms.filter {
                    it.contains(query, ignoreCase = true) && !suggestions.contains(it)
                }.take(5 - suggestions.size)
            )
        }

        return suggestions
    }

    fun getAvailableCategories(): List<String> {
        return allProducts.map { it.category }.distinct().sorted()
    }

    fun getPriceRange(): Pair<Double, Double> {
        if (allProducts.isEmpty()) return Pair(0.0, 0.0)
        val prices = allProducts.map { it.price }
        return Pair(prices.minOrNull() ?: 0.0, prices.maxOrNull() ?: 0.0) as Pair<Double, Double>
    }

    private fun addToRecentSearches(query: String) {
        viewModelScope.launch {
            val currentSearches = _recentSearches.value.toMutableList()

            // Remove if already exists
            currentSearches.remove(query)

            // Add to beginning
            currentSearches.add(0, query)

            // Keep only last 10 searches
            if (currentSearches.size > 10) {
                currentSearches.removeAt(currentSearches.size - 1)
            }

            _recentSearches.value = currentSearches
            saveRecentSearches(currentSearches)
        }
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            try {
                // TODO: Load from SharedPreferences or local storage
                // val searches = sharedPreferences.getStringSet("recent_searches", emptySet())?.toList() ?: emptyList()
                // _recentSearches.value = searches
            } catch (e: Exception) {
                // Handle error silently for recent searches
            }
        }
    }

    private fun saveRecentSearches(searches: List<String>) {
        viewModelScope.launch {
            try {
                // TODO: Save to SharedPreferences or local storage
                // sharedPreferences.edit().putStringSet("recent_searches", searches.toSet()).apply()
            } catch (e: Exception) {
                // Handle error silently for recent searches
            }
        }
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
        saveRecentSearches(emptyList())
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearCartMessage() {
        _cartMessage.value = null
    }
}