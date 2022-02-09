package uz.elmurod.rickandmortyapi.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "ram")
data class RAM(
    @PrimaryKey
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val image: String? = null
) : Serializable {
    companion object {
        object Comparator : DiffUtil.ItemCallback<RAM>() {
            override fun areItemsTheSame(oldItem: RAM, newItem: RAM): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RAM,
                newItem: RAM
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}