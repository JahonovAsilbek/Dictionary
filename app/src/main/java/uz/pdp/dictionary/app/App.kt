package uz.pdp.dictionary.app

import android.app.Application
import uz.pdp.dictionary.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}