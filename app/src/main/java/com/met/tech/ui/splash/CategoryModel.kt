package com.met.tech.ui.splash

data class CategoryModel(
    var category_id: String,
    var category_name: String,
    var category_item: List<ItemsModel>
)

data class ItemsModel(
    var product_id: String,
    var product_suk_id: String,
    var product_name: String,
    var category_id: String,
    var description: String,
    var product_image: String,
    var price: String,
    var vendor_id: String,
    var status: String,
    var created_at: String,
    var updated_at: String,
    var category_name: String,
    var vendor_name: String
)


