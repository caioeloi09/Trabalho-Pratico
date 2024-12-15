package org.eng.soft.two.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.eng.soft.two.entity.Account
import java.io.File

class FileManagerUtil(private val filePath: String) {
    private val gson = Gson()

    fun saveAccounts(accounts: Map<Long, Account>) {
        val json = gson.toJson(accounts)
        File(filePath).writeText(json)
    }

    fun loadAccounts(): MutableMap<Long, Account> {
        val file = File(filePath)
        if (!file.exists()) {
            return mutableMapOf()
        }
        val type = object : TypeToken<MutableMap<Long, Account>>() {}.type
        return gson.fromJson(file.readText(), type) ?: mutableMapOf()
    }
}
