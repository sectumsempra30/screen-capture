package dev.hnatiuk.android.samples.compose_bookshelf.presentation.pages.main;

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.hnatiuk.android.samples.compose_bookshelf.domain.entity.BookEntity
import dev.hnatiuk.compose_bookshelf.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = stringResource(id = R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.loading_failed))
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun BookCard(
    book: BookEntity,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .requiredHeight(296.dp),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = book.title,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            AsyncImage(
                modifier = modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageLink?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_book_96),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.content_description),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun BooksGreedScreen(
    books: List<BookEntity>,
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        itemsIndexed(books) { _, book ->
            BookCard(book = book, modifier = modifier)
        }
    }
}

@Composable
fun HomeScreen(
    booksUIState: BooksUIState,
    retryAction: () -> Unit,
    modifier: Modifier
) {
    when (booksUIState) {
        BooksUIState.Loading -> LoadingScreen()
        is BooksUIState.Error -> ErrorScreen(retryAction)
        is BooksUIState.Success -> BooksGreedScreen(books = booksUIState.books, modifier = modifier)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookShelfApp(
    modifier: Modifier = Modifier
) {
    val viewModel: BookShelfViewModel = viewModel(factory = BookShelfViewModel.Factory)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                booksUIState = viewModel.booksUIState,
                retryAction = { viewModel.loadBooks() },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun JustPreview() {
    BookCard(
        BookEntity(id = "1", title = "Some title", publishDate = "2004", imageLink = null),
        modifier = Modifier
    )
}