package com.freshervnc.pharmacycounter.presentation.ui.manager.categorytype

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.BottomDialogAddToCartBinding
import com.freshervnc.pharmacycounter.databinding.DialogCategoryBinding
import com.freshervnc.pharmacycounter.databinding.FragmentStoreCategoryTypeBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.domain.response.category.CategoryTypeResponse
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestDeleteProductResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestCreateCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestDeleteCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestUpdateCategoryResponse
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory
import com.freshervnc.pharmacycounter.presentation.ui.cart.CartFragment
import com.freshervnc.pharmacycounter.presentation.ui.category.adapter.CategoryTypeAdapter
import com.freshervnc.pharmacycounter.presentation.ui.category.adapter.ChildCategoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel.CategoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.manager.categorytype.adapter.StoreCategoryTypeAdapter
import com.freshervnc.pharmacycounter.presentation.ui.manager.product.ProductStoreFragment
import com.freshervnc.pharmacycounter.presentation.ui.product.ProductFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class StoreCategoryTypeFragment : Fragment(), OnClickItemCategory {
    private lateinit var binding: FragmentStoreCategoryTypeBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var storeCategoryTypeAdapter: StoreCategoryTypeAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    private var checkCategoryType = ""
    var myInt = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreCategoryTypeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
        getData()
    }

    private fun init() {
        (activity as MainActivity).hideBottomNav()
        categoryViewModel = ViewModelProvider(
            this,
            CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
        )[CategoryViewModel::class.java]
        storeCategoryTypeAdapter = StoreCategoryTypeAdapter(this)
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun initVariable() {
        binding.storeCategoryTypeRcView.setHasFixedSize(true)
        binding.storeCategoryTypeRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.storeCategoryTypeRcView.adapter = storeCategoryTypeAdapter
        getData()
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.storeCategoryTypeRcView)
    }

    private fun getData() {
        val b = arguments
        if (b != null) {
            myInt = b.getString("key_category")!!
            val categoryTypeTemp = RequestCategoryTypeResponse(myInt.toString())
            checkCategoryType = myInt.toString()
            categoryViewModel.getStoreCategoryType(
                "Bearer " + mySharedPrefer.token,
                categoryTypeTemp
            )
                .observe(viewLifecycleOwner) { it ->
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> {
                                resources.data?.let { item ->
                                    storeCategoryTypeAdapter.setList(item.response.data)
                                }
                            }

                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                }
        } else {
            Log.e("log bundle o day", "null")
        }
    }

    override fun onClickItem(item: Category , key : String) {
        val args = Bundle()
        args.putInt("key_product", item.value)
        args.putString("key_category", checkCategoryType)
        val newFragment: ProductStoreFragment = ProductStoreFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_store_category_type, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.icon_toolbar_store_category) {
            val view = DialogCategoryBinding.inflate(layoutInflater, null, false)
            val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
            dialog.setContentView(view.root)
            dialog.show()
            view.dialogBottomStoreTitle.text = "Thêm mới danh mục"
            view.dialogBottomStoreBtnSave.setOnClickListener {
                val categoryTypeTemp = RequestCreateCategoryResponse(
                    myInt,
                    view.dialogBottomStoreEdCategory.text.toString()
                )
                categoryViewModel.getCreateStoreCategoryType(
                    "Bearer " + mySharedPrefer.token,
                    categoryTypeTemp
                ).observe(viewLifecycleOwner,
                    Observer {
                        it?.let { resources ->
                            when (resources.status) {
                                Status.SUCCESS -> {
                                    getData()
                                    storeCategoryTypeAdapter.notifyDataSetChanged()
                                    dialog.dismiss()
                                }

                                Status.LOADING -> {

                                }

                                Status.ERROR -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                    })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            private var p: Paint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.FILL
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    val item = storeCategoryTypeAdapter.getItem(viewHolder.adapterPosition)
                    val alertDialog = AlertDialog.Builder(context)
                        .setMessage(getString(R.string.str_xoa_san_pham) + item.name)
                        .setPositiveButton("Đồng ý") { dialog, which ->
                            if (item.size > 0) {
                                Snackbar.make(requireView(), "${item.name} có chứa sản phẩm nên không thể xóa", 3000)
                                    .show()
                                storeCategoryTypeAdapter.notifyDataSetChanged()
                            }else{
                                categoryViewModel.getDeleteStoreCategoryType(
                                    "Bearer " + mySharedPrefer.token,
                                    RequestDeleteCategoryResponse(myInt, item.value)
                                ).observe(viewLifecycleOwner,
                                    Observer {
                                        it?.let { resources ->
                                            when (resources.status) {
                                                Status.SUCCESS -> {
                                                    getData()
                                                    storeCategoryTypeAdapter.notifyDataSetChanged()
                                                    dialog.dismiss()
                                                }

                                                Status.ERROR -> {
                                                    dialog.dismiss()
                                                }

                                                Status.LOADING -> {}
                                            }
                                        }
                                    })
                                storeCategoryTypeAdapter.notifyDataSetChanged()
                                dialog.dismiss()
                            }
                        }

                        .setNegativeButton("Hủy bỏ") { dialog, which ->
                            dialog.dismiss()
                            storeCategoryTypeAdapter.notifyDataSetChanged()
                        }
                        .setCancelable(false)
                    alertDialog.show()
                } else if (direction == ItemTouchHelper.RIGHT) {
                    val item = storeCategoryTypeAdapter.getItem(viewHolder.adapterPosition)
                    Log.e("itemAdapter", "" + item)
                    dialog(item, position)

                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX < 0) {
                        p.color = Color.RED
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)
                        val icon = getBitmapFromVectorDrawable(
                            context!!,
                            R.drawable.baseline_delete_forever_24
                        )
                        if (icon != null) {
                            val margin = (dX / 5 - width) / 2
                            val iconDest = RectF(
                                itemView.right.toFloat() + margin,
                                itemView.top.toFloat() + width,
                                itemView.right.toFloat() + (margin + width),
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, iconDest, p)
                        } else {
                            Log.e("Failed_icon", "Failed to load icon for add action.")
                        }
                    }
                    if (dX > 0) {
                        p.color = Color.BLUE
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            itemView.left.toFloat() + dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)
                        val icon = getBitmapFromVectorDrawable(
                            context!!,
                            R.drawable.baseline_edit_note_24
                        )
                        if (icon != null) {
                            val margin = (dX / 5 - width) / 2
                            val iconDest = RectF(
                                margin,
                                itemView.top.toFloat() + width,
                                margin + width,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, iconDest, p)
                        } else {
                            Log.e("Failed_icon", "Failed to load icon for add action.")
                        }
                    }
                } else {
                    c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX / 5,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

    private fun dialog(
        item: Category, position: Int
    ) {
        val view = DialogCategoryBinding.inflate(layoutInflater, null, false)
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(view.root)
        dialog.show()

        view.dialogBottomStoreTitle.text = "Cập nhật danh mục"
        view.dialogBottomStoreBtnSave.text = "Cập nhật"
        view.dialogBottomStoreEdCategory.setText(item.name)
        view.dialogBottomStoreBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        view.dialogBottomStoreBtnSave.setOnClickListener {
            val requestCategoryTemp = RequestUpdateCategoryResponse(
                myInt,
                item.value,
                view.dialogBottomStoreEdCategory.text.toString()
            )
            categoryViewModel.getUpdateStoreCategoryType(
                "Bearer " + mySharedPrefer.token,
                requestCategoryTemp
            ).observe(viewLifecycleOwner,
                Observer {
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> {
                                getData()
                                storeCategoryTypeAdapter.notifyDataSetChanged()
                                dialog.dismiss()
                            }

                            Status.ERROR -> {

                            }

                            Status.LOADING -> {

                            }
                        }
                    }
                })
        }

    }

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}