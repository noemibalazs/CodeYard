package com.example.codeyard

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class User(
    @SerializedName("name")
    val name: UserName,
    @SerializedName("location")
    val location: UserLocation,
    @SerializedName("email")
    val email: String,
    @SerializedName("cell")
    val mobileNumber: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("picture")
    val picture: UserPicture
) : Parcelable

@Keep
@Parcelize
data class UserName(
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val firstName: String,
    @SerializedName("last")
    val lastName: String
) : Parcelable

@Keep
@Parcelize
data class UserLocation(
    @SerializedName("street")
    val street: UserStreet,
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
data class UserStreet(
    @SerializedName("number")
    val number: Int,
    @SerializedName("name")
    val name: String
) : Parcelable

@Keep
@Parcelize
data class UserPicture(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("thumbnail")
    val thumbNail: String
) : Parcelable


fun RemoteUser.toUser() = User(
    name = name.toUserName(),
    location = location.toUserLocation(),
    email = email,
    mobileNumber = mobileNumber,
    phoneNumber = phoneNumber,
    picture = picture.toUUserPicture()
)

fun RemoteUserName.toUserName() = UserName(
    title = title,
    firstName = firstName,
    lastName = lastName
)

fun RemoteUserLocation.toUserLocation() = UserLocation(
    street = street.toUserStreet(),
    city = city,
    state = state,
    county = county,
    postCode = postCode

)

fun RemoteUserStreet.toUserStreet() = UserStreet(
    number = number,
    name = name
)

fun RemoteUserPicture.toUUserPicture() = UserPicture(
    large = large,
    medium = medium,
    thumbNail = thumbNail
)