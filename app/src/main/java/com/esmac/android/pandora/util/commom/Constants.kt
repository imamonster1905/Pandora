package com.esmac.android.pandora.util.commom

object Constants {
    const val DEFAULT_FIRST_PAGE = 1
    const val DEFAULT_NUM_VISIBLE_THRESHOLD = 3
    const val DEFAULT_ITEM_PER_PAGE = 20
    const val DEFAULT_LOAD_MORE_PARAM_PAGE = "page"
    const val DEFAULT_LOAD_MORE_PARAM_SIZE = "size"

    const val METHOD_PUT = "PUT"
    const val THRESHOLD_CLICK_TIME = 1000
    const val DEVICE_TYPE = "android"
    const val LOAD_MORE_PARAM_PAGE = "page"
    const val LOAD_MORE_PARAM_LIMIT = "limit"

    const val placeHolder = -1/*R.drawable.ic_menu_gallery*/

    class Image {
        companion object {
            const val UPLOAD_IMAGE_TYPE_PHYSICAL_DAMAGE_COVERAGE = "physical_damage_coverage"
            const val UPLOAD_IMAGE_TYPE_CIVIL_LIABILITY_INSURANCE = "civil_liability_insurance"
            const val UPLOAD_IMAGE_TYPE_FRONT = "front"
            const val UPLOAD_IMAGE_TYPE_TAIL = "tail"
            const val UPLOAD_IMAGE_TYPE_LEFT = "left"
            const val UPLOAD_IMAGE_TYPE_RIGHT = "right"
            const val UPLOAD_IMAGE_TYPE_INSIDE_1 = "inside_1"
            const val UPLOAD_IMAGE_TYPE_INSIDE_2 = "inside_2"
        }
    }

    class CollapseKey {
        companion object {
            const val COLLAPSE_KEY_SYSTEM = "system"
            const val COLLAPSE_KEY_MY_CAR = "my_car"
            const val COLLAPSE_KEY_TRIP = "trip"
            const val COLLAPSE_KEY_WALLET = "wallet"
        }
    }

    class PaymentMethod {
        companion object {
            const val ALEPAY= "alepay"
            const val WALLET = "wallet"
        }
    }

    class AppPref {
        companion object {
            const val APP_SHARED_PREFERENCES = "dicar_shared_pref"
        }
    }
}