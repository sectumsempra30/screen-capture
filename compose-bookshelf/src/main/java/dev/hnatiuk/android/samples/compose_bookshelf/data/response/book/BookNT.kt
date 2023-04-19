package dev.hnatiuk.android.samples.compose_bookshelf.data.response.book;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BookShelfNT(
    @SerialName("items") val books: List<BookNT>? = null
)

@Serializable
class BookNT(
    @SerialName("id") val id: String? = null,
    @SerialName("volumeInfo") val volumeInfo: VolumeInfoNT? = null
)

@Serializable
class VolumeInfoNT(
    @SerialName("title") val title: String? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("imageLinks") val imageLinks: ImageLinksNT? = null
)

@Serializable
class ImageLinksNT(
    @SerialName("thumbnail") val thumbnail: String? = null
)