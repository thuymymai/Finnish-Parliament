package com.example.parliamentmembers.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.CommentAdapter
import com.example.parliamentmembers.databinding.MembersInfoFragmentBinding
import com.example.parliamentmembers.repositories.MemberRepo
import com.example.parliamentmembers.roomdb.Comment
import com.example.parliamentmembers.roomdb.MemberDB
import com.example.parliamentmembers.roomdb.Rating
import com.example.parliamentmembers.viewmodels.MemberDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MembersInfoFragment : Fragment() {
    private lateinit var binding: MembersInfoFragmentBinding
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Default)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.members_info_fragment, container, false
        )

        val memberDetailViewModel =
            ViewModelProvider(
                this
            ).get(MemberDetailViewModel::class.java)

        binding.memberDetailViewModel = memberDetailViewModel

        //return data to ViewModel
        val memberId = MembersInfoFragmentArgs.fromBundle(requireArguments()).personNumber
        Log.i("mimi", memberId.toString())
        memberDetailViewModel.setPersonalNum(memberId)
        memberDetailViewModel.getRatingByMem(memberId)
        memberDetailViewModel.getCommentByNum(memberId)
        memberDetailViewModel.getRatingAvg(memberId)

        memberDetailViewModel.member.observe(
            viewLifecycleOwner, { member ->
                member.let {
                    binding.apply {
                        activity?.let { it1 ->
                            Glide.with(it1)
                                .load("https://avoindata.eduskunta.fi/" + member.picture)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(partyLogo)
                        }
                        binding.ratingBar1.setOnRatingBarChangeListener { p0, _, _ ->
                            coroutineScope.launch {
                                //val rating = Rating(member.personNumber, p0.rating)
                                context?.let { it1 ->
                                    MemberDB.getInstance(it1).ratingDao.insert(
                                        Rating(
                                            personNum = member.personNumber,
                                            numberOfStars = p0.rating
                                        )
                                    )
                                }

                            }
                        }

                        binding.submitButton.setOnClickListener {
                            coroutineScope.launch {
                                context?.let { it1 ->
                                    MemberDB.getInstance(it1).commentDao.insert(
                                        Comment(
                                            personNum = member.personNumber,
                                            comment = binding.commentEditText.text.toString()
                                        )
                                    )
                                }
                            }
                            binding.commentEditText.text = null
                        }
                        name.text = getString(R.string.name, member.first, member.last)
                        if (member != null) {
                            age.text = getString(R.string.age, 2021 - member.bornYear)
                        }
                        constituency.text = getString(R.string.constituency, member.constituency)
                        party.text = getString(R.string.party, member.party)


                        memberDetailViewModel.ratingAvg.observe(
                            viewLifecycleOwner,
                            Observer { rating ->
                                rating.let {
                                    if(rating != null){ ratingBar1.rating = rating
                                    binding.ratingText.text = rating.toString()}
                                }
                            }
                        )
                        val commentAdapter = CommentAdapter()
                        binding.listComments.adapter = commentAdapter
                        memberDetailViewModel.comment.observe(
                            viewLifecycleOwner,
                            Observer { comment ->
                                comment.let {
                                    if (comment != null)
                                        commentAdapter.submitList(it)
                                }
                            })

                    }
                }
            })



        return binding.root
    }

}

/*private fun randomMember() {
val random = Random().nextInt(ParliamentMembersData.members.size)
binding.apply {
    //check if this random member is a minister
    if (ParliamentMembersData.members[random].minister) minister.text =
        getString(R.string.minister) else minister.text = getString(R.string.member)
    name.text = getString(
        R.string.name,
        ParliamentMembersData.members[random].first,
        ParliamentMembersData.members[random].last
    )
    age.text =
        getString(R.string.age, 2021 - ParliamentMembersData.members[random].bornYear)
    constituency.text = getString(
        R.string.constituency,
        ParliamentMembersData.members[random].constituency
    )
    party.text = getString(R.string.party, ParliamentMembersData.members[random].party)
    invalidateAll()

    //set logo for parties
    when (ParliamentMembersData.members[random].party) {
        "sd" -> partyLogo.setImageResource(R.drawable.sd)
        "ps" -> partyLogo.setImageResource(R.drawable.ps)
        "kd" -> partyLogo.setImageResource(R.drawable.kd)
        "kesk" -> partyLogo.setImageResource(R.drawable.kesk)
        "kok" -> partyLogo.setImageResource(R.drawable.kok)
        "r" -> partyLogo.setImageResource(R.drawable.r)
        "vas" -> partyLogo.setImageResource(R.drawable.vas)
        "vihr" -> partyLogo.setImageResource(R.drawable.vihr)
        "liik" -> partyLogo.setImageResource(R.drawable.liik)
    }

}
}*/

