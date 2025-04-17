package com.example.ig_connect.adapters



import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ig_connect.databinding.ItemContainerReceivedMessageBinding
import com.example.ig_connect.databinding.ItemContainerSentMessageBinding
import com.example.ig_connect.models_chat.ChatMessage

class ChatAdapter(
    private val chatMessages: List<ChatMessage>,
    private var receiverProfileImage: Bitmap?,
    private val senderId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    fun setReceiverProfileImage(bitmap: Bitmap) {
        receiverProfileImage = bitmap
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            SentMessageViewHolder(
                ItemContainerSentMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ReceiverMessageViewHolder(
                ItemContainerReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            (holder as SentMessageViewHolder).setData(chatMessages[position])
        } else {
            (holder as ReceiverMessageViewHolder).setData(chatMessages[position], receiverProfileImage)
        }
    }

    override fun getItemCount(): Int = chatMessages.size

    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position].senderId == senderId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    class SentMessageViewHolder(private val binding: ItemContainerSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.dateTime
        }
    }

    class ReceiverMessageViewHolder(private val binding: ItemContainerReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(chatMessage: ChatMessage, receiverProfileImage: Bitmap?) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.dateTime
            receiverProfileImage?.let {
                binding.imageProfile.setImageBitmap(it)
            }
        }
    }
}
