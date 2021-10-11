package com.example.parliamentmembers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.CommentAdapter
import com.example.parliamentmembers.databinding.MembersInfoFragmentBinding
import com.example.parliamentmembers.viewmodels.MemberDetailViewModel
import timber.log.Timber

/*Name: My Mai, student ID: 2012197
This Fragment observes the changes in the LiveData object of MemberDetailViewModel
From that update information of each member including rating and comment for each.
Date: 06/10/2021
*/

class MembersInfoFragment : Fragment() {

    private lateinit var binding: MembersInfoFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.members_info_fragment, container, false
        )

        //call instance of view model
        val memberDetailViewModel =
            ViewModelProvider(
                this
            ).get(MemberDetailViewModel::class.java)

        binding.memberDetailViewModel = memberDetailViewModel

        //get data from bundle sent from MembersFragment via navigation
        val memberId = MembersInfoFragmentArgs.fromBundle(requireArguments()).personNumber
        Timber.i(memberId.toString())

        //pass data to view model
        memberDetailViewModel.setPersonalNum(memberId)
        memberDetailViewModel.getCommentByNum(memberId)
        memberDetailViewModel.getRatingAvg(memberId)


        //bind the picture of PM with Glide
        memberDetailViewModel.member.observe(
            viewLifecycleOwner, { member ->
                member.let {
                    activity?.let { it1 ->
                        Glide.with(it1)
                            .load("https://avoindata.eduskunta.fi/" + member.picture)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .circleCrop()
                            .into(binding.memberImage)
                    }
                }
            })

        //Add rating to room database on RatingBar change
        binding.ratingBar1.setOnRatingBarChangeListener { p0, _, _ ->
            memberDetailViewModel.insertRating(memberId, p0.rating)
        }


        //Add comment to room data base with person number of the member accordingly
        binding.submitButton.setOnClickListener {
            memberDetailViewModel.insertComment(
                memberId,
                binding.commentEditText.text.toString()
            )

            //hide keyboard and make edit text 's text null after submit comment
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken, 0
            )
            binding.commentEditText.text = null
        }

        //bind name, constituency, age and party info of each member
        memberDetailViewModel.member.observe(
            viewLifecycleOwner, { member ->
                member.let {
                    binding.apply {
                        //check if this member is minister or not
                        if (member.minister) minister.text =
                            getString(R.string.minister) else minister.text =
                            getString(R.string.member)
                        name.text = getString(R.string.name, member.first, member.last)
                        if (member != null) {
                            age.text = getString(R.string.age, 2021 - member.bornYear)
                        }
                        constituency.text = getString(R.string.constituency, member.constituency)
                        party.text = getString(R.string.party, member.party)
                        invalidateAll()
                    }
                }
            })

        //bind rating average to textview
        memberDetailViewModel.ratingAvg.observe(
            viewLifecycleOwner,
            { rating ->
                rating.let {
                    if (rating != null) {
                        binding.ratingBar1.rating = rating
                        binding.ratingText.text =
                            String.format("%.1f", rating)
                    }
                }
            })

        //update list of comments to recyclerview
        val commentAdapter = CommentAdapter()
        binding.listComments.adapter = commentAdapter
        memberDetailViewModel.comment.observe(
            viewLifecycleOwner,
            { comment ->
                comment.let {
                    if (comment != null)
                        commentAdapter.submitList(it)
                }
            })

        return binding.root
    }
}

