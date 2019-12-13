package kz.dragau.larek.models.objects

data class Boundaries(val points: List<Points>){
    override fun toString(): String {
        return "Boundaries(points=$points)"
    }
}