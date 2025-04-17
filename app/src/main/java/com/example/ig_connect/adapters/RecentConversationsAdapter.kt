package com.example.ig_connect.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ig_connect.databinding.ItemContainerRecentConversionBinding
import com.example.ig_connect.Listeners.ConversionListener
import com.example.ig_connect.models_chat.ChatMessage
import com.example.ig_connect.models_chat.User

class RecentConversationsAdapter(
    private val chatMessages: List<ChatMessage>,
    private val conversionListener: ConversionListener
) : RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        val binding = ItemContainerRecentConversionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ConversionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.setData(chatMessages[position])
    }

    override fun getItemCount(): Int = chatMessages.size

    inner class ConversionViewHolder(private val binding: ItemContainerRecentConversionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(chatMessage: ChatMessage) {
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage))
            binding.textName.text = chatMessage.conversionName
            binding.textRecentMessage.text = chatMessage.message
            binding.root.setOnClickListener {
                val user = User().apply {
                    id = chatMessage.coversionId
                    name = chatMessage.conversionName
                    image = chatMessage.conversionImage
                }
                conversionListener.onConversionClicked(user)
            }
        }
    }

    private fun getConversionImage(encodedImage: String?): Bitmap? {
        Log.d("ImageDebug", "Encoded Image: $encodedImage")

        if (encodedImage.isNullOrEmpty()) {
            // You can return a default Bitmap here if you want
            return null
        }
        val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
