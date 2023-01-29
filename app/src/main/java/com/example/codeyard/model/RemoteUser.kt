package com.example.codeyard

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteResults(
    @SerializedName("results")
    val results: List<RemoteUser>
) : Parcelable

@Keep
@Parcelize
data class RemoteUser(
    @SerializedName("name")
    val name: RemoteUserName,
    @SerializedName("location")
    val location: RemoteUserLocation,
    @SerializedName("email")
    val email: String,
    @SerializedName("cell")
    val mobileNumber: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("picture")
    val picture: RemoteUserPicture
) : Parcelable

@Keep
@Parcelize
data class RemoteUserName(
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val firstName: String,
    @SerializedName("last")
    val lastName: String
) : Parcelable

@Keep
@Parcelize
data class RemoteUserLocation(
    @SerializedName("street")
    val street: RemoteUserStreet,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("county")
    val county: String?,
    @SerializedName("postcode")
    val postCode: String
) : Parcelable

@Keep
@Parcelize
data class RemoteUserStreet(
    @SerializedName("number")
    val number: Int,
    @SerializedName("name")
    val name: String
) : Parcelable

@Keep
@Parcelize
data class RemoteUserPicture(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("thumbnail")
    val thumbNail: String
) : Parcelable


