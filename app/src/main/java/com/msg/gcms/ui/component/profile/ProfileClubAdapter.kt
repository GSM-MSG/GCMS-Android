package com.msg.gcms.ui.component.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.msg.gcms.R

class ProfileClubAdapter: PagerAdapter() {
    override fun getCount(): Int = 3

    val clubNameTxt = arrayOf(
        "전공동아리",
        "자율동아리",
        "심화동아리"
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_my_club, container,false)
        view.findViewById<TextView>(R.id.club_name_txt).text = clubNameTxt[position]
        container.addView(view)
        return view
    }
}