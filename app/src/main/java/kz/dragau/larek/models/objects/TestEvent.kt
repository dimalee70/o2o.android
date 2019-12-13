package kz.dragau.larek.models.objects

data class TestEvent(
                     var backgrndImage: String,
                     var time: String,
                     var address: String,
                     var where: String,
                     var title: String,
                     var theme: String,
                     var likes: String,
                     var frameColor: String,
                     var amount: String,
                     var firstProfile: String,
                     var secondProfile: String,
                     var thirdProfile: String,
                     var footer: List<TestEventState>
                     ){
}