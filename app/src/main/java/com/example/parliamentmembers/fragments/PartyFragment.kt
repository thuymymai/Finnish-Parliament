package com.example.parliamentmembers.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.PartyAdapter
import com.example.parliamentmembers.adapters.PartyListListener
import com.example.parliamentmembers.databinding.Fragment1Binding
import com.example.parliamentmembers.viewmodels.PartyViewModel
import kotlinx.android.synthetic.main.members_info_fragment.*
import timber.log.Timber

class PartyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<Fragment1Binding>(
            inflater,
            R.layout.fragment_1, container, false
        )
        val partyViewModel =
            ViewModelProvider(
                this).get(PartyViewModel::class.java)


        binding.partyViewModel = partyViewModel

        val adapter = PartyAdapter(PartyListListener { party ->
            partyViewModel.onPartyClicked(party)
            Toast.makeText(context, party, Toast.LENGTH_LONG).show()
        })
        binding.partyList.adapter = adapter


        partyViewModel.parties.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }

        })

        partyViewModel.navigateToMemberList.observe(viewLifecycleOwner, Observer{ party ->
            party?.let {
                this.findNavController().navigate(PartyFragmentDirections.actionFragment1ToFragment2(party))
                partyViewModel.onMemberListNavigated()
            }
            //Log.i("mimi", party)
        })

        binding.lifecycleOwner = this
        return binding.root
    }

}




