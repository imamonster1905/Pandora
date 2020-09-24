//package com.esmac.android.dicar.di.module
//
//import android.content.Context
//import com.esmac.android.dicar.data.local.db.AppDatabase
//import com.esmac.android.dicar.data.local.db.dao.*
//import com.esmac.android.dicar.di.scope.ApplicationScope
//import dagger.Module
//import dagger.Provides
//
//
///**
// * Created by Hữu Nguyễn on 16/03/2020.
// * Email: huuntt1905@gmail.com.
// */
//@Module
//class DatabaseModule {
//
//    @Provides
//    @ApplicationScope
//    fun provideAppDatabase(context: Context): AppDatabase {
//        return AppDatabase.getDatabase(context)
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideUserDao(appDatabase: AppDatabase): UserDao {
//        return appDatabase.userDao()
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideCarStyleDao(appDatabase: AppDatabase): CarStyleDao {
//        return appDatabase.carStyleDao()
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideCarTypeDao(appDatabase: AppDatabase): CarTypeDao {
//        return appDatabase.carTypeDao()
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideManufacturerDao(appDatabase: AppDatabase): ManufacturerDao {
//        return appDatabase.manufacturerDao()
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideIdentityPaperDao(appDatabase: AppDatabase): IdentityPaperDao {
//        return appDatabase.identityPaperDao()
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideInsuranceDao(appDatabase: AppDatabase): InsuranceDao {
//        return appDatabase.insuranceDao()
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideRegisterPackageDao(appDatabase: AppDatabase): RegisterPackageDao {
//        return appDatabase.registerPackageDao()
//    }
//}
