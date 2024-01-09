package com.ouo.wan_android_kt.official

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ouo.wan_android_kt.R

class OfficialAccountsFragment : Fragment() {

    companion object {
        fun newInstance() = OfficialAccountsFragment()
    }

    private lateinit var viewModel: OfficialAccountsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_official_accounts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfficialAccountsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}