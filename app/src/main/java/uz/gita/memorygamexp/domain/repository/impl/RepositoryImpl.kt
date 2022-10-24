package uz.gita.memorygamexp.domain.repository.impl

import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.data.local.preference.AppPreference
import uz.gita.memorygamexp.data.model.Category
import uz.gita.memorygamexp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val appPreference: AppPreference) : Repository {

    private fun getCardOne(): List<Int> {
        return listOf(
            R.raw.card1_0,
            R.raw.card1_1,
            R.raw.card1_2,
            R.raw.card1_3,
            R.raw.card1_4,
            R.raw.card1_5,
            R.raw.card1_6,
            R.raw.card1_7,
            R.raw.card1_8,
            R.raw.card1_9,
            R.raw.card1_10,
            R.raw.card1_11,
            R.raw.card1_12,
            R.raw.card1_13,
            R.raw.card1_14,
            R.raw.card1_15,
            R.raw.card1_16,
            R.raw.card1_17,
            R.raw.card1_18,
            R.raw.card1_19,
            R.raw.card1_20,
            R.raw.card1_21,
            R.raw.card1_22,
            R.raw.card1_23,
            R.raw.card1_24,
            R.raw.card1_25,
            R.raw.card1_26,
            R.raw.card1_27,
        )
    }
    private fun getCardTwo(): List<Int> {
        return listOf(
            R.raw.card2_0,
            R.raw.card2_1,
            R.raw.card2_2,
            R.raw.card2_3,
            R.raw.card2_4,
            R.raw.card2_5,
            R.raw.card2_6,
            R.raw.card2_7,
            R.raw.card2_8,
            R.raw.card2_9,
            R.raw.card2_10,
            R.raw.card2_11,
            R.raw.card2_12,
            R.raw.card2_13,
            R.raw.card2_14,
            R.raw.card2_15,
            R.raw.card2_16,
            R.raw.card2_17,
            R.raw.card2_18,
            R.raw.card2_19,
            R.raw.card2_20,
            R.raw.card2_21,
            R.raw.card2_22,
            R.raw.card2_23,
            R.raw.card2_24,
            R.raw.card2_25,
            R.raw.card2_26,
            R.raw.card2_27,
        )
    }
    private fun getCardThree(): List<Int> {
        return listOf(
            R.raw.card3_0,
            R.raw.card3_1,
            R.raw.card3_2,
            R.raw.card3_3,
            R.raw.card3_4,
            R.raw.card3_5,
            R.raw.card3_6,
            R.raw.card3_7,
            R.raw.card3_8,
            R.raw.card3_9,
            R.raw.card3_10,
            R.raw.card3_11,
            R.raw.card3_12,
            R.raw.card3_13,
            R.raw.card3_14,
            R.raw.card3_15,
            R.raw.card3_16,
            R.raw.card3_17,
            R.raw.card3_18,
            R.raw.card3_19,
            R.raw.card3_20,
            R.raw.card3_21,
            R.raw.card3_22,
            R.raw.card3_23,
            R.raw.card3_24,
            R.raw.card3_25,
            R.raw.card3_26,
            R.raw.card3_27,
        )
    }
    private fun getCardFour(): List<Int> {
        return listOf(
            R.raw.card4_0,
            R.raw.card4_1,
            R.raw.card4_2,
            R.raw.card4_3,
            R.raw.card4_4,
            R.raw.card4_5,
            R.raw.card4_6,
            R.raw.card4_7,
            R.raw.card4_8,
            R.raw.card4_9,
            R.raw.card4_10,
            R.raw.card4_11,
            R.raw.card4_12,
            R.raw.card4_13,
            R.raw.card4_14,
            R.raw.card4_15,
            R.raw.card4_16,
            R.raw.card4_17,
            R.raw.card4_18,
            R.raw.card4_19,
            R.raw.card4_20,
            R.raw.card4_21,
            R.raw.card4_22,
            R.raw.card4_23,
            R.raw.card4_24,
            R.raw.card4_25,
            R.raw.card4_26,
            R.raw.card4_27,
        )
    }
    private fun getItemCount(level: Int): Int {
        return when (level) {
            1 -> 6
            2 -> 12
            else -> 24
        }
    }
    private fun getList(l: List<Int>, level: Int): List<Int> {
        val list = ArrayList<Int>()
        for (i in 0 until level / 2) {
            list.add(l[i])
            list.add(l[i])
        }
        list.shuffle()
        return list
    }

    override suspend fun setIndex(level: Int) {
        when (appPreference.category) {
            Category.FIRST.name -> appPreference.categoryFirstLevel = level
            Category.SECOND.name -> appPreference.categorySecondLevel = level
            Category.THIRD.name -> appPreference.categoryThirdLevel = level
            else -> appPreference.categoryFourthLevel = level
        }
    }

    override suspend fun getItems(): List<Int> {
        val category = getCategory()
        val level = getLevel()
        return when (category) {
            Category.FIRST.name -> {
                getList(getCardOne(), getItemCount(level))
            }
            Category.SECOND.name -> {
                getList(getCardTwo(), getItemCount(level))
            }
            Category.THIRD.name -> {
                getList(getCardThree(), getItemCount(level))
            }
            else -> {
                getList(getCardFour(), getItemCount(level))
            }
        }
    }

    override suspend fun setCategory(category: Category) {
        appPreference.category = category.name
    }

    override suspend fun getCategory(): String = appPreference.category

    override suspend fun getLevel(): Int {
        val category = appPreference.category
        return when (category) {
            Category.FIRST.name -> {
                appPreference.categoryFirstLevel
            }
            Category.SECOND.name -> {
                appPreference.categorySecondLevel
            }
            Category.THIRD.name -> {
                appPreference.categoryThirdLevel
            }
            else -> {
                appPreference.categoryFourthLevel
            }
        }
    }

    override suspend fun setLevel() {
        val category = getCategory()
        var level = getLevel()+1
        if (level < 4) {
            when (category) {
                Category.FIRST.name -> {
                    appPreference.categoryFirstLevel = level
                }
                Category.SECOND.name -> {
                    appPreference.categorySecondLevel = level
                }
                Category.THIRD.name -> {
                    appPreference.categoryThirdLevel = level
                }
                else -> {
                    appPreference.categoryFourthLevel = level
                }
            }
        }
    }

    override suspend fun getItemsForMultiPlayer(): List<Int> {
        return when (appPreference.category) {
            Category.FIRST.name -> {
                getList(getCardOne(), getItemCount(24))
            }
            Category.SECOND.name -> {
                getList(getCardTwo(), getItemCount(24))
            }
            Category.THIRD.name -> {
                getList(getCardThree(), getItemCount(24))
            }
            else -> {
                getList(getCardFour(), getItemCount(24))
            }
        }
    }

    override suspend fun setDefaultLevel() {
        when (getCategory()) {
            Category.FIRST.name -> {
                appPreference.categoryFirstLevel = 1
            }
            Category.SECOND.name -> {
                appPreference.categorySecondLevel = 1
            }
            Category.THIRD.name -> {
                appPreference.categoryThirdLevel = 1
            }
            else -> {
                appPreference.categoryFourthLevel = 1
            }
        }
    }

}