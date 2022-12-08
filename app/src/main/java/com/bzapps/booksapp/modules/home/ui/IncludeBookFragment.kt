package com.bzapps.booksapp.modules.home.ui

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bzapps.booksapp.BuildConfig
import com.bzapps.booksapp.R
import com.bzapps.booksapp.databinding.FragmentIncludeBookBinding
import com.bzapps.booksapp.modules.home.viewmodel.HomeViewModel
import com.bzapps.booksapp.shared.base.BaseFragment
import com.bzapps.booksapp.shared.components.closeKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File
import java.util.*


class IncludeBookFragment : BaseFragment() {
    private var imageToUploadUri: Uri? = null
    private val CAMERA_REQUEST: Int = 100
    private val binding by lazy { FragmentIncludeBookBinding.inflate(layoutInflater) }

    private val homeViewModel by sharedViewModel<HomeViewModel>()
    companion object{
        private const val PROVIDER = ".provider"
        private const val FILE_EXTENSION = "_book.jpg"
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
    }

    override fun initView() {
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.ivPhoto.setOnClickListener {
            if (checkPermission()) {
                openCamera()
            } else {
                requestPermission()
            }
        }

        binding.btIncludeBook.setOnClickListener {
            val name = binding.etName.text.toString()
            val author = binding.etAuthor.text.toString()
            val genre = binding.etGenre.text.toString()
            val filePath = imageToUploadUri
            homeViewModel.includeBook(name, author, genre, filePath)
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(CAMERA), CAMERA_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Permissões da Camera negadas", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val randomId = UUID.randomUUID().toString()
        val directoryPhoto: String =
            requireContext().getExternalFilesDir(null).toString() + "/" + randomId + FILE_EXTENSION
        val file = File(directoryPhoto)
        imageToUploadUri = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + PROVIDER,
            file
        )
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri)
        startActivityForResult(intentCamera, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (imageToUploadUri != null) {
                setPhoto(imageToUploadUri)
            }
        }
    }

    private fun setPhoto(photo: Uri?) {
        Glide.with(requireActivity().applicationContext).load(photo).into(binding.ivPhoto)
    }

    private fun initObserver() {
        homeViewModel.loadingState.observe(::getLifecycle, ::loading)
        homeViewModel.includeBookSuccessState.observe(::getLifecycle, ::goHome)
    }

    private fun goHome(status: Boolean) {
        if (status) {
            navController.navigate(R.id.action_includeBookFragment_to_booksFragment)
        }
    }

    private fun loading(status: Boolean) {
        requireActivity().closeKeyboard(binding.root)
        loading.visibility = if (status) View.VISIBLE else View.GONE
    }

}