package com.example.flavorhub.data

import android.util.Log
import com.example.flavorhub.core.ApiService
import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.RestaurantRepo
import com.example.flavorhub.domain.dataModel.RestaurantModel
import com.example.flavorhub.domain.dataModel.ReviewModel
import com.example.flavorhub.domain.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestaurantRepoImpl(val restaurantDao: RestaurantDao,val apiService: ApiService) : RestaurantRepo {
    override suspend fun getRestaurantWithId(id: String): Flow<Resource<RestaurantEntity>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(restaurantDao.getRestaurants(id)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something went wrong"))
        }
    }

    override suspend fun getRestaurants(
        page: Int,
        filter: String
    ): Flow<Resource<List<RestaurantEntity>>> = flow {

        emit(Resource.Loading)

        try {
            val limit = 20
            val offset = page * limit
            Log.d("RestaurantData", "API Calling")
            val response = apiService.getRestroList(
                filter = filter,
                limit = limit,
                offset = offset,
                apiKey = "c42c50d8721147109535a6e68902b5bc"
            )

            Log.d("RestaurantData", "Code = ${response.code()}")

            if (response.isSuccessful && response.body() != null) {

                val restaurantModel = response.body()!!

                Log.d(
                    "RestaurantData",
                    restaurantModel.toString()
                )
                insertRestroListToDb(restaurantModel)
                val restaurantList =
                    restaurantDao.getAllRestaurant()
                Log.d(
                    "RestaurantData",
                    restaurantList.toString()
                )
                emit(
                    Resource.Success(restaurantList)
                )

            } else {
                emit(
                    Resource.Error(
                        response.errorBody()?.string()
                            ?: "Unknown API Error"
                    )
                )
            }

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.message ?: "Something went wrong"
                )
            )

            Log.d(
                "RestaurantData",
                e.toString()
            )
        }
    }
    override suspend fun getHiddenRestaurants(): Flow<Resource<List<RestaurantEntity>>> = flow{
        emit(Resource.Loading)
        try {
            emit(Resource.Success(restaurantDao.getHiddenRestaurant()))
        }catch (e:Exception){
            emit(Resource.Error(e.message?:"Something went wrong"))
        }
    }

    override suspend fun getReviews(): Flow<Resource<List<ReviewModel>>> = flow{
        emit(Resource.Loading)
        try {
//            emit(Resource.Success(restaurantDao.getReviews()))
        }catch (e:Exception){
            emit(Resource.Error(e.message?:"Something went wrong"))
        }
    }

    override suspend fun updateRestaurant(restro: RestaurantEntity) {
        restaurantDao.updateRestaurant(restro)
    }


    suspend fun insertRestroListToDb(result: RestaurantModel) {
        val mappedProfiles = result.features.map {
            RestaurantEntity(
                id = it.properties.name?:"Unknown",
                name = it.properties.name?:"Unknown",
                locality = it.properties.street?:"Unknown",
                dishes = it.properties.catering?.cuisine
                    ?.replace(";", ", ")
                    ?: "",
                rating = (it.properties.catering?.stars ?: (1..5).random()).toString(),
                image = getImage()
            )
        }
        restaurantDao.insertRestaurant(mappedProfiles)
    }

    fun getImage(): String{
        return "${(1..19).random()}"
    }
}