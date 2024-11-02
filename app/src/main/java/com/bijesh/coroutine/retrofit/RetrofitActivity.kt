package com.bijesh.coroutine.retrofit

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bijesh.coroutine.R
import com.bijesh.coroutine.retrofit.apis.RetrofitClient
import com.bijesh.coroutine.retrofit.models.LocalUser
import com.bijesh.coroutine.retrofit.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        initRetrofit()
    }

    private fun initRetrofit(){
//        getLocalUser()
//        getAllUsers()
//        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//        }
//        downloadPdf()
        downloadPdfFileRx(this)
    }

    /**
     * POC for downloading the pdf
     */
    fun downloadPdfFileRx(context: Context) {
        RetrofitClient.localApi.downloadPDFRx()
            .map { responseBody ->
                saveFileToDownloads(context, responseBody, "downloaded_rx_file.pdf")
                responseBody
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responseBody ->
//                saveFileToDownloads(context, responseBody, "downloaded_rx_file.pdf")
                Log.d("Saved","File saved")
            }, { error ->
                Log.e("DownloadError", "Error downloading file: ${error.message}")
            })
    }

    private fun downloadPdf(){
        RetrofitClient.localApi.downloadPDF().enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    response?.let {
                        response.body()?.let { it1 ->
//                            saveFileToDisk(
//                                this@RetrofitActivity.applicationContext,
//                                it1,
//                                "downloaded_file.pdf"
//                            )
                            saveFileToDownloads(this@RetrofitActivity.applicationContext,
                                response.body()!!,
                                "downloaded_file.pdf")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG,"${t.message}")
            }

        })
    }

    private fun saveFileToDownloads(context: Context, body: ResponseBody, fileName: String) {
        val inputStream: InputStream = body.byteStream()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 (API 29) and above: Use MediaStore API
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                var outputStream: OutputStream? = null
                try {
                    outputStream = resolver.openOutputStream(it)
                    if (outputStream != null) {
                        copyStream(inputStream, outputStream!!)
                        Log.d("SaveSuccess", "File saved to Downloads folder")
                    } else{
                        Log.e("SaveError", "Failed to open output stream")
                    }
                } catch (e: Exception) {
                    Log.e("SaveError", "Error saving file: ${e.message}")
                } finally {
                    outputStream?.close()
                }
            }
        } else {
            // Android 9 (API 28) and below: Use traditional file paths
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)

            var outputStream: OutputStream? = null
            try {
                outputStream = FileOutputStream(file)
                copyStream(inputStream, outputStream as FileOutputStream)
                Log.d("SaveSuccess", "File saved to Downloads folder: ${file.absolutePath}")
            } catch (e: Exception) {
                Log.e("SaveError", "Error saving file: ${e.message}")
            } finally {
                outputStream?.close()
            }
        }

        inputStream.close()
    }

    private fun copyStream(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(4096)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }
    }

//    private fun saveFileToDisk(context: Context, body: ResponseBody, fileName: String) {
//        try {
//            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
//            var inputStream: InputStream? = null
//            var outputStream: OutputStream? = null
//
//            try {
//                inputStream = body.byteStream()
//                outputStream = FileOutputStream(file)
//
//                val fileReader = ByteArray(4096)
//                var bytesRead: Int
//
//                while (inputStream.read(fileReader).also { bytesRead = it } != -1) {
//                    outputStream.write(fileReader, 0, bytesRead)
//                }
//
//                outputStream.flush()
//                Log.d("DownloadSuccess", "File downloaded to ${file.absolutePath}")
//            } catch (e: Exception) {
//                Log.e("SaveFileError", "Error saving file: ${e.message}")
//            } finally {
//                inputStream?.close()
//                outputStream?.close()
//            }
//        } catch (e: Exception) {
//            Log.e("FileCreationError", "Error creating file: ${e.message}")
//        }
//    }

    private fun getAllUsers(){
        RetrofitClient.localApi.getAllUsers().enqueue(object : Callback<List<LocalUser>>{
            override fun onResponse(
                call: Call<List<LocalUser>>,
                response: Response<List<LocalUser>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { users ->
                        for(user in users){
                            Log.d(TAG,"User name ${user.name} and email ${user.email}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<LocalUser>>, t: Throwable) {
                Log.e(TAG,"${t.message}")
            }

        })
    }

    private fun getLocalUser(){
        RetrofitClient.localApi.getLocalUser().enqueue(object : Callback<LocalUser> {
            override fun onResponse(call: Call<LocalUser>, response: Response<LocalUser>) {
                if(response.isSuccessful){
                    response.body()?.let { users->
                            Log.d(TAG,"${users.name}")
                    }
                }
            }

            override fun onFailure(call: Call<LocalUser>, t: Throwable) {
                Log.d(TAG,"${t.message}")
            }


        }
        )
    }

    private fun getUser(){
        RetrofitClient.localApi.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    response.body()?.let { users->
                        for (user in users){
                            Log.d(TAG,"${user.name}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d(TAG,"${t.message}")
            }
        }
        )
    }

    companion object{
        const val TAG = "RetrofitActivity"
    }
}