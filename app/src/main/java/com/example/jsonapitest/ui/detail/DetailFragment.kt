package com.example.jsonapitest.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.jsonapitest.R
import com.example.jsonapitest.ui.extensions.inflate
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment(var bundle: Bundle) : Fragment() {

    companion object {
        private val CLASS_TAG = DetailFragment::class.java.simpleName
        const val POST = "DetailFragment:post"
        const val USER_NAME = "DetailFragment:user_name"
        const val USER_EMAIL = "DetailFragment:user_email"
        const val USER_PHONE = "DetailFragment:user_phone"
        const val USER_WEBSITE = "DetailFragment:user_website"
    }

    private lateinit var id: String
    private var name = ""
    private var email = ""
    private var phone = ""
    private var website = ""

    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        id = requireActivity().intent.getIntExtra(POST, -1).toString()
        if (!bundle.isEmpty) {
            bundle = activity?.intent?.extras!!
            name = bundle.getString(USER_NAME).orEmpty()
            email = bundle.getString(USER_EMAIL).orEmpty()
            phone = bundle.getString(USER_PHONE).orEmpty()
            website = bundle.getString(USER_WEBSITE).orEmpty()
        }
        parametersOf(id.toDouble())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initViews(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.model.observe(this, Observer(::updateUi))

        setListeners()

        viewModel.updateReadStatus()
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.post) {
        id = model.post.postId.toString()

        titleDetailTextView.text = model.post.title
        tvDetailDescription.text = model.post.body

        val icon = if (favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
        ivFavorite.setImageDrawable(activity?.applicationContext?.let { getDrawable(it, icon) })

        tvUserName.text = name
        tvUserEmail.text = email
        tvUserPhone.text = phone
        tvUserWebsite.text = website
    }

    private fun setListeners() {
        ivFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun initViews(container: ViewGroup?): View? {
        return container?.inflate(R.layout.fragment_detail)
    }
}
