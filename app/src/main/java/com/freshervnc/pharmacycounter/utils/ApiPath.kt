package com.freshervnc.pharmacycounter.utils

object ApiPath {
    const val LOGIN_AGENCY = "member/login";
    const val LOGOUT_AGENCY = "member/logout";
    const val FORGOT_PASSWORD_AGENCY = "member/reset_password";
    const val REGISTER_AGENCY = "member/register";
    const val HOME_AGENCY = "system/homepage";
    const val CATEGORY_AGENCY = "system/category";
    const val CATEGORY_TYPE_AGENCY = "system/category_type";
    const val HISTORY_AGENCY = "history/payment";
    const val HISTORY_DETAIL_AGENCY = "history/payment_details";
    const val HISTORY_SELL_AGENCY = "agency/history/sales";
    const val HISTORY_SELL_DETAIL_AGENCY = "agency/history/sales_details";
    const val PROFILE_AGENCY = "member/profile";
    const val NEWS_AGENCY = "system/list_news";
    const val CONTACT_AGENCY = "system/contact";
    const val NOTIFICATIONS_AGENCY = "agency/notifications";

    const val STORE_AGENCY = "agency/category";
    const val STORE_CATEGORY_AGENCY = "agency/category_type";
    const val STORE_CATEGORY_CREATE_AGENCY = "agency/category_type/create";
    const val STORE_CATEGORY_DELETE_AGENCY = "agency/category_type/delete";
    const val STORE_CATEGORY_EDIT_AGENCY = "agency/category_type/edit";
    const val STORE_SEARCH_PRODUCT_AGENCY = "agency/products";
    const val STORE_PRODUCT_BEST_SELLER_AGENCY = "agency/product/bestseller";
    const val STORE_PRODUCT_HIDE_SHOW_AGENCY = "agency/product/change_of_status";
    const val STORE_PRODUCT_DELETE_AGENCY = "agency/product/delete";
    const val STORE_PRODUCT_CREATE_AGENCY = "/agency/products/create";
    const val STORE_PRODUCT_EDIT_AGENCY = "/agency/products/edit/";
    const val STORE_SEARCH_AGENCY = "agency/search";

    const val LOGIN_CUSTOMER = "customer/login";
    const val REGISTER_CUSTOMER = "customer/register";
    const val FORGOT_PASSWORD_CUSTOMER = "customer/reset_password";
    const val LOGOUT_CUSTOMER = "customer/logout";
    const val HOME_CUSTOMER = "customer/homepage";
    const val CATEGORY_CUSTOMER = "customer/category";
    const val CATEGORY_TYPE_CUSTOMER = "customer/category_type";
    const val HISTORY_CUSTOMER = "customer/history/payment";
    const val HISTORY_DETAIL_CUSTOMER = "customer/history/payment_details";
    const val PROFILE_CUSTOMER = "customer/profile";
    const val CONTACT_CUSTOMER = "agency/contact";

    const val PROVINCES = "system/provinces";
    const val PROVINCES_PHARMACY = "system/provinces/agency_list";
    const val SEARCH_PRODUCT = "product/index";
    const val SEARCH_AUTOCOMPLETE = "search";
    const val CART = "cart/index";
    const val CART_UPDATE = "cart/update";
    const val PAYMENT = "cart/payment";
    const val PAYMENT_VOUCHER_LIST = "cart/list_voucher";
    const val PAYMENT_DISCOUNT = "cart/discount";
}