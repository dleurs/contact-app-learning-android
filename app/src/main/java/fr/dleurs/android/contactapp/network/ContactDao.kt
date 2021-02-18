package fr.dleurs.android.contactapp.database

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.dleurs.android.contactapp.network.ContactNtw
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://www.mocky.io/v2/5d63dcb93200007500ba1e43";

interface ContactDao {
    @GET("")
    suspend fun getContacts(): List<ContactNtw>
}

object ContactRetrofitApi {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : ContactDao by lazy { retrofit.create(ContactDao::class.java) }
}

