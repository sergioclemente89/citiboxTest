package com.clementecastillo.citiboxtest.screen.userinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.client.ImageLoader
import com.clementecastillo.citiboxtest.screen.base.BaseDialogFragment
import com.clementecastillo.citiboxtestcore.domain.data.Address
import com.clementecastillo.citiboxtestcore.domain.data.User
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.user_info_layout.*
import javax.inject.Inject

class UserInfoDialogFragment : BaseDialogFragment(), UserInfoDialogView {
    companion object {
        fun create(): UserInfoDialogFragment = UserInfoDialogFragment()
    }

    @Inject
    lateinit var presenter: UserInfoDialogPresenter

    private val onAddressClickSubject = PublishSubject.create<Address>()
    private val onPhoneClickSubject = PublishSubject.create<String>()
    private val onEmailClickSubject = PublishSubject.create<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screen().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_info_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        close_dialog_button.setOnClickListener { dismiss() }

        init(presenter, this)
    }

    override fun bindUser(user: User) {
        with(user) {
            ImageLoader.loadUserAvatar(id, user_avatar, rounded = true)
            user_nickname.text = nickname
            user_fullname.text = fullName
            user_address.text = address.getFullAddress()
            user_address_container.setOnClickListener { onAddressClickSubject.onNext(address) }
            user_phone.text = phone
            user_phone_container.setOnClickListener { onPhoneClickSubject.onNext(phone) }
            user_email.text = email
            user_email_container.setOnClickListener { onEmailClickSubject.onNext(email) }
        }
    }

    override fun onAddressClick(): Observable<Address> {
        return onAddressClickSubject
    }

    override fun onPhoneClick(): Observable<String> {
        return onPhoneClickSubject
    }

    override fun onEmailClick(): Observable<String> {
        return onEmailClickSubject
    }
}