package fr.dleurs.android.contactapp.database

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.dleurs.android.contactapp.network.ContactNetwork
import fr.dleurs.android.contactapp.network.ContactNetworkContainer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.mocky.io/v2/5d63dcb93200007500ba1e43/";

interface ContactNtwDao {
    @GET(".")
    suspend fun getContacts(): ContactNetworkContainer
}

object ContactRetrofitApi {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val contacts = retrofit.create(ContactNtwDao::class.java)
}

