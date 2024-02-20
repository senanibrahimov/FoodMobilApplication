package com.example.foodsproject.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "yemekler")
data class Yemekler( @PrimaryKey(autoGenerate = true)
                    @ColumnInfo("yemek_id") @NotNull val food_id:Int,
                   @ColumnInfo("yemek_adi") @NotNull val name:String,
                     @ColumnInfo("yemek_resim_adi") @NotNull val pictureNAme:String,
                     @ColumnInfo("yemek_fiyat") @NotNull val price:Int)
