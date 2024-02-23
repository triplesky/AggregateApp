/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ll.aggregate.R
import com.ll.aggregate.listscreen.Frame
import com.ll.aggregate.listscreen.ImageBean
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


/**
 * Shows a static leaderboard with multiple users.
 */
class Leaderboard : Fragment() {

    var image_list = mutableListOf<Bitmap?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        generateHeaderImgList()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        val viewAdapter = MyAdapter(Array(image_list.size) { "Person ${it + 1}" },image_list)

        view.findViewById<RecyclerView>(R.id.leaderboard_list).run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
        return view
    }



    private fun getJson( fileName: String?): String? {
        val stringBuilder = StringBuilder()
        //获得assets资源管理器
        context?.let {
            val assetManager: AssetManager = it.assets
            //使用IO流读取json文件内容
            try {
                val bufferedReader = BufferedReader(InputStreamReader(
                    assetManager.open(fileName!!), "utf-8"))
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }
        return null

    }

    fun generateHeaderImgList(){
        val myjson = getJson("ui.json")
        var imageBean = Gson().fromJson(myjson, ImageBean::class.java)



        var inputStream: InputStream? = null
        try {
            // 获取 Assets 文件的输入流
            inputStream = context?.assets?.open("ui.png")

            inputStream?.let {
                var bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false)

                for (image in imageBean.frames){
                    val bitmap = bitmapRegionDecoder?.decodeRegion(
                        Rect(image.frame.x, image.frame.y, image.frame.x +image.frame.w, image.frame.y +image.frame.h),  //解码区域
                        null) //解码选项 BitmapFactory.Options 类型
                    Log.i("lldebug bitmap", bitmap.toString())
                    image_list.add(bitmap)
                }

            }


        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

}




class MyAdapter(private val myDataset: Array<String>, val img_list:MutableList<Bitmap?>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)


        return ViewHolder(itemView)
    }



    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]

        img_list[position]?.let {
            holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageBitmap(it)
        }


        holder.item.setOnClickListener {
            val bundle = bundleOf(USERNAME_KEY to myDataset[position])

            holder.item.findNavController().navigate(
                    R.id.action_leaderboard_to_userProfile,
                bundle)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    companion object {
        const val USERNAME_KEY = "userName"
    }
}

private val listOfAvatars = listOf(
    R.drawable.avatar_1_raster,
    R.drawable.avatar_2_raster,
    R.drawable.avatar_3_raster,
    R.drawable.avatar_4_raster,
    R.drawable.avatar_5_raster,
    R.drawable.avatar_6_raster
)
