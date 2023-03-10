package com.home.cinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.home.cinema.R
import com.home.cinema.databinding.AccountItemCollectionBinding
import com.home.cinema.domain.models.entities.collections.AccountCollection

class AccountCollectionsAdapter(
    private val onItemClick: (AccountCollection) -> Unit
) :
    ListAdapter<AccountCollection, AccountCollectionsAdapter.AccountCollectionsItemViewHolder>(
        AccountCollectionsDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountCollectionsItemViewHolder {
        return AccountCollectionsItemViewHolder(
            AccountItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AccountCollectionsItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {

            root.setOnClickListener {
                onItemClick(item)
            }

            when (position) {
                0 -> {
                    removeCollectionButton.visibility = View.GONE
                    iconImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            iconImageView.resources,
                            R.drawable.account_item_collection_icon_favorite,
                            iconImageView.context.theme
                        )
                    )
                    nameCollectionTextView.text =
                        nameCollectionTextView.resources.getString(R.string.account_favorite_collection_name)
                }
                1 -> {
                    removeCollectionButton.visibility = View.GONE
                    iconImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            iconImageView.resources,
                            R.drawable.account_item_collection_icon_will_watch,
                            iconImageView.context.theme
                        )
                    )
                    nameCollectionTextView.text =
                        nameCollectionTextView.resources.getString(R.string.account_will_watch_collection_name)
                }
                else -> {
                    removeCollectionButton.visibility = View.VISIBLE
                    iconImageView.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            iconImageView.resources,
                            R.drawable.account_item_collection_icon_custom,
                            iconImageView.context.theme
                        )
                    )
                    nameCollectionTextView.text = item.name
                }
            }

            moviesCounterCollectionTextView.text = item.count.toString()
        }
    }

    inner class AccountCollectionsItemViewHolder(val binding: AccountItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class AccountCollectionsDiffUtilCallback : DiffUtil.ItemCallback<AccountCollection>() {

        override fun areItemsTheSame(
            oldItem: AccountCollection,
            newItem: AccountCollection
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: AccountCollection,
            newItem: AccountCollection
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.count == newItem.count
        }
    }
}