package com.droid.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droid.camera.adapters.ImageAdapter
import com.droid.camera.objects.UriStrings.mutableList


class MainActivity : AppCompatActivity() {

    lateinit var rvImages : RecyclerView
    lateinit var btnClick : Button
    lateinit var btnClear : Button
    lateinit var imgeAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvImages = findViewById(R.id.rvImages)
        btnClick = findViewById(R.id.btnClick)
        btnClear = findViewById(R.id.btnClear)

        requestPermission()

        var imgUri: Uri? = intent.data
        Log.d("filepathimg","$imgUri")
        if (imgUri != null) {

                
                checkListSize(imgUri)


        }

        setUpRecyclerView()


        btnClick.setOnClickListener {
//                if (hasWriteStoragePermission() && hasCameraAccessPermission()){
                    if (mutableList.size <= 3) {
                        val intent = Intent(this, CameraActivity::class.java)
                        startActivity(intent)
                    } else {
                        val builder = AlertDialog.Builder(this)
                        //set title for alert dialog
                        builder.setTitle("Storage Full")
                        //set message for alert dialog
                        builder.setMessage("Storage is Full. Want to clear all and save again ?")
                        builder.setPositiveButton("Yes") { dialogInterface, which ->
                            mutableList.clear()
                            setUpRecyclerView()
                        }
                        builder.setNegativeButton("No") { dialogInterface, which ->
                            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG)
                                .show()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        // Set other dialog properties
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
//                }



//            }else{
//                Toast.makeText(this,"Permission not  Allowed",Toast.LENGTH_LONG).show()
//            }
        }


        btnClear.setOnClickListener {
        mutableList.clear()
            setUpRecyclerView()
        }




    }

    private fun checkListSize(imgUri : Uri) {
        if (mutableList.size <= 3){
            mutableList.add(imgUri)
        }
    }

    private fun setUpRecyclerView() {
            rvImages.apply {
                imgeAdapter  = ImageAdapter(context , mutableList)
                adapter = imgeAdapter
                imgeAdapter.notifyDataSetChanged()
                layoutManager = LinearLayoutManager(context)
                Log.d("RV","In Setup Recyclerview")

            }
    }

    private fun hasWriteStoragePermission() : Boolean = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasCameraAccessPermission() : Boolean = ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(){
        var permissionsRequest = mutableListOf<String>()
        if (!hasCameraAccessPermission()){
            permissionsRequest.add(Manifest.permission.CAMERA)
        }
        if (!hasWriteStoragePermission()){
            permissionsRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionsRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this , permissionsRequest.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==0 && grantResults.isNotEmpty()){
                for (i in grantResults.indices){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d("Permissions", permissions[i])
                    }
                }
        }
    }


    companion object {
        const val PERMISSION_REQUEST = 10

    }


}