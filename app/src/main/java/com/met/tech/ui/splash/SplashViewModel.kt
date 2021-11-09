package com.met.tech.ui.splash


import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.met.tech.base.BaseViewModel
import com.met.tech.base.ViewState
import com.met.tech.util.toLiveData
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _getCategory = MutableLiveData<List<CategoryModel>>()
    val getCategory = _getCategory.toLiveData()

    private val _postError = MutableLiveData<String>()
    val postError = _postError.toLiveData()
    val url = "https://devfitser.com/PinkDelivery/dev/api/product/get"


    fun getCategory(queue: RequestQueue) {

        launch {
            val stringReq: StringRequest =
                object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        _viewState.postValue(ViewState.Loading)
                        val reponse_obj = response.toString()
                        val json_object = JSONObject(reponse_obj);
                        val status_object = json_object.getJSONObject("status")
                        val result_obj = json_object.getJSONObject("result")
                        val error_code = status_object.getString("error_code")
                        if (error_code.equals("0")) {
                            val category_array = result_obj.getJSONArray("data");
                            val category_data = ArrayList<CategoryModel>();

                            category_array.let {
                                for (i in 0 until category_array.length()) {
                                    val itemss = category_array.getJSONObject(i)
                                    val items_data = ArrayList<ItemsModel>();
                                    val itemarray: JSONArray? = itemss.getJSONArray("items");
                                    itemarray?.let {
                                        for (count in 0 until itemarray.length()) {
                                            val item = itemarray.getJSONObject(count)
                                            items_data.add(
                                                ItemsModel(
                                                    item.getString("product_id"),
                                                    item.getString("product_suk_id"),
                                                    item.getString("product_name"),
                                                    item.getString("category_id"),
                                                    item.getString("description"),
                                                    item.getString("product_image"),
                                                    item.getString("price"),
                                                    item.getString("vendor_id"),
                                                    item.getString("status"),
                                                    item.getString("created_at"),
                                                    item.getString("updated_at"),
                                                    item.getString("category_name"),
                                                    item.getString("vendor_name")
                                                )
                                            )
                                        }
                                    }

                                    category_data.add(
                                        CategoryModel(
                                            itemss.getString("cat_id"),
                                            itemss.getString("cat_name"),
                                            items_data
                                        )
                                    )
                                }
                            }
                            _getCategory.postValue(category_data)
                            _viewState.postValue(ViewState.Success())
                        }
                    },
                    Response.ErrorListener { error ->
                        _postError.postValue(error.toString())
                        _viewState.postValue(ViewState.Error())
                    }) {
                    override fun getBody(): ByteArray {
                        val params = HashMap<String, String>()
                        params.put("store_id", "3")
                        params.put("u_id", "")
                        params.put("access_type", "1")
                        params.put("source", "mob")
                        return JSONObject(params as Map<*, *>).toString().toByteArray()
                    }
                }
            queue.add(stringReq)
        }
    }

}