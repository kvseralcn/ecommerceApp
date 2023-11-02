package com.pixelark.capstoneproject.ui.home.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.adapter.CategoryAdapter
import com.pixelark.capstoneproject.adapter.CategoryClickListener
import com.pixelark.capstoneproject.adapter.FavoriteProductClickListener
import com.pixelark.capstoneproject.adapter.ProductAdapter
import com.pixelark.capstoneproject.adapter.SaleProductAdapter
import com.pixelark.capstoneproject.adapter.SaleProductClickListener
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.CampaignType
import com.pixelark.capstoneproject.core.data.CampaignModel
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.CustomCampaignDialogBinding
import com.pixelark.capstoneproject.databinding.FragmentHomeBinding
import com.pixelark.capstoneproject.ui.home.domain.HomeViewModel
import com.pixelark.capstoneproject.util.MoshiHelper
import com.pixelark.capstoneproject.util.PreferencesHelper
import com.squareup.moshi.Types
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    private lateinit var saleProductAdapter: SaleProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    override fun onFragmentStarted() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // TODO: pop up çıkar çıkmak istediğnie emin misini diye
            }
        })
        binding.fragmentHomeRvSaleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeRvCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeRvProductRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.getSaleProducts()
        viewModel.saleProductsData.observe(this) { response ->
            setSaleProductAdapter(response.products)
        }

        viewModel.getCategories()
        viewModel.categoriesData.observe(this) { response ->
            setCategoryAdapter(response.categories)
        }

        viewModel.getProducts()
        viewModel.productsData.observe(this) { response ->
            setProductAdapter(response.products)
        }
        fetchRemoteConfig()

        binding.fragmentHomeBtnUser.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment())
        }
    }

    private fun fetchRemoteConfig() {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { configTask ->
            if (configTask.isSuccessful) {
                handleBannerData(firebaseRemoteConfig.getString("campaigns")) // TODO: const
            }
        }
    }

    private fun handleBannerData(campaignData: String) {
        if (campaignData.isEmpty())
            return

        val lastBannerDateKey = "banner_date" // TODO: constants
        val dateFormat =
            SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US) // TODO: constants
        val preferencesHelper = PreferencesHelper(requireContext())

        val lastBannerDate = preferencesHelper.getString(lastBannerDateKey)
        val currentDate = Date()

        if (lastBannerDate.isEmpty()) {
            // If lastBannerDate is not set, show the banner
            preferencesHelper.putString(lastBannerDateKey, currentDate.toString())
            showBanner(campaignData)
        } else {
            val lastBannerDateTime = dateFormat.parse(lastBannerDate)
            val timeDifference = currentDate.time - lastBannerDateTime.time
            val hoursDifference = timeDifference / (1000 * 60 * 60)
            val secondsDifference = timeDifference / 1000

            //  if (hoursDifference >= 24) {
            //      // If it has been more than 24 hours, show the banner and update lastBannerDate
            //      preferencesHelper.putString(lastBannerDateKey, currentDate.toString())
            //      showBanner(campaignData)
            //  } else {
            //      Log.i("XXX", "Not time yet.")
            //  }
            //TODO bu tarihte gösterildi mi kontrolü yap
            if (secondsDifference >= 30) { // TODO: constants
                // If it has been more than 24 hours, show the banner and update lastBannerDate
                preferencesHelper.putString(lastBannerDateKey, currentDate.toString())
                showBanner(campaignData)
            } else {
                Log.i("XXX", "Not time yet. -> $secondsDifference") // TODO: log tag
            }
        }
    }

    private fun showBanner(campaignData: String) {
        try {
            val moshiHelper = MoshiHelper.getInstance()
            val listType =
                Types.newParameterizedType(List::class.java, CampaignModel::class.java)
            val campaignList: List<CampaignModel> =
                moshiHelper.moshiFromJson(campaignData, listType)
            val banner = campaignList.single { it.type == CampaignType.Banner.value }

            val dialogBinding = CustomCampaignDialogBinding.inflate(layoutInflater)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding.root)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val window = myDialog.window
            val layoutParams = window?.attributes
            layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
            window?.attributes = layoutParams
            myDialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            myDialog.window!!.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.bottom_sheet_background_tint
                )
            )
            myDialog.window!!.setGravity(Gravity.CENTER)
            Glide.with(this)
                .load(banner.imageUrl)
                .into(dialogBinding.customCampaignDialogIvCampaign)
            myDialog.show()
            dialogBinding.customCampaignDialogBtnOk.setOnClickListener {
                myDialog.dismiss()
            }
        } catch (e: Exception) {
            Log.e("XXX", e.toString())
        }
    }

    private fun setSaleProductAdapter(productList: List<ProductModel>) {
        saleProductAdapter =
            SaleProductAdapter(productList,
                object : SaleProductClickListener {
                    override fun onClick(selectedProduct: ProductModel) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                                selectedProduct.id
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvSaleRecyclerView.adapter = saleProductAdapter
    }

    private fun setProductAdapter(productList: List<ProductModel>) {
        productAdapter =
            ProductAdapter(productList,
                object : FavoriteProductClickListener {
                    override fun onClick(selectedProduct: ProductModel) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                                selectedProduct.id
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvProductRecyclerView.adapter = productAdapter
    }

    private fun setCategoryAdapter(productList: List<String>) {
        categoryAdapter =
            CategoryAdapter(productList,
                object : CategoryClickListener {
                    override fun onCategoryClick(selectedProduct: String) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductsByCategoryFragment(
                                selectedProduct
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvCategoryRecyclerView.adapter = categoryAdapter
    }
}