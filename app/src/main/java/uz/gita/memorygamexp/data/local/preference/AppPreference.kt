package uz.gita.memorygamexp.data.local.preference

import android.content.Context

class AppPreference(context: Context) {

    private val sharedPreference = context.getSharedPreferences("memory", Context.MODE_PRIVATE)

    var category: String
        get() = sharedPreference!!.getString("CATEGORY", "") ?: ""
        set(value) = sharedPreference!!.edit().putString("CATEGORY", value).apply()

    var categoryFirstLevel: Int
        get() = sharedPreference!!.getInt("FIRSTLEVEL", 1)
        set(value) = sharedPreference!!.edit().putInt("FIRSTLEVEL", value).apply()

    var categorySecondLevel: Int
        get() = sharedPreference!!.getInt("SECONDLEVEL", 1)
        set(value) = sharedPreference!!.edit().putInt("SECONDLEVEL", value).apply()

    var categoryThirdLevel: Int
        get() = sharedPreference!!.getInt("THIRDLEVEL", 1)
        set(value) = sharedPreference!!.edit().putInt("THIRDLEVEL", value).apply()

    var categoryFourthLevel: Int
        get() = sharedPreference!!.getInt("FOURTHLEVEL", 1)
        set(value) = sharedPreference!!.edit().putInt("FOURTHLEVEL", value).apply()


}