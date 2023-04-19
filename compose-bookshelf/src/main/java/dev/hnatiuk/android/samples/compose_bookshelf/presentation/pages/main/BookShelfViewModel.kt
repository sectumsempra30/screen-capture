package dev.hnatiuk.android.samples.compose_bookshelf.presentation.pages.main;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.hnatiuk.android.samples.compose_bookshelf.domain.entity.BookEntity
import dev.hnatiuk.android.samples.compose_bookshelf.domain.repository.IBooksRepository
import dev.hnatiuk.android.samples.compose_bookshelf.presentation.core.BooksShelfApplication
import kotlinx.coroutines.launch

sealed interface BooksUIState {

    data class Success(val books: List<BookEntity>) : BooksUIState

    object Error : BooksUIState

    object Loading : BooksUIState
}

class BookShelfViewModel(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    var booksUIState: BooksUIState by mutableStateOf(BooksUIState.Loading)
        private set

    init {
        loadBooks()
    }

    fun loadBooks(query: String = "book", maxResults: Int = 40) {
        viewModelScope.launch {
            booksUIState = BooksUIState.Loading
            booksRepository.getBooks(query, maxResults)
                .onSuccess { booksUIState = BooksUIState.Success(it) }
                .onFailure { booksUIState = BooksUIState.Error }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksShelfApplication)
                val booksRepository = application.appContainer.booksRepository
                BookShelfViewModel(booksRepository)
            }
        }
    }
}